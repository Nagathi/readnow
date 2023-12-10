const formulario = document.querySelector('#formulario-1');
const modalMessage = document.getElementById("modal-message");
const closeButton = document.querySelector(".close");

formulario.addEventListener('submit', function(event) {

  event.preventDefault();
 
  var pagina;
  const paginaFinalizacaoPedido = localStorage.getItem('paginaFinalizacao') || null;

  if(paginaFinalizacaoPedido != null){
    pagina = localStorage.getItem('paginaFinalizacao');
  }
  else{
    pagina = "/enderecos";
  }
  const email = localStorage.getItem('email');

  const nomeDestino = document.getElementById('nome').value;
  const telefone = document.getElementById('numero-telefone').value;
  const logradouro = document.getElementById('endereco').value;
  const bairro = document.getElementById('bairro').value;
  const numeroCasa = document.getElementById('numero-residencia').value;
  const cep = document.getElementById('cep').value;
  const complemento = document.getElementById('complemento').value;
  const cidade = document.getElementById('cidade').value;
  const estado = document.getElementById('estado').value;
  const pais = document.getElementById('pais').value;

  const novoEndereco = {
    email: email,
    nomeDestino: nomeDestino,
    logradouro: logradouro,
    telefone: telefone,
    bairro: bairro,
    numeroCasa: numeroCasa,
    cep: cep,
    complemento: complemento,
    cidade: cidade,
    estado: estado,
    pais: pais
  };

  fetch('/cadastra-endereco', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(novoEndereco)
  })
  .then(response => {
    if (response.ok) {
        window.location.href = pagina;
    } else {
      throw new Error('Erro ao cadastrar endereço');
    }
  })
  .catch(error => {
    console.error('Erro:', error);
  });
 
});

async function consultarCep(cep) {
  try {
    const response = await fetch(`https://viacep.com.br/ws/${cep}/json`, { mode: 'cors' });
    const data = await response.json();
    preencherCamposEndereco(data);
  } catch (error) {
    modalMessage.textContent = "Erro ao obter o CEP";
    modal.style.display = "block";
  }
}

function preencherCamposEndereco(data) {
  if (!data.erro) {
    document.getElementById('endereco').value = data.logradouro || "";
    document.getElementById('bairro').value = data.bairro || "";
    document.getElementById('numero-residencia').value = ""; 
    document.getElementById('complemento').value = data.complemento || "";
    document.getElementById('cidade').value = data.localidade || "";
    document.getElementById('estado').value = data.uf || "";
    document.getElementById('pais').value = "Brasil"; 
  } else {
    document.getElementById('endereco').value = "";
    document.getElementById('bairro').value = "";
    document.getElementById('numero-residencia').value = "";
    document.getElementById('complemento').value = "";
    document.getElementById('cidade').value = "";
    document.getElementById('estado').value = "";
    document.getElementById('pais').value = "";
    modalMessage.textContent = "CEP não encontrado!";
    modal.style.display = "block";  }
}

function closeModal() {
  modal.style.display = "none";
}
closeButton.addEventListener("click", closeModal);
window.addEventListener("click", function (event) {
  if (event.target === modal) {
    closeModal();
  }
});
