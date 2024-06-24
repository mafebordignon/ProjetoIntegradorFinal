
fetch('/ola/api/usuario/nome', {
        method: 'GET',
        credentials: 'same-origin', // Para enviar cookies e session ID
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Erro ao obter nome do usuário: ' + response.statusText);
        }
        return response.text(); // Retorna o texto da resposta
    })
    .then(data => {
        document.getElementById('nomeUsuario').textContent = "Olá, "+data; // Define o texto no elemento HTML
        console.log('Nome do usuário:', data);
    })
    .catch(error => console.error(error)); // Captura e exibe erros no console

fetch('/ola/api/usuario/role', {
        method: 'GET',
        credentials: 'same-origin', // Para enviar cookies e session ID
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Erro ao obter role do usuário: ' + response.statusText);
        }
        return response.text(); // Retorna o texto da resposta
    })
    .then(data => {
        document.getElementById('nav-admin').style.display = "none";
        if( data === "ADMIN"){
            document.getElementById('nav-admin').style.display = "flex";

        }
    })
    .catch(error => console.error(error)); // Captura e exibe erros no console

function toggleNav() {
    var nav = document.getElementById('side-nav');

    if (nav) {
        if (!nav.style.left || nav.style.left === '-10vw') {
            nav.style.left = '0vw';
        } else {
            nav.style.left = '-10vw';
        }
    }
}

function closeNavOnClickOutside(event) {
    var nav = document.getElementById('side-nav');
    var headerNavButton = document.getElementById('header-nav-button');

    if (!nav.contains(event.target) && !headerNavButton.contains(event.target)) {
        nav.style.left = '-10vw';
    }
}

document.body.addEventListener('click', closeNavOnClickOutside);
