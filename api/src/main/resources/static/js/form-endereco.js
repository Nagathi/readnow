const form = document.getElementById('formulario-1');

form.addEventListener('submit', function(event) {
  event.preventDefault();

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
        window.location.href = "/enderecos";
    } else {
      throw new Error('Erro ao cadastrar endereço');
    }
  })
  .catch(error => {
    console.error('Erro:', error);
  });
});

  