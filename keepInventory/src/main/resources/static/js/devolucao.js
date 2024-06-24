var id;
 // window.onload=function (Puxa as informações do item)
const campoItem = document.getElementById("item")
const campoCategoria = document.getElementById("categoria")
const campoOrigem = document.getElementById("origem")
const campoDisponibilidade = document.getElementById("disponibilidade")

// buttonSalvar.addEventListener (Puxa as informações a serem salvadas)
const buttonSalvar = document.getElementById("salvar")
const campoLocalAtual = document.getElementById("local")
const campoResponsavel = document.getElementById("responsavel")
const campoDescricao = document.getElementById("descricao")

const url = window.location.href;
const parts = url.split('/');
Id = parts[parts.length - 1];

// Mostra as infromações puxadas do item
window.onload=function (){
    console.log('aqui')
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
            console.log(data.disponibilidade.nome);

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
            var select = document.getElementById('local');
            select.innerHTML = ''; // Limpa as opções existentes
            options.forEach(option => {
                if (option.nome === document.getElementById('origem').innerText){ //
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

    const campoErroResponsavel = document.getElementById("erro-responsavel");

    if (!campoResponsavel.value.trim()) {
        campoErroResponsavel.textContent = "Este campo é obrigatório.";
        campoErroResponsavel.style.display = "block";
        return;
    } else {
        campoErroResponsavel.style.display = "none";
    }

    async function adicionarAcao() {
        const acao = {
            item: { id: Id },
            tipoacao: { id: 2 },
            localizacao: { id: campoLocalAtual.value },
            usuario: { id: 1 },
            dataEmprestimo: getFormattedDate(),
            dataDevolucao: getFormattedDate(),
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

            if (response.ok) {
                const novaAcao = await response.json();
                document.getElementById('alert-sucesso').classList.add('show');

                window.onclick = function(event) {
                    const modal = document.getElementById('alert-sucesso');
                    if (event.target == modal) {
                        modal.style.display = 'none';
                         window.location.href = "/especificacao-item/"+Id;
                    }
                }

            }
        } catch (error) {
            const errorData = await response.json();
            document.getElementById('salvar').addEventListener('click', function() {
                document.getElementById('alert-erro').classList.add('show');
            });

            window.onclick = function(event) {
                const modal = document.getElementById('alert-erro');
                if (event.target == modal) {
                    modal.style.display = 'none';
                }
            }
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


// campo de anexos:
// document.addEventListener('DOMContentLoaded', function() {
//     document.getElementById('anexos').addEventListener('change', function() {
//         var fileNames = Array.from(this.files).map(file => file.name).join(', ');
//         document.getElementById('file-upload-label').textContent = fileNames || 'Nenhum arquivo selecionado';
//     });
// });