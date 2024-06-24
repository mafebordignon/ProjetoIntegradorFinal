var curpage = 0;
var size = 10;
var totalPages = 0;
var paginaType;
document.addEventListener('DOMContentLoaded', function() {
    const url = '/campo-ativo/marca/tem';
    updateContent('categorias',curpage,size)

    fetch(url)
        .then(response => response.json())
        .then(data => {
            if (!data) {
                document.getElementById('modeloButton').style.display = 'none';
            }
        })
        .catch(error => console.error('Erro ao verificar marcas:', error));


    document.getElementById('categoriaButton').addEventListener('click', () => updateContent('categorias',curpage,size));
    document.getElementById('localizacaoButton').addEventListener('click', () => updateContent('localizacoes',curpage,size));
    document.getElementById('marcaButton').addEventListener('click', () => updateContent('marcas',curpage,size));
    document.getElementById('modeloButton').addEventListener('click', () => updateContent('modelos',curpage,size));

    document.getElementById('form').addEventListener('submit', () => cadastrar(event,paginaType));

    document.getElementById('anteriorButton').addEventListener('click', () => {
        if (curpage > 0) {
            curpage--;
            updateContent(paginaType, curpage, size);
        }
    });

    document.getElementById('proximoButton').addEventListener('click', () => {
        if (curpage < totalPages - 1) {
            curpage++;
            updateContent(paginaType, curpage, size);
        }
    });

    document.getElementById('pageNumberInput').addEventListener('change', (event) => {
        const pageNumber = parseInt(event.target.value);
        if (!isNaN(pageNumber) && pageNumber >= 1 && pageNumber <= totalPages) {
            curpage = pageNumber - 1;
            updateContent(paginaType, curpage, size);
        }
    });
});



const endpoints = {
    categorias: '/campo-ativo/categorias',
    localizacoes: '/campo-ativo/localizacoes',
    marcas: '/campo-ativo/marcas',
    modelos: '/campo-ativo/modelos'
};

function updateContent(type, page, size) {
    console.log(type)


    if (paginaType !== type) {
            page = 0;
    }
    paginaType = type;


    const marcaCampo = document.getElementById('marca-campo');
    const lMarcaCampo = document.getElementById('l-marca-campo');
    if (type === "modelos") {
        lMarcaCampo.style.display = "block";
        marcaCampo.style.display = "block";

        pegarMarcas()
            .then(marcaList => {
                marcaCampo.innerHTML = "";
                marcas = marcaList;
                marcas.forEach(marca => {
                    let option = document.createElement('option');
                    option.value = marca.id;
                    option.textContent = marca.nome;
                    marcaCampo.appendChild(option);
                });
            })
            .catch(error => {
                console.error('Erro ao buscar marcas:', error);
            });
    } else {
        lMarcaCampo.style.display = "none";
        marcaCampo.style.display = "none";
    }

    const h1 = document.querySelector('section h1');
    const listaItens = document.getElementById('listaItens');
    const pageNumberInput = document.getElementById('pageNumberInput');
    const anteriorButton = document.getElementById('anteriorButton');
    const proximoButton = document.getElementById('proximoButton');

    // Update the h1 text
    h1.textContent = capitalizeFirstLetter(type);

    // Fetch the data for the clicked type
    fetch(`${endpoints[type]}?page=${page}&size=${size}`)
        .then(response => response.json())
        .then(data => {
            curpage = page; // Atualiza a página atual

            // Calcula o número total de páginas
            totalPages = data.totalPages;

            // Atualiza o campo de entrada da página com o número atual
            pageNumberInput.value = curpage + 1;

            // Habilita ou desabilita os botões de anterior e próximo conforme necessário
            anteriorButton.disabled = curpage === 0;
            proximoButton.disabled = curpage === totalPages - 1;

            // Limpa os itens existentes na lista
            listaItens.innerHTML = '';

            // Popula a lista com os dados obtidos
            data.content.forEach(item => {
                const listItem = document.createElement('div');
                listItem.classList.add('item');
                listItem.dataset.itemId = item.id;
                const nome = document.createElement('p');
                nome.textContent = item.nome;
                listItem.appendChild(nome);
                const alterarButton = document.createElement('button');
                alterarButton.textContent = 'Alterar';
                alterarButton.classList.add("button")
                alterarButton.classList.add("button-alterar")

                // Adiciona evento de abrir modal ao botão Alterar
                alterarButton.addEventListener('click', () => abrirModal(item));

                if (type === "modelos") {
                    const marca = document.createElement('p');
                    marca.textContent = item.marca.nome;
                    listItem.appendChild(marca);
                }
                listItem.appendChild(alterarButton);

                listItem.style.display = "flex";
                listItem.style.flexDirection = "row";
                listaItens.appendChild(listItem);
            });
        })
        .catch(error => console.error(`Erro ao buscar ${type}:`, error));
}


function capitalizeFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}

function pegarMarcas(){
    return fetch("/campo-ativo/marcas/todas")
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao buscar marcas');
            }
            return response.json();
        })
        .then(data => {
            return data;
        })
        .catch(error => {
            console.error('Erro ao buscar marcas:', error);
            throw error; // Rejeita a Promise para propagar o erro
        });
}

function cadastrar(event, pagina){
    event.preventDefault();
    const nomeCampo = document.getElementById("nome-campo");
    const marcaCampo = document.getElementById('marca-campo')
    var body = {
        nome: nomeCampo.value
    }

    switch(pagina){
        case "categorias":
            cadastrarComum("categorias", body);
            break;
        case "localizacoes":
            cadastrarComum("localizacoes", body);
            break;
        case "marcas":
            cadastrarComum("marcas", body);
            break;
        case "modelos":
            body = {
                nome: nomeCampo.value,
                marca: {
                    id: marcaCampo.value
                }

            }
            cadastrarModelo(body);
            break;
    }
    nomeCampo.value = ""
}

function cadastrarComum(campo,body){
    fetch('/campo-ativo/'+campo, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(body)
    })
        .then(response => {
            if (!response.ok) {
                alert("Campo já existe!")
                throw new Error('Erro ao cadastrar campo');
            }
            else{
                            alert("Campo cadastrado com sucesso")
                            updateContent(paginaType,curpage,size)
                        }
            return response.json();
        })

        .catch(error => {
            console.error('Erro ao cadastrar campo:', error);
        });
}

function cadastrarModelo(body){
    fetch('/campo-ativo/modelos', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(body)
    })
        .then(response => {
            if (!response.ok) {
                alert("Campo já existe!")
                throw new Error('Erro ao cadastrar campo');
            }
            else{
                alert("Campo cadastrado com sucesso")
                updateContent(paginaType,curpage,size)
            }
            return response.json();
        })

        .catch(error => {
            console.error('Erro ao cadastrar campo:', error);
        });
}

function abrirModal(item) {
    console.log('Abrindo modal para item:', item);
     const modal = document.getElementById('alterarModal');
     modal.style.display = 'block';

     const novoNomeInput = document.getElementById('novoNome');
     novoNomeInput.value = item.nome;

     const marcaCampoModal = document.getElementById('marcaCampoModal');
     if (paginaType === 'modelos') {
         marcaCampoModal.style.display = 'block';

         pegarMarcas()
             .then(marcaList => {
                 const novaMarcaSelect = document.getElementById('novaMarca');
                 novaMarcaSelect.innerHTML = '';
                 marcaList.forEach(marca => {
                     let option = document.createElement('option');
                     option.value = marca.id;
                     option.textContent = marca.nome;
                     novaMarcaSelect.appendChild(option);
                 });
                 novaMarcaSelect.value = item.marca.id;
             })
             .catch(error => {
                 console.error('Erro ao buscar marcas:', error);
             });
     } else {
         marcaCampoModal.style.display = 'none';
     }

     const confirmarButton = document.getElementById('confirmarAlteracao');
     // Remover o evento de clique anterior
     // Adicionar o evento de clique atual
     confirmarButton.addEventListener('click', () => confirmarAlteracao(item.id), { once: true });
 }

// Função para confirmar a alteração
function confirmarAlteracao(itemId) {
console.log('Confirmando alteração para item ID:', itemId);
    const novoNome = document.getElementById('novoNome').value;
    let body = { nome: novoNome };

    if (paginaType === 'modelos') {
        const novaMarca = document.getElementById('novaMarca').value;
        body = {
            nome: novoNome,
            marca: { id: novaMarca }
        };
    }

    fetch(`/campo-ativo/${paginaType}/${itemId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(body)
    })
    .then(response => {
        if (!response.ok) {
            alert("Campo já existe!")
            throw new Error('Erro ao atualizar item');
        }
        alert('Item atualizado com sucesso');
        // Aqui você pode chamar updateContent novamente para atualizar a lista após a alteração
        updateContent(paginaType, curpage, size);
        const modal = document.getElementById('alterarModal');
        modal.style.display = 'none'; // Fecha o modal após o sucesso
    })
    .catch(error => {
        console.error('Erro ao atualizar item:', error);
    });
}

// Evento para fechar o modal
document.getElementsByClassName('close')[0].onclick = function() {
    const modal = document.getElementById('alterarModal');
    modal.style.display = 'none';
}

// Evento para fechar o modal se clicar fora dele
window.onclick = function(event) {
    const modal = document.getElementById('alterarModal');
    if (event.target == modal) {
        modal.style.display = 'none';
    }
}