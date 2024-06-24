var manutencao;
var item;
var estado;
var id;
window.onload = function() {
    let url = window.location.href;
    let parts = url.split('/');
    id = parts[parts.length - 1];

    pegarManutencaoPorId(id);
    pegarItemPorManutencao(id);

};

function pegarManutencaoPorId(id){
    const dataInicio = document.getElementById("infoDataInicio");
    const descricaoProblema = document.getElementById("infoDescricaoProblema");
    const section_aberto_naoEmAcao = document.getElementById("aberto-naoEmAcao");
    const section_aberto_EmAcao = document.getElementById("aberto-EmAcao");
    const section_fechado = document.getElementById("editar-fechado");

    const infoTecnico = document.getElementById("infoTecnico");
    const infoDataMandado = document.getElementById("infoDataMandado");
    const infoDataEsperada = document.getElementById("infoDataEsperada");

    const infoDescricaoSolucao = document.getElementById("infoDescricaoSolucao");
    const infoDataFim = document.getElementById("infoDataFim");

    const infoAberto = document.getElementById("infoAberto");
    const infoFechado = document.getElementById("infoFechado");


    fetch("/api/manutencoes/" + id)
        .then(response => {
            if (!response.ok) {
                 window.location.href = "/not-found"
            }
            return response.json();
        })
        .then(data => {
            document.getElementById("link-espec").href = "/especificacao-item/" + data.item.id;
            manutencao = data;
            console.log('Dados da manutenção:', data);

            descricaoProblema.innerText = data.descricaoProblema;
            dataInicio.innerText = formatDate(data.dataInicio);

            infoTecnico.innerText = data.tecnico;
            infoDataMandado.innerText = formatDate(data.dataMandado);
            console.log(formatDate(data.dataMandado))
            infoDataEsperada.innerText = formatDate(data.dataEsperada);
            infoDescricaoSolucao.innerText = data.descricaoSolucao;
            infoDataFim.innerText = formatDate(data.dataFim);

            if(data.dataFim === null && data.emAcao === false) {
                estado = "aberto";
                section_aberto_naoEmAcao.style.display = "block";
            } else if(data.dataFim === null && data.emAcao === true) {
                estado = "aberto-EmAcao";
                infoAberto.style.display = "block";

                section_aberto_EmAcao.style.display = "block";
            } else if(data.dataFim != null) {
                estado = "fechado";
                infoAberto.style.display = "block";
                infoFechado.style.display = "block";

                section_fechado.style.display = "block";
            }
        })
}

function pegarItemPorManutencao(id){
    const descricao = document.getElementById("item-descricao");
    const nf = document.getElementById("item-nf");
    fetch("/api/manutencoes/" + id + "/item")
        .then(response => {
            if (!response.ok) {
                 window.location.href = "/not-found"
            }
            return response.json();
        })
        .then(data => {
            descricao.innerText = data.descricao;
            nf.innerText = data.numeroNotaFiscal
            item = data
            console.log('Dados do item:', data);
        })
}




function formatDate(inputDateStr) {
    // Parse input datetime string into a Date object
    let date = new Date(inputDateStr);

    // Get individual date components
    let day = date.getDate();
    let month = date.getMonth() + 1; // Months are zero based, so we add 1
    let year = date.getFullYear();
    let hours = date.getHours();
    let minutes = date.getMinutes();

    // Ensure two-digit formatting with leading zeros if necessary
    let formattedDay = ('0' + day).slice(-2);
    let formattedMonth = ('0' + month).slice(-2);
    let formattedYear = year;
    let formattedHours = ('0' + hours).slice(-2);
    let formattedMinutes = ('0' + minutes).slice(-2);

    // Construct the formatted date string
    let formattedDate = formattedDay + '-' + formattedMonth + '-' + formattedYear + ' ' + formattedHours + ':' + formattedMinutes;

    return formattedDate;
}


document.getElementById("button-aberto-naoEmAcao").onclick = function(event) {
    event.preventDefault(); // Prevent the form from submitting normally

    const tecnico = document.getElementById("tecnico").value;
    const dataMandado = document.getElementById("dataMandado").value;
    const dataEsperada = document.getElementById("dataEsperada").value;

    manutencao.tecnico = tecnico;
    manutencao.dataMandado = dataMandado+":00";
    manutencao.dataEsperada = dataEsperada+":00";

    console.log(manutencao);
    if(manutencao.tecnico === "" || manutencao.dataMandado === ":00" || manutencao.dataEsperada === ":00"){
        alert("Campo vazio")
    } else {

        fetch('/api/manutencoes/mandar', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(manutencao)
        })
        .then(response => response.json())
        .then(data => {
            location.reload();
            console.log('Success:', data);
        })
        .catch((error) => {
            alert(error)
            console.error('Error:', error);
        });
    }

};


document.getElementById("button-aberto-EmAcao").onclick = function(event) {
    event.preventDefault();



    const descricaoSolucao = document.getElementById("descricaoSolucao").value;
    const dataFim = document.getElementById("dataFim").value;
    const dataProximaManutencao = document.getElementById("dataProximaManutencao").value;

        manutencao.descricaoSolucao = descricaoSolucao;
        manutencao.dataFim = dataFim+":00";
        manutencao.dataProximaManutencao = dataProximaManutencao+":00";
    if(manutencao.descricaoSolucao === "" || manutencao.dataFim === ":00" || manutencao.dataProximaManutencao === ":00"){
        alert("Campo vazio")
    }else{



        fetch('/api/manutencoes/fechar', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(manutencao)
        })
        .then(response => response.json())
        .then(data => {
            location.reload();
            console.log('Success:', data);
        })
        .catch((error) => {
            alert(error)
            console.error('Error:', error);
        });
    }
};

