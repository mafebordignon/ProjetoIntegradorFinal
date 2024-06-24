var Id;
// window.onload=function (Puxa as informações do item)

const campoItem = document.getElementById("item")
const campoCategoria = document.getElementById("categoria")
const campoOrigem = document.getElementById("origem")
const campoDisponibilidade = document.getElementById("disponibilidade")

// buttonSalvar.addEventListener (Puxa as informações a serem salvadas)
const buttonSalvar = document.getElementById("salvar")
const campoLocalAtual = document.getElementById("loc_atual")
const campoResponsavel = document.getElementById("responsavel")
const campoDescricao = document.getElementById("descricao")

const url = window.location.href;
const parts = url.split('/');
Id = parts[parts.length - 1];

// Mostra as infromações puxadas do item
window.onload=function (){
    fetch('/itens/'+Id)
        .then(response => {
            if(!response.ok){
                throw new Error('Erro ao buscar item');
            }
            return response.json();
        })

        .then(data => {
            campoItem.value = data.descricao;
            campoCategoria.value = data.categoria.nome;
            campoOrigem.value = data.localizacao.nome;
            campoDisponibilidade.value = data.disponibilidade.nome;

            console.log(data);
        })

        .catch(error => {
            console.error('Erro:', error)
        })

    fetch('/localizacoes')
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao buscar as opções.');
            }
            return response.json();
        })
        .then(options => {
            var flagOrigem = 1;
            var select = document.getElementById('loc_atual');
            select.innerHTML = '';
            options.forEach(option => {
                if (option.nome === document.getElementById('origem').innerText){
                    flagOrigem = option.id;
                }
                var optionElement = document.createElement('option');
                optionElement.value = option.id;
                optionElement.textContent = option.nome;
                select.appendChild(optionElement);
            });
            select.value = flagOrigem;
        })
        .catch(error => {
            console.error('Erro:', error);
        });

}

// Salva as informações colocadas
buttonSalvar.addEventListener("click", function() {
    async function adicionarAcao() {

    const campoErroResponsavel = document.getElementById("erro-responsavel");

        if (!campoResponsavel.value.trim()) {
            campoErroResponsavel.textContent = "O campo de responsável é obrigatório.";
            campoErroResponsavel.style.display = "block";
            return;
        } else {
            campoErroResponsavel.style.display = "none";
        }

        const acao = {
            item: { id: Id },
            tipoacao: { id: 1 },
            localizacao: { id: campoLocalAtual.value },
            usuario: { id: 1 },
            dataEmprestimo: getFormattedDate(),
            descricao: campoDescricao.value,
            entidade: campoResponsavel.value,
        };

        try {
            const response = await fetch('/acoes', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(acao)
            });

            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message || 'Erro ao adicionar ação');
            }

            const novaAcao = await response.json();
            console.log('Ação adicionada com sucesso:', novaAcao);
            window.location.href = "/especificacao-item/"+Id;

//            window.location.href = '/logon';
        } catch (error) {
            console.error('Erro:', error.message);
        }
    }

    adicionarAcao();
});
function getFormattedDate() {
    const now = new Date();

    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, '0');
    const day = String(now.getDate()).padStart(2, '0');

    return `${year}-${month}-${day}T00:00:00`;
}