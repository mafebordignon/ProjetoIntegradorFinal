const curPageElement = document.getElementById("curPage");
const proxB = document.getElementById("botao-proximo");
const anteB = document.getElementById("botao-anterior");
const tbody = document.getElementById('usuario-table-body');
const pVazio = document.getElementById('pVazio');
var maxPage;

document.getElementById('botao-anterior').addEventListener('click', paginaAnterior);
document.getElementById('botao-proximo').addEventListener('click', paginaProxima);

window.onload = function() {
curPageElement.value = 1;
    carregarUsuarios(curPageElement.value);
};


/// Listar Usuarios

function carregarUsuarios(curPage){
    tbody.innerHTML = '';
    curPage -= 1;
    fetch('api/usuarios?page='+curPage) // Replace with your API endpoint
        .then(response => response.json())
        .then(data => {
            maxPage = data.totalPages;
            console.log(data);
            initPages((data.content).length);
            data.content.forEach(usuario => {
                if(usuario.ativo === true){
                    usuario.ativo = "Sim";
                } else {
                    usuario.ativo = "Não";
                }
                const tr = document.createElement('tr');
                tr.classList.add(usuario.id)
                tr.innerHTML = `
                <td>${usuario.nome}</td>
                <td>${usuario.sobrenome}</td>
                <td>${usuario.email}</td>
                <td>${usuario.role}</td>
                <td>${usuario.ativo}</td>
                <td style="align-items: center;">
                    <button onclick="alterarUsuario(${usuario.id})" class="button"  style="height: 50px;">Alterar</button>
                    <button onclick="senhaReset(${usuario.id})" class="button">Resetar Senha</button>
                    <button onclick="toggleAtivo(${usuario.id})" class="button" style="height: 50px;">${usuario.ativo === "Sim" ? "Desativar" : "Ativar"}</button>
                </td>
                `;
                tbody.appendChild(tr);
            });
            console.log(curPage)
            if(maxPage == 1){
                curPageElement.style.display = 'none';
                proxB.style.display = 'none';
                anteB.style.display = 'none';
            }
        })
        .catch(error => {
            console.error('Error fetching data:', error);
        });
}



function initPages(numUsuarios) {
    console.log(numUsuarios);
    if (numUsuarios === 0) {
        pVazio.style.display = 'block';
    } else {
        pVazio.style.display = 'none';
    }

    curPageElement.style.display = 'block';
    proxB.style.display = 'block';
    anteB.style.display = 'block';

    anteB.disabled = curPageElement.value == 1;
    proxB.disabled = curPageElement.value == maxPage;
}

function paginaAnterior() {
    if (curPageElement.value > 1) {
        curPageElement.value = parseInt(curPageElement.value) - 1;
        carregarUsuarios(curPageElement.value);
    }
}

function paginaProxima() {
    if (curPageElement.value < maxPage) {
        curPageElement.value = parseInt(curPageElement.value) + 1;
        carregarUsuarios(curPageElement.value);
    }
}
function tirarFiltros(){
    curPageElement.value = 1;
}

/// Cadastrar Usuarios
function validateForm(event) {
    event.preventDefault();
    var nome =  document.getElementById("nome-input").value;
    var sobrenome =  document.getElementById("sobrenome-input").value;
    var email = document.getElementById("email-input").value.toLowerCase();
    var role = document.getElementById("cargo-input").value;
    fetch('/admin/api/usuarios', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({nome, sobrenome, email, role})
        })
        .then(response => {
            if (!response.ok) {
                return response.text().then(errorMsg => {
                    throw new Error(errorMsg);
                });
            }
            return response.json();
        })
        .then(() => {
            alert("Usuário cadastrado com sucesso!");
            tirarFiltros();
            carregarUsuarios(curPageElement.value);
        })
        .catch(error => {
            alert('Erro: ' + error.message);
        });
        return true;
}

document.getElementById('logon-form').addEventListener('submit', validateForm);

// atualizar usuario

function toggleAtivo(id) {
    if(id != 1){
        fetch('/admin/api/usuarios/' + id + '/toggleAtivo', { method: 'PUT' }) // Endpoint para ativar/desativar o usuário
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erro ao ativar/desativar usuário');
                }
                return response.json();
            })
            .then(() => {
                alert("Status do usuário atualizado com sucesso!");
                tirarFiltros();
                carregarUsuarios(curPageElement.value);
            })
            .catch(error => {
                console.error('Erro ao ativar/desativar usuário:', error);
            });
    } else {
        alert("admin principal")
    }

}

