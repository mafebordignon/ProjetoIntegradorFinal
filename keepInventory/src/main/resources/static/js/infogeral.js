document.addEventListener('DOMContentLoaded', () => {
    var itemCarregado;
    const prev = document.getElementById("prev");
    const next = document.getElementById("next");
    const pageNumber = document.getElementById("page");
    const pageSize = 10;
    let currentPage = 1;
    let maxPage = 0;
    let filter = '';
    let searchContent = '';
    let idFilter = '';
    let statusFilter = 'none';
    let url = '';
    let selectedCategory = '';
    let query = "";
    const searchButton = document.getElementById('button-pesquisar');
    const search = document.querySelector('.pesquisar');

    function changeUrl() {
        url = `/infogeral/itens?pageNumber=${currentPage}&pageSize=${pageSize}&filter=${filter}&idFilter=${idFilter}&query=${encodeURIComponent()}&nome=${searchContent}`;
    }

    function changeCategoryUrl(categoriaId) {
        if (selectedCategory === categoriaId) {
            filter = 'none';
            idFilter = '1';
            selectedCategory = '';
        } else {
            filter = 'categoria';
            idFilter = categoriaId;
            selectedCategory = categoriaId;
        }
        currentPage = 0;
        changeUrl();
    }

    function carregarItens() {
        fetch(url)
            .then(response => response.json())
            .then(data => {
                exibirItens(data);
                console.log(data);
                maxPage = data.totalPages - 1;
            })
            .catch(error => {
                console.error('Erro:', error);
            });
    }
//
    var pageCategoria = 1;
    var maxPageCategoria;

    document.getElementById("botao-anterior-categoria").onclick = () => {
        if(pageCategoria != 1){
            pageCategoria -= 1;
            carregarCategorias();
        }
    }

    document.getElementById("botao-proximo-categoria").onclick = () => {
        if(pageCategoria != maxPageCategoria){
            pageCategoria += 1;
            carregarCategorias();
        }
    }
  //

    function carregarCategorias() {
        const boxcat = document.getElementsByClassName('box-cat')[0];
        boxcat.innerHTML = ""
        fetch("/categorias")
            .then(response => response.json())
            .then(data => {
                console.log(data);
                let categoriaCount = 0;
                maxPageCategoria = Math.ceil(data.length / 3);
                data.forEach((categoria, index) => {
                    if (index >= (pageCategoria*3)-3 && categoriaCount < 3) {
                        const categoryHeader = document.createElement('div');
                        categoryHeader.classList.add('category-header1');

                        const buttonCat = document.createElement('button');
                        buttonCat.classList.add('category-title1');
                        buttonCat.setAttribute('data-category', categoria.id);
                        buttonCat.textContent = categoria.nome;

                        categoryHeader.appendChild(buttonCat);
                        boxcat.appendChild(categoryHeader);

                        const categoryButtons = document.querySelectorAll('.box-cat button');
                        categoryButtons.forEach(button => {
                            button.onclick = () => {
                                const categoriaId = button.getAttribute('data-category');
                                console.log(categoriaId);
                                changeCategoryUrl(categoriaId);
                                pageNumber.value = 1;
                                carregarItens();
                            };
                        });

                        categoriaCount++;
                    }
                });
            })
            .catch(error => {
                console.error('Erro:', error);
            });
    }

    async function getLocalAtual(itemId) {
        try {
            const response = await fetch(`/infogeral/acoes/${itemId}`);
            if (!response.ok) {
                throw new Error('Erro ao buscar dados do servidor');
            }
            const data = await response.json();
            if ((data.content).length === 0) {
                return itemCarregado.localizacao.nome;
            } else {
                return data.content[0].localizacao.nome;
            }
        } catch (error) {
            console.error('Erro:', error);
            return 'Erro ao buscar localização';
        }
    }

    function getClassFromId(id) {
        switch (id) {
            case 1:
                return 'Emprestado';
            case 2:
                return 'Disponivel';
            case 3:
                return 'Manutençãonecessita';
            case 4:
                return 'Emmanutenção';
            case 5:
                return 'irrecuperável';
            default:
                return ''; // Caso nenhum dos ids correspondam
        }
    }


    async function exibirItens(data) {
        const container = document.getElementById('container');
        container.innerHTML = '';

        for (const item of data.content) {
            itemCarregado = item;
            const localAtual = await getLocalAtual(item.id);

            const itemElement = document.createElement('a');
            itemElement.href = `/especificacao-item/${item.id}`;
            itemElement.style.textDecoration = 'none';
            itemElement.style.color = 'black';
            itemElement.setAttribute('class', 'grid-layout-item');
            const htmlContent = `
                <div class="ptamanho itemlayout">${item.descricao}</div>
                <div class="ptamanho locallayout">${localAtual}</div>
                <div class="ptamanho origemlayout">${item.localizacao.nome}</div>
                <div class="ptamanho marcalayout">${item.modelo.marca.nome}</div>
                <div class="ptamanho ativolayout">${item.estado.nome}</div>
                <div class="ptamanho ${getClassFromId(item.disponibilidade.id)}">${getNomeClass(item.disponibilidade.id)}</div>
            `;
            itemElement.innerHTML = htmlContent;
            container.appendChild(itemElement);
        console.log(item)
            const hr = document.createElement('hr');
            hr.setAttribute('class', 'separator');
            container.appendChild(hr);
        }
    }

    function getNomeClass(id) {
        switch (id) {
            case 1:
                return 'Disponível';
            case 2:
                return 'Emprestado';
            case 3:
                return 'Requer Reparo';
            case 4:
                return 'Em manutenção';
            case 5:
                return 'Irrecuperável';
            default:
                return ''; // Caso nenhum dos ids correspondam
        }
    }

    next.onclick = () => {
        if (parseInt(pageNumber.value) < maxPage) {
            currentPage = parseInt(pageNumber.value) + 1;
            pageNumber.value = currentPage;
            changeUrl();
            carregarItens();
        }
    };

    prev.onclick = () => {
        if (parseInt(pageNumber.value) > 1) {
            currentPage = parseInt(pageNumber.value) - 1;
            pageNumber.value = currentPage;
            changeUrl();
            carregarItens();
        }
    };
    //Pesquisar Inicio

    function displayResults(data) {
        const resultsContainer = document.getElementById('results');
        resultsContainer.innerHTML = '';

        if (data.content.length === 0) {
            resultsContainer.innerHTML = '<p>Nenhum item encontrado.</p>';
            return;
        }

        data.content.forEach(item => {
            const resultItem = document.createElement('div');
            resultItem.textContent = item.descricao;
            resultItem.classList.add('item');
            resultsContainer.appendChild(resultItem);
        });
    }

    function filterItems(query) {
        const items = document.querySelectorAll('.item');
        items.forEach(item => {
            if (item.textContent.toLowerCase().includes(query.toLowerCase())) {
                item.style.display = 'block';
            } else {
                item.style.display = 'none';
            }
        });
    }

    searchButton.addEventListener('click', () => {
        searchContent = search.value.trim().toLowerCase();
        searchContent ? filter = "nome" : filter= "none";
        console.log(searchContent);
        changeUrl();
        carregarItens();

        });
//pesquisar Fim

    window.onload = function () {
        pageNumber.value = 1;
        changeUrl();
        carregarItens();
        carregarCategorias();
    };
});

