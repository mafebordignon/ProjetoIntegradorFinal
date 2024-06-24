function UpdateItem() {
  document.getElementById('edit-texto').style.display = 'none';
  document.getElementById('edit-texto-marca').style.display = 'none';
  document.getElementById('edit-texto-potencia').style.display = 'none';
  document.getElementById('edit-texto-status').style.display = 'none';
  document.getElementById('edit-texto-item').style.display = 'none';
  document.getElementById('edit-texto-local-atual').style.display = 'none';
  document.getElementById('edit-texto-modelo').style.display = 'none';
  document.getElementById('edit-texto-nf').style.display = 'none';
  document.getElementById('edit-texto-disponivel').style.display = 'none';
  document.getElementById('edit-texto-data').style.display = 'none';
  document.getElementById('edit-texto-utensilio').style.display = 'none';
  document.getElementById('edit-texto-comentario').style.display = 'none';
  document.getElementById('edit-texto-data-nf').style.display = 'none';

  document.getElementById('edit-origem').style.display = 'block';
  document.getElementById('edit-marca').style.display = 'block';
  document.getElementById('edit-potencia').style.display = 'block';
  document.getElementById('edit-status').style.display = 'block';
  document.getElementById('edit-item').style.display = 'block';
  document.getElementById('edit-local-atual').style.display = 'block';
  document.getElementById('edit-modelo').style.display = 'block';
  document.getElementById('edit-nf').style.display = 'block';
  document.getElementById('edit-disponivel').style.display = 'block';
  document.getElementById('edit-data').style.display = 'block';
  document.getElementById('edit-utensilio').style.display = 'block';
  document.getElementById('edit-comentario').style.display = 'block';
  document.getElementById('edit-data-nf').style.display = 'block';

  document.getElementById('saveBtn').style.display = 'block';
  document.querySelector('.update-item').style.display = 'none';
  
  //document.getElementById('fileIcon').style.display = 'none';
  // Preencher o campo de edição com o texto atual
  document.getElementById('edit-origem').value = document.getElementById('edit-texto').textContent.trim();
  document.getElementById('edit-marca').value = document.getElementById('edit-texto-marca').textContent.trim();
  document.getElementById('edit-potencia').value = document.getElementById('edit-texto-potencia').textContent.trim();
  document.getElementById('edit-status').value = document.getElementById('edit-texto-status').textContent.trim();
  document.getElementById('edit-item').value = document.getElementById('edit-texto-item').textContent.trim();
  document.getElementById('edit-local-atual').value = document.getElementById('edit-texto-local-atual').textContent.trim();
  document.getElementById('edit-modelo').value = document.getElementById('edit-texto-modelo').textContent.trim();
  document.getElementById('edit-nf').value = document.getElementById('edit-texto-nf').textContent.trim();
  document.getElementById('edit-data').value = document.getElementById('edit-texto-data').textContent.trim();
  document.getElementById('edit-utensilio').value = document.getElementById('edit-texto-utensilio').textContent.trim();
  document.getElementById('edit-data-nf').value = document.getElementById('edit-texto-data-nf').textContent.trim();
  document.getElementById('edit-comentario').value = document.getElementById('edit-texto-comentario').textContent.trim();

  
  
  fetch('/disponibilidades')
  .then(response => {
      if (!response.ok) {
          throw new Error('Erro ao buscar as opções.');
      }
      return response.json();
  })
  .then(options => {
      var flagUtensilio = 1;
      var select = document.getElementById('edit-disponivel');
      select.innerHTML = ''; // Limpa as opções existentes
      options.forEach(option => {
       
          var optionElement = document.createElement('option');
          optionElement.value = option.id;
          optionElement.textContent = option.nome;

          if (option.nome === document.getElementById('edit-texto-disponivel').innerText) {
            flagUtensilio = option.id
          }
          select.appendChild(optionElement);
      });
      select.value =  flagUtensilio;

      select.style.display = 'block'; // Exibe o select


  })
  .catch(error => {
      console.error('Erro:', error);
  });

  fetch('/categorias')
  .then(response => {
      if (!response.ok) {
          throw new Error('Erro ao buscar as opções.');
      }
      return response.json();
  })
  .then(options => {
      var flagUtensilio = 1;
      var select = document.getElementById('edit-utensilio');
      select.innerHTML = ''; // Limpa as opções existentes
      options.forEach(option => {
        if (option.nome === document.getElementById('edit-texto-utensilio').innerText) {
          flagUtensilio = option.id
        }
          var optionElement = document.createElement('option');
          optionElement.value = option.id;
          optionElement.textContent = option.nome;
          select.appendChild(optionElement);
      });
      select.value =  flagUtensilio;

      select.style.display = 'block'; // Exibe o select
  })
  .catch(error => {
      console.error('Erro:', error);
  });

  fetch('/estados')
  .then(response => {
      if (!response.ok) {
          throw new Error('Erro ao buscar as opções.');
      }
      return response.json();
  })
  .then(options => {
      var flagStatus = 1;
      var select = document.getElementById('edit-status');
      select.innerHTML = ''; // Limpa as opções existentes
      options.forEach(option => {
        if (option.nome === document.getElementById('edit-texto-status').innerText) {
          flagStatus = option.id
        }
          var optionElement = document.createElement('option');
          optionElement.value = option.id;
          optionElement.textContent = option.nome;
          select.appendChild(optionElement);
      });
      select.value =  flagStatus;

      select.style.display = 'block'; // Exibe o select
  })
  .catch(error => {
      console.error('Erro:', error);
  });

  fetch('/localizacoes')
  .then(response => {
      if (!response.ok) {
          throw new Error('Erro ao buscar as opções.');
      }
      return response.json();
  })
  .then(options => {
      var flagOrigem = 1; //
      var select = document.getElementById('edit-origem');
      select.innerHTML = ''; // Limpa as opções existentes
      options.forEach(option => {
          if (option.nome === document.getElementById('edit-texto').innerText){ //
            flagOrigem = option.id; //
          } //
          var optionElement = document.createElement('option');
          optionElement.value = option.id;
          optionElement.textContent = option.nome;
          select.appendChild(optionElement);
      });
      select.value = flagOrigem; //

      select.style.display = 'block'; // Exibe o select
  })
  .catch(error => {
      console.error('Erro:', error);
  });
 var campoMarca = document.getElementById('edit-marca')
    fetch('/api/register/marcas')
            .then(response => response.json())
            .then(options => preencherSelect(campoMarca, options))
            .catch(error => console.error('Erro ao buscar marcas:', error));

  document.getElementById('edit-marca').addEventListener('change', (event) => {
        var campoModelo = document.getElementById('edit-modelo')
        const selectedValue = event.target.value;
        fetch('/api/register/modelos/' + campoMarca.value)
        .then(response => response.json())
        .then(options => preencherSelect(campoModelo, options))
        .catch(error => console.error('Erro ao buscar marcas:', error));
        console.log('Você selecionou: ' + selectedValue);
    });
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


function saveChanges() {
salvarItem();

  // Pegar o valor editado
  selectOrigem = document.getElementById("edit-origem");
  optionOrigem = selectOrigem.options[selectOrigem.selectedIndex];
  var newOrigem = optionOrigem.textContent;
  var newMarca = document.getElementById('edit-marca').value;
  var newPotencia = document.getElementById('edit-potencia').value;
  selectStatus = document.getElementById("edit-status");
  optionStatus = selectStatus.options[selectStatus.selectedIndex];
  var newStatus = optionStatus.textContent;
  var newItem = document.getElementById('edit-item').value;
  var newLocalAtual = document.getElementById('edit-local-atual').value;
  var newModelo = document.getElementById('edit-modelo').value;
  var newNf = document.getElementById('edit-nf').value;
  selectDisponivel = document.getElementById("edit-disponivel");
  optionDisponivel = selectDisponivel.options[selectDisponivel.selectedIndex];
  var newDisponivel = optionDisponivel.textContent;
  var newData = document.getElementById('edit-data').value;
  selectUtensilio = document.getElementById("edit-utensilio");
  optionUtensilio = selectUtensilio.options[selectUtensilio.selectedIndex];
  var newUtensilio = optionUtensilio.textContent;
  var newDataNf = document.getElementById('edit-data-nf').value;
  var newComentario = document.getElementById('edit-comentario').value;

  // Atualizar o texto visível
carregarItem();


// Ocultar o campo de edição
  document.getElementById('edit-texto').style.display = 'block';
  document.getElementById('edit-texto-marca').style.display = 'block';
  document.getElementById('edit-texto-potencia').style.display = 'block';
  document.getElementById('edit-texto-status').style.display = 'block';
  document.getElementById('edit-texto-item').style.display = 'block';
  document.getElementById('edit-texto-local-atual').style.display = 'block';
  document.getElementById('edit-texto-modelo').style.display = 'block';
  document.getElementById('edit-texto-nf').style.display = 'block';
  document.getElementById('edit-texto-disponivel').style.display = 'block';
  document.getElementById('edit-texto-data').style.display = 'block';
  document.getElementById('edit-texto-utensilio').style.display = 'block';
  document.getElementById('edit-texto-data-nf').style.display = 'block';
  document.getElementById('edit-texto-comentario').style.display = 'block';

  document.getElementById('edit-origem').style.display = 'none';
  document.getElementById('edit-marca').style.display = 'none';
  document.getElementById('edit-potencia').style.display = 'none';
  document.getElementById('edit-status').style.display = 'none';
  document.getElementById('edit-item').style.display = 'none';
  document.getElementById('edit-local-atual').style.display = 'none';
  document.getElementById('edit-modelo').style.display = 'none';
  document.getElementById('edit-nf').style.display = 'none';
  document.getElementById('edit-disponivel').style.display = 'none';
  document.getElementById('edit-data').style.display = 'none';
  document.getElementById('edit-utensilio').style.display = 'none';
  document.getElementById('edit-data-nf').style.display = 'none';
  document.getElementById('edit-comentario').style.display = 'none';


// Ocultar botão salvar
  document.getElementById('saveBtn').style.display = 'none';
  // Mostrar botão editar
  document.querySelector('.update-item').style.display = 'block';
}

function cancelEdit() {
  // Cancelar edição
  document.getElementById('edit-texto').style.display = 'block';
  document.getElementById('edit-texto-marca').style.display = 'block';
  document.getElementById('edit-texto-potencia').style.display = 'block';
  document.getElementById('edit-texto-status').style.display = 'block';
  document.getElementById('edit-texto-item').style.display = 'block';
  document.getElementById('edit-texto-local-atual').style.display = 'block';
  document.getElementById('edit-texto-modelo').style.display = 'block';
  document.getElementById('edit-texto-nf').style.display = 'block';
  document.getElementById('edit-texto-disponivel').style.display = 'block';
  document.getElementById('edit-texto-data').style.display = 'block';
  document.getElementById('edit-texto-utensilio').style.display = 'block';
  document.getElementById('edit-texto-data-nf').style.display = 'block';
  document.getElementById('edit-texto-comentario').style.display = 'block';

  
  document.getElementById('edit-origem').style.display = 'none';
  document.getElementById('edit-marca').style.display = 'none';
  document.getElementById('edit-potencia').style.display = 'none';
  document.getElementById('edit-status').style.display = 'none';
  document.getElementById('edit-item').style.display = 'none';
  document.getElementById('edit-local-atual').style.display = 'none';
  document.getElementById('edit-modelo').style.display = 'none';
  document.getElementById('edit-nf').style.display = 'none';
  document.getElementById('edit-disponivel').style.display = 'none';
  document.getElementById('edit-data').style.display = 'none';
  document.getElementById('edit-utensilio').style.display = 'none';
  document.getElementById('edit-data-nf').style.display = 'none';
  document.getElementById('edit-comentario').style.display = 'none';


  document.getElementById('saveBtn').style.display = 'none';
  document.querySelector('.update-item').style.display = 'block';
}

const campoOrigem = document.getElementById("edit-texto")
const campoMarca = document.getElementById("edit-texto-marca")
const campoPotencia = document.getElementById("edit-texto-potencia")
const campoStatus = document.getElementById("edit-texto-status")
const campoItem = document.getElementById("edit-texto-item")
const campoLocalAtual = document.getElementById("edit-texto-local-atual")
const campoModelo = document.getElementById("edit-texto-modelo")
const campoNf = document.getElementById("edit-texto-nf")
const campoDisponibilidade = document.getElementById("edit-texto-disponivel")
const campoDataEntradal = document.getElementById("edit-texto-data")
const campoCategoria = document.getElementById("edit-texto-utensilio")
const campoDataNf = document.getElementById("edit-texto-data-nf")
const campoComentario = document.getElementById("edit-texto-comentario")

var id;
var itemCarregado;
let url = window.location.href;
  let parts = url.split('/');
  id = parts[parts.length - 1];

function carregarItem() {
  fetch('/itens/'+id)
   .then(response => {
       if (!response.ok) {
         throw new Error('Erro ao buscar dados do servidor');
       }
       return response.json();
     })
     .then(data => {
      console.log(data);
       itemCarregado = data
       campoOrigem.innerText = data.localizacao.nome
       campoMarca.innerText = data.modelo.marca.nome
       campoPotencia.innerText = data.potencia
       campoStatus.innerText = data.estado.nome
       campoItem.innerText = data.descricao
       const titulo = document.querySelector(".name-item")
       titulo.innerHTML = data.descricao
       campoLocalAtual.innerText = data.localizacao.id
       campoModelo.innerText = data.modelo.nome
       campoNf.innerText = data.numeroNotaFiscal
       campoDisponibilidade.innerText = data.disponibilidade.nome
       campoDataEntradal.innerText = (data.dataEntrada).substring(0, 10);
       campoCategoria.innerText = data.categoria.nome
       campoDataNf.innerText = (data.dataNotaFiscal).substring(0,10)

       atualizarBotaoAcao()
       console.log(data);
     })
     .catch(error => {
       console.error('Erro:', error);
     });

     fetch('/acoes/'+id)
     .then(response => {
      if (!response.ok) {
        throw new Error('Erro ao buscar dados do servidor');
      }
      return response.json();
    })
    .then(data => {

      if((data.content).length == 0){
        campoLocalAtual.innerText = itemCarregado.localizacao.nome;
        console.log(data.content.length)

      } else {
        campoLocalAtual.innerText = data.content[0].localizacao.nome;

      }

    })
    .catch(error => {
      console.error('Erro:', error);
    });

    fetch('/especificacao/manutencao/'+id)
    .then(response => {
      if (!response.ok) {
        campoComentario.innerText = "Não houve manutenções";
        throw new Error('Erro ao buscar dados do servidor');
      }
      return response.json();
    })
    .then(data => {
        const manutencaoButton = document.getElementById("manutencao-button");
      console.log(data);
      console.l
      if (data.dataFim == null || data.dataFim == ""){
        manutencaoButton.onclick = () => {
            alert("Há uma manutenção inacabada");
            window.location.href = "/manutencao/"+data.id;
        }
        campoComentario.innerText = data.descricaoProblema;
      } else {
        campoComentario.innerText = data.descricaoSolucao;
      }
    })
    .catch(error => {
      console.error('Erro:', error);
    });
} 

window.onload = carregarItem();

function carregarHistorico() {
 const containerHistorico = document.querySelector('.container-historico');
 containerHistorico.innerHTML = '';
fetch('/acoes/'+id)
     .then(response => {
      if (!response.ok) {
        throw new Error('Erro ao buscar dados do servidor');
      }
      return response.json();
    })
    .then(data => {
      data.content.forEach(campo => {
        box = document.createElement("div")
        box.className = "box-historico"
        historicoItem = document.createElement("p")

        var string1;
            if(campo.tipoacao.id == 1){
                string1 = `<strong>${campo.entidade}</strong> fez um(a) <strong>${campo.tipoacao.nome}</strong> no dia ${campo.dataEmprestimo.substring(0,10)} às ${campo.dataEmprestimo.substring(11,19)}. Descrição: ${campo.descricao}`;
            } else {
                string1 = `<strong>${campo.entidade}</strong> fez um(a) <strong>${campo.tipoacao.nome}</strong> no dia ${campo.dataDevolucao.substring(0,10)} às ${campo.dataDevolucao.substring(11,19)}. Descrição: ${campo.descricao}`;
            }
        historicoItem.innerHTML = string1;
        console.log(string1);
        // Daniel do(a) Empresa Fish-Inos Emprestou para Empresa Fish-Inos no dia 10/07/2023 as 13:30h.
        // (data.dataEntrada).substring(0, 10);
        

        // Adicionando o parágrafo ao container-historico
        box.appendChild(historicoItem);
        containerHistorico.appendChild(box);
        
    });
    })
    .catch(error => {
      console.error('Erro:', error);
    });
}

function showSection(section) {
// Esconde todas as seções
document.querySelectorAll('.container-historico').forEach(function(sec) {
    sec.style.display = 'none';
});

// Mostra a seção selecionada
document.getElementById(section + 'Section').style.display = 'block';

// Carrega o histórico se a seção de histórico for selecionada
if (section === 'historico') {
    carregarHistorico();
}

  // Carrega o histórico se a seção de histórico for selecionada
  if (section === 'manutencao') {
    carregarManutencao();
}
}

function handleFileUpload(files) {
const fileList = document.getElementById('fileList');
fileList.innerHTML = ''; // Limpa a lista de arquivos

for (let i = 0; i < files.length; i++) {
    const li = document.createElement('li');
    const link = document.createElement('a');
     link.className="box-historico"
    link.href = URL.createObjectURL(files[i]);
    link.download = files[i].name;
    link.textContent = files[i].name;
    li.appendChild(link);
    fileList.appendChild(li);
}
}



// Inicialmente mostra a seção de Histórico
showSection('historico');


const campoOrigemAlterado = document.getElementById("edit-origem")
const campoMarcaAlterado = document.getElementById("edit-marca")
const campoPotenciaAlterado = document.getElementById("edit-potencia")
const campoStatusAlterado = document.getElementById("edit-status")
const campoItemAlterado = document.getElementById("edit-item")
const campoLocalAtualAlterado = document.getElementById("edit-local-atual")
const campoModeloAlterado = document.getElementById("edit-modelo")
const campoNfAlterado = document.getElementById("edit-nf")
const campoDisponibilidadeAlterado = document.getElementById("edit-disponivel")
const campoDataEntradalAlterado = document.getElementById("edit-data")
const campoCategoriaAlterado = document.getElementById("edit-utensilio")
const campoDataNfedit = document.getElementById("edit-data-nf")


function salvarItem() {
  itemAtualizado = {
  descricao: campoItemAlterado.value,
  numeroNotaFiscal: campoNfAlterado.value,
  potencia: campoPotenciaAlterado.value,
  dataEntrada: (campoDataEntradalAlterado.value)+ "T00:00:00",
  dataNotaFiscal: (campoDataNfedit.value) + "T00:00:00",
  modelo:
  {
    id: campoModeloAlterado.value
  },
  estado: {
    id: campoStatusAlterado.value
  },
  disponibilidade: {
    id: campoDisponibilidadeAlterado.value
  },
  localizacao: {
    id: campoOrigemAlterado.value
  },
  categoria: {
    id: campoCategoriaAlterado.value
  }
};

console.log("item atualizado: "+itemAtualizado.modelo.id)

idItemAtualCategoria = (itemCarregado.categoria.id).toString();
idItemAtualStatus = (itemCarregado.estado.id).toString();
idItemAtualDisponivilidade = (itemCarregado.disponibilidade.id).toString();
idItemAtualLocalizacao = (itemCarregado.localizacao.id).toString();

itemAtual = {
  descricao: campoItem.innerText,
  numeroNotaFiscal: campoNf.innerText,
  marca: campoMarca.innerText,
  modelo: campoModelo.innerText,
  numeroSerie: "campoAlterado.value",
  potencia: campoPotencia.innerText,
  dataEntrada: (campoDataEntradal.innerText)+ "T00:00:00",
  categoria:  
  {
    id: idItemAtualCategoria
  },
  estado: {
    id: idItemAtualStatus
  },
  disponibilidade: {
    id: idItemAtualDisponivilidade
  },
  localizacao: {
    id: idItemAtualLocalizacao
  }
};
console.log(JSON.stringify(itemAtual));

console.log(JSON.stringify(itemAtualizado));
if (JSON.stringify(itemAtualizado) === JSON.stringify(itemAtual)) {
  alert("Item Está igual;")
}  else if(itemAtualizado.modelo.id == null || itemAtualizado.modelo.id == "" || itemAtualizado.descricao == null || itemAtualizado.numeroNotaFiscal == null || itemAtualizado.potencia == null || itemAtualizado.dataNotaFiscal == ":00" || itemAtualizado.dataEntrada  == ":00"){
  alert("Há campos vazios")
}else {
  fetch('/itens/'+id, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(itemAtualizado)
  })
  .then(response => {
    if (!response.ok) {
      throw new Error('Erro ao atualizar item');
    }
    return response.json();
  })
  .then(data => {
    carregarItem()
    console.log('Item atualizado com sucesso:', data);
  })
  .catch(error => {
    console.error('Erro:', error);
  });
}




}


