
    document.addEventListener('DOMContentLoaded', function() {
        const campoItem = document.getElementById("item");
        const campoMarca = document.getElementById("marca");
        const campoModelo = document.getElementById("modelo");
        const camponumeroSerie = document.getElementById("numSerie");
        const campoCategoria = document.getElementById("categoria");
        const campoOrigem = document.getElementById("origem");
        const campoPotencia = document.getElementById("potencia");
        const campoNumeroNotaFiscal = document.getElementById("nf");
        const campoDataEntrada = document.getElementById("data");
        const campoDataNota = document.getElementById("dnf");
        const campoPrazoMunutencao = document.getElementById("prazoManutencao");
        const campoLocal = document.getElementById("local");
        const campoAnexos = document.getElementById("anexos");
        const buttonSalvar = document.getElementById("salvar");

    function limparCampos() {
        campoItem.value = '';
        camponumeroSerie.value = '';
        campoCategoria.value = '';
        campoPotencia.value = '';
        campoNumeroNotaFiscal.value = '';
        campoDataNota.value = '';
        campoPrazoMunutencao.value = '';
        campoAnexos.value = '';
    }

        function carregarOpcoes() {
            fetch('/api/register/localizacoes')
            .then(response => response.json())
            .then(options => {
                preencherSelect(campoOrigem, options);
                preencherSelect(campoLocal, options);
            })
            .catch(error => console.error('Erro ao buscar localizações:', error));

            fetch('/api/register/categorias')
            .then(response => response.json())
            .then(options => preencherSelect(campoCategoria, options))
            .catch(error => console.error('Erro ao buscar categorias:', error));

            fetch('/api/register/marcas')
            .then(response => response.json())
            .then(options => preencherSelect(campoMarca, options))
            .catch(error => console.error('Erro ao buscar marcas:', error));
        }   

        function preencherSelect(selectElement, options) {
            selectElement.innerHTML = '';
            options.forEach(option => {
                const optionElement = document.createElement('option');
                optionElement.value = option.id;
                optionElement.textContent = option.nome;
                selectElement.appendChild(optionElement);
            });
        }

        carregarOpcoes();

        campoMarca.addEventListener('change', (event) => {
            const selectedValue = event.target.value;
            fetch('/api/register/modelos/' + campoMarca.value)
            .then(response => response.json())
            .then(options => preencherSelect(campoModelo, options))
            .catch(error => console.error('Erro ao buscar marcas:', error));
            console.log('Você selecionou: ' + selectedValue);
        });

        document.querySelector("form").addEventListener("submit", function(e) {
            e.preventDefault();
            if (campoNumeroNotaFiscal.value.length !== 9 || !/^\d+$/.test(campoNumeroNotaFiscal.value)) {

                alert('O número da Nota Fiscal deve ter exatamente 9 dígitos.');
                return;
            }

            const dataAtual = new Date();
            const ano = dataAtual.getFullYear();
            const mes = String(dataAtual.getMonth() + 1).padStart(2, '0'); // Janeiro é 0!
            const dia = String(dataAtual.getDate()).padStart(2, '0');
            console.log(`${campoDataNota.value}T00:00:00`);

            let campoDataEntrada = `${ano}-${mes}-${dia}T00:00:00`;
            const dadosItem = {
                descricao: campoItem.value,
                modelo: {id: campoModelo.value},
                numeroSerie: camponumeroSerie.value,
                categoria: {id: campoCategoria.value},
                localizacao: {id: campoOrigem.value},
                potencia: campoPotencia.value,
                numeroNotaFiscal: campoNumeroNotaFiscal.value,
                dataEntrada: campoDataEntrada,
                dataNotaFiscal: `${campoDataNota.value}T00:00:00`,
                disponibilidade: {id: 1},
                estado: {id: 1}
            };

            fetch('/api/register/salvarItem', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(dadosItem)
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erro ao salvar o item');
                }
                return response.json();
            })
            .then(data => {
                alert('Item salvo com sucesso');
                limparCampos();

                // Redirecionar ou mostrar uma mensagem de sucesso
            })
            .catch(error => console.error('Erro ao salvar o item:', error));
        });
    });

    function getFormattedDate() {
        const now = new Date();

        const year = now.getFullYear();
        const month = String(now.getMonth() + 1).padStart(2, '0');
        const day = String(now.getDate()).padStart(2, '0');

        return `${year}-${month}-${day}T00:00:00`;
    }