// Gerar XLSX

function abrirModal() {
    document.getElementById('modal-importar').style.display = 'block';
}

function fecharModal() {
    document.getElementById('modal-importar').style.display = 'none';
}

function salvarArquivo() {
       var file = document.getElementById('fileInput').files[0];
    console.log('Arquivo selecionado:', file);

    const message = document.getElementById('message');

    if (!fileInput.files.length) {
        message.textContent = 'Please select a file.';
        return;
    }

    const formData = new FormData();
    formData.append('file', file);

    fetch('/api/import/excel', {
        method: 'POST',
        body: formData,
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            return response.text();
        })
        .then(data => {
            alert(data);
        })
        .catch(error => {
            console.error('Failed to upload file: ' + error.message);
        });

    fecharModal();
}

// baixar
function baixarArquivo() {
    const url = '/gerar-relatorio/lista';

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao baixar o arquivo');
            }
            return response.blob();
        })
        .then(blob => {
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.style.display = 'none';
            a.href = url;
            a.download = 'relatorio_lista.xlsx';
            document.body.appendChild(a);
            a.click();
            window.URL.revokeObjectURL(url);
        })
        .catch(error => {
            console.error('Erro:', error);
        });
}

function redirecionar(){
    window.location.href = "/cadastro-item"
}