function AtualizarItem() {
  fetch('itens/'+id, {
  method: 'PUT',
  headers: {
      'Content-Type': 'application/json'
  },
  body: JSON.stringify(itemAtualizado)
  })
  .then(response => {
    if (!response.ok) {
      throw new Error('Erro ao atualizar o item');
    }
    return response.json();
  })
  .then(data => {
    alert("sucesso");
    console.log('Item atualizado com sucesso:', data);
  })
  .catch(error => {
  console.error('Erro:', error);
});
}


const disponibilidadeSelect = document.getElementById('edit-disponivel');
// Selecionando o botão de atualizar item
const botaoAtualizarItem = document.getElementById('updateBtn');

// Adicionando um ouvinte de evento ao campo de disponibilidade
disponibilidadeSelect.addEventListener('change', function() {
   // Obtendo o valor selecionado
   const selecionado = this.value;

   // Verificando o valor selecionado e atualizando o texto do botão correspondente
   if (selecionado === 'disponivel') {
       botaoAtualizarItem.textContent = '+ Emprestar Item';
   } else if (selecionado === 'emprestado') {
       botaoAtualizarItem.textContent = '+ Devolução';
   }
});


function acao(){
      window.location.href = "/devolucao/"+id;
}

document.getElementById("excel").onclick = function() {
const url = '/gerar-relatorio/especifico/' + id;

fetch(url, {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json'
    }
})
.then(response => {
    if (!response.ok) {
        throw new Error('Erro ao baixar o arquivo específico');
    }
    return response.blob();
})
.then(blob => {
    // Criar um link temporário
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.style.display = 'none';
    a.href = url;
    a.download = 'relatorio_especifico_' + id + '.xlsx'; // Nome do arquivo
    document.body.appendChild(a);
    a.click();
    window.URL.revokeObjectURL(url);
})
.catch(error => {
    console.error('Erro:', error);
    // Lidar com erros, se necessário
});
}