document.getElementById("editar-editar-button").addEventListener("click", function() {

    // Obter os valores atuais para preencher o formulário de edição
    const descricaoProblemaAtual = document.getElementById("infoDescricaoProblema").innerText;
    const descricaoSolucaoAtual = document.getElementById("infoDescricaoSolucao").innerText;
    const dataInicioAtual = document.getElementById("infoDataInicio").innerText;
    const dataMandadoAtual = document.getElementById("infoDataMandado").innerText;
    const dataEsperadaAtual = document.getElementById("infoDataEsperada").innerText;
    const dataFimAtual = document.getElementById("infoDataFim").innerText;
    const tecnicoAtual = document.getElementById("infoTecnico").innerText;

    // Preencher o formulário de edição com os valores atuais
    document.getElementById("editar-descricaoProblema").value = descricaoProblemaAtual;
    document.getElementById("editar-descricaoSolucao").value = descricaoSolucaoAtual;
    document.getElementById("editar-dataInicio").value = formatDateForInput(dataInicioAtual);
    document.getElementById("editar-dataMandado").value = formatDateForInput(dataMandadoAtual);
    document.getElementById("editar-dataEsperada").value = formatDateForInput(dataEsperadaAtual);
    document.getElementById("editar-dataFim").value = formatDateForInput(dataFimAtual);
    document.getElementById("editar-tecnico").value = tecnicoAtual;

    // Exibir o formulário de edição e ocultar o botão "Editar"
    document.getElementById("editar-form-edicao").style.display = "flex";
    document.getElementById("editar-editar-button").style.display = "none";
});

document.getElementById("editar-salvar-edicao").addEventListener("click", function() {
    // Obter os novos valores dos campos de edição
    const descricaoProblemaEdit = document.getElementById("editar-descricaoProblema").value;
    const descricaoSolucaoEdit = document.getElementById("editar-descricaoSolucao").value;
    const dataInicioEdit = document.getElementById("editar-dataInicio").value;
    const dataMandadoEdit = document.getElementById("editar-dataMandado").value;
    const dataEsperadaEdit = document.getElementById("editar-dataEsperada").value;
    const dataFimEdit = document.getElementById("editar-dataFim").value;
    const tecnicoEdit = document.getElementById("editar-tecnico").value;

    manutencao.descricaoProblema = descricaoProblemaEdit;
    manutencao.descricaoSolucao = descricaoSolucaoEdit;
    manutencao.dataInicio = dataInicioEdit + ":00"; // Ajuste para o formato necessário, se aplicável
    manutencao.dataMandado = dataMandadoEdit + ":00"; // Ajuste para o formato necessário, se aplicável
    manutencao.dataEsperada = dataEsperadaEdit + ":00"; // A juste para o formato necessário, se aplicável
    manutencao.dataFim = dataFimEdit + ":00"; // Ajuste para o formato necessário, se aplicável
    manutencao.tecnico = tecnicoEdit;

    console.log(manutencao.dataFim)

    fetch(`/api/manutencoes/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(manutencao)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao atualizar a manutenção');
            }
            return response.json();
        })
        .then(data => {
            console.log('Manutenção atualizada com sucesso:', data);

            // Atualizar a interface com os novos dados, se necessário
            // Exemplo: atualizar os campos de exibição com os novos valores
            document.getElementById("infoDescricaoProblema").innerText = descricaoProblemaEdit;
            document.getElementById("infoDescricaoSolucao").innerText = descricaoSolucaoEdit;
            document.getElementById("infoDataInicio").innerText = formatDate(data.dataInicio);
            document.getElementById("infoDataMandado").innerText = formatDate(data.dataMandado);
            document.getElementById("infoDataEsperada").innerText = formatDate(data.dataEsperada);
            document.getElementById("infoDataFim").innerText = formatDate(data.dataFim);
            document.getElementById("infoTecnico").innerText = tecnicoEdit;

            // Exibir novamente a seção 'fechado' e ocultar o formulário de edição
            document.getElementById("editar-form-edicao").style.display = "none";
            document.getElementById("editar-fechado").style.display = "block";
            document.getElementById("editar-editar-button").style.display = "block";
        })
        .catch(error => {
            console.error('Erro ao atualizar a manutenção:', error);
            // Tratar o erro conforme necessário (exibir mensagem de erro, etc.)
        });

    // Ocultar o formulário de edição e exibir novamente a seção "fechado"
    document.getElementById("editar-form-edicao").style.display = "none";
    document.getElementById("editar-editar-button").style.display = "block";
});

document.getElementById("editar-cancelar-edicao").addEventListener("click", function() {
    // Se o usuário cancelar a edição, apenas ocultar o formulário de edição e exibir novamente a seção "fechado"
    document.getElementById("editar-form-edicao").style.display = "none";
    document.getElementById("editar-editar-button").style.display = "block";

});

function formatDateForInput(inputDateStr) {
    // Dividir a string da data e hora em componentes
    let parts = inputDateStr.split(' ');
    let datePart = parts[0]; // "15-06-2024"
    let timePart = parts[1]; // "20:03"

    // Dividir a parte da data em dia, mês e ano
    let dateParts = datePart.split('-');
    let day = dateParts[0];
    let month = dateParts[1];
    let year = dateParts[2];

    // Dividir a parte do tempo em horas e minutos
    let timeParts = timePart.split(':');
    let hours = timeParts[0];
    let minutes = timeParts[1];

    // Construir um objeto Date a partir dos componentes obtidos
    let formattedDate = new Date(year, month - 1, day, hours, minutes);

    // Formatar para o formato ISO 8601
    let isoFormattedDate = formattedDate.toISOString().slice(0, 16);

    return isoFormattedDate;
}