function senhaReset(id){
    if(id != 1){
        var modal = document.getElementById("resetModal");
        var span = document.getElementsByClassName("close")[0];
        var confirmButton = document.getElementById("confirmButton");
        var cancelButton = document.getElementById("cancelButton");

        if (!modal || !span || !confirmButton || !cancelButton) {
            console.error('Elementos do modal não encontrados');
            return;
        }
        modal.style.display = "flex";

        span.onclick = function() {
            modal.style.display = "none";
        }

        cancelButton.onclick = function() {
            modal.style.display = "none";
        }

        window.onclick = function(event) {
            if (event.target == modal) {
                modal.style.display = "none";
            }
        }

        confirmButton.onclick = function() {
            fetch('/admin/api/usuarios/' + id + '/senhaReset', { method: 'PUT' })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Erro ao resetar senha');
                    }
                    return response.json();
                })
                .then(() => {
                    alert("Senha do usuário resetada com sucesso!");
                    modal.style.display = "none";
                    tirarFiltros();
                    carregarUsuarios(curPageElement.value);
                })
                .catch(error => {
                    console.error('Erro ao resetar senha:', error);
                });

            }
    } else {
        alert("admin principal")
    }
//    Swal.fire({
//        title: 'Deseja resetar a senha deste usuário?',
//        showDenyButton: false,
//        showCancelButton: true,
//        confirmButtonText: 'Sim',
//    }).then((result) => {
//        if (result.isConfirmed) {
//            fetch('/admin/api/usuarios/' + id + '/senhaReset', { method: 'PUT' })
//                .then(response => {
//                    if (!response.ok) {
//                        throw new Error('Erro ao resetar senha');
//                    }
//                    return response.json();
//                })
//                .then(() => {
//                    alert("Senha do usuário resetada com sucesso!");
//                    tirarFiltros();
//                    carregarUsuarios(curPageElement.value);
//                })
//                .catch(error => {
//                    console.error('Erro ao resetar senha:', error);
//                });
//        }
//    })
}


const form = document.getElementById("form-alterar-usuario");
const aNome = document.getElementById("a-nome");
const aSobrenome = document.getElementById("a-sobrenome");
const aEmail = document.getElementById("a-email");
const aCargo = document.getElementById("a-cargo");
const aBsalvar = document.getElementById("Bsalvar");

function alterarUsuario(id){
    if(id != 1){


        form.style.display = "block";
        fetch('/admin/api/usuarios/' + id)
          .then(response => {
            if (!response.ok) {
              throw new Error('Não foi possível obter o usuário');
            }
            return response.json();
          })
          .then(data => {
            aNome.value = data.nome;
            aSobrenome.value = data.sobrenome;
            aEmail.value = data.email;
            switch (data.role) {
                case "ADMIN":
                    indiceOpcaoSelecionada = 0;
                    break;
                case "ALUNO":
                    indiceOpcaoSelecionada = 1;
                    break;
                case "PROFESSOR":
                    indiceOpcaoSelecionada = 2;
                    break;
                default:
                    indiceOpcaoSelecionada = 0;
            }
            aCargo.selectedIndex = indiceOpcaoSelecionada;
            aBsalvar.onclick = function() {
                salvarMudanca(id);
            };
          })
          .catch(error => {
            console.error('Erro:', error);
          });
    } else {
        alert("Admin principal");
    }
}

function cancelar(){
    form.style.display = "none";
}

function validateEmail(email) {
    const re = /\S+@\S+\.\S+/;
    return re.test(String(email).toLowerCase());
}

function salvarMudanca(id){
    var usuario = {
      nome:aNome.value,
      sobrenome:aSobrenome.value,
      role:aCargo.value,
      email:aEmail.value
    };

    if(validateEmail(usuario.email)){
        fetch('/admin/api/usuarios/' + id + '/alterarUsuario', { method: 'PUT' , headers: {'Content-Type': 'application/json'}, body: JSON.stringify(usuario)})
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erro ao alterar usuário.');
                }
                return response.json();
            })
            .then(() => {
                tirarFiltros();
                carregarUsuarios(curPageElement.value);
                cancelar();
            })
            .catch(error => {
                console.error('Erro ao alterar usuário: ', error);
            });


    } else {
        alert("Insira um email no formato certo.")
    }




}