function showPopup() {
  var element = document.getElementById("popup");
  element.classList.add("show-popup");
}

function hidePopup() {
  var element = document.getElementById("popup");
  element.classList.remove("show-popup");

}

function saveManutencao() {
  var motivo = document.getElementById('motivo').value;
  var simChecked = document.getElementById('radio-sim').checked;
  
  if (!simChecked) {
      alert('Você precisa selecionar "Sim" para solicitar a manutenção.');
      return;
  }

  if (motivo.trim() === '') {
      alert('O campo Motivo é obrigatório!');
      return;
  }

  
  const manutencao = {
    dataInicio: datadehoje(), 
    descricaoProblema: motivo,
    item: {
      id: id
    }
  }

  fetch('/api/manutencoes/abrir', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json'
    },
    body: JSON.stringify(manutencao)
  })
  .then(response => {
    if (!response.ok) {
        throw new Error('Erro na requisição');
    }
    return response.json();
  })
  .then(data => {
    const idManutencao = data.id;

    window.location.href = "/manutencao/"+idManutencao;
  })
  .catch(error => {
    console.error('Erro:', error);
    // Aqui você pode adicionar um tratamento de erro para o usuário, se necessário
  });
}


function datadehoje() {
  var dataAtual = new Date();

  // Extrai o ano, mês, dia, horas, minutos e segundos da data atual
  var ano = dataAtual.getFullYear();
  var mes = ('0' + (dataAtual.getMonth() + 1)).slice(-2); // Os meses começam do zero, então adicione 1 e formate com dois dígitos
  var dia = ('0' + dataAtual.getDate()).slice(-2); // Formata o dia com dois dígitos
  var horas = ('0' + dataAtual.getHours()).slice(-2); // Formata as horas com dois dígitos
  var minutos = ('0' + dataAtual.getMinutes()).slice(-2); // Formata os minutos com dois dígitos
  var segundos = ('0' + dataAtual.getSeconds()).slice(-2); // Formata os segundos com dois dígitos
  
  // Formata a data no formato desejado (exemplo: YYYY-MM-DDTHH:MM:SS)
  var dataFormatada = `${ano}-${mes}-${dia}T${horas}:${minutos}:${segundos}`;

  return dataFormatada;
}

function carregarManutencao() {
  fetch('/especificacao/manutencaoLista/'+id)
    .then(response => {
      if (!response.ok) {
        throw new Error('Erro ao buscar dados do servidor');
      }
      return response.json();
    })
    .then(data => {
      const containerManutencao = document.getElementById('manutencaoSection');
      containerManutencao.innerHTML = ''; // Limpa o container antes de adicionar novos elementos

      data.forEach(campo => {
        console.log(campo);
        const box = document.createElement("div");
        box.className = "box-manutencao";
        const manutencaoItem = document.createElement("a");

        manutencaoItem.href="/manutencao/"+campo.id;
        var string1;
        if(campo.dataMandado == null){
            string1 = `<strong>${campo.item.descricao}</strong> está <strong>precisando de manutencao desde </strong> o dia: ${campo.dataInicio.substring(0,10)}. O problema relatado é: ${campo.descricaoProblema}.`;

        } else if (campo.dataMandado != null && campo.dataFim == null) {
            string1 = `<strong>${campo.item.descricao}</strong> foi <strong>mandado pra manutencao </strong>no dia: ${campo.dataMandado.substring(0,10)}, para o Técnico responsável: ${campo.tecnico}, com data de conserto esperada para o dia ${campo.dataEsperada.substring(0,10) }. O problema relatado é: ${campo.descricaoProblema}.`;

        } else if (campo.dataMandado != null && campo.dataFim != null){
            string1 = `<strong>${campo.item.descricao}</strong> <strong>retornou da manutencao </strong>no dia: ${campo.dataFim.substring(0,10)}. O problema relatado era: ${campo.descricaoProblema}. A solução foi: ${campo.descricaoSolucao}`;

        }


        manutencaoItem.innerHTML = string1;
        // Adicionando o parágrafo ao container-historico
        box.appendChild(manutencaoItem);
        containerManutencao.appendChild(box);
      });

      // Exibe a seção de manutenção
      containerManutencao.style.display = 'block';
    })
    .catch(error => {
      console.error('Erro:', error);
    });
}


  function atualizarBotaoAcao() {
    const botao = document.getElementById("acao-button");
    if (document.getElementById('edit-texto-disponivel').innerText == "Emprestado"){
          botao.innerText = "+ Devolução";
    } else if (document.getElementById('edit-texto-disponivel').innerText == "Disponível"||document.getElementById('edit-texto-disponivel').innerText == "Disponivel"){
          botao.innerText = "+ Empréstimo";
    } else {
         botao.innerText = "+ Bloqueado"
          botao.disabled = true;
    }
}

function acao(){
      if (document.getElementById('edit-texto-disponivel').innerText == "Emprestado"){
            window.location.href = "/devolucao/"+id;
      } else if (document.getElementById('edit-texto-disponivel').innerText == "Disponível"||document.getElementById('edit-texto-disponivel').innerText == "Disponivel"){
            window.location.href = "/emprestimo/"+id;
      }
}