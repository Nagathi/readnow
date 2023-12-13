(() => {
  const listaProblemas = document.querySelectorAll('.problema');
  
  function activeButton() {
    listaProblemas.forEach((button) => {
      button.removeAttribute('id');
      this.id = 'selecionado';
    })
  }
  
  listaProblemas.forEach((item) => {
    item.addEventListener('click', activeButton);
  });

  const urlParams = new URLSearchParams(window.location.search);
  const diaMes = urlParams.get('diaMes');
  const ano = urlParams.get('ano');
  const imagem = urlParams.get('imagem');
  const titulo = urlParams.get('titulo');

  const cardLivro = document.querySelector('.card-livro');
  const imagemProduto = cardLivro.querySelector('.container-imagem img');
  const descricaoProduto = cardLivro.querySelector('.descricao-produto h2');
  const descricaoPedido = cardLivro.querySelector('.descricao-produto p');

  imagemProduto.src = `images/livros/${imagem}`;
  imagemProduto.alt = 'Produto com problema';
  descricaoProduto.textContent = titulo;
  descricaoPedido.textContent = `Pedido em ${diaMes} de ${ano}`;

  const formulario = document.getElementById('formulario-1');

  formulario.addEventListener('submit', (event) => {
    event.preventDefault();

    const tituloHistoria = document.getElementById('titulo-historia').value;
    const descricaoProblema = document.getElementById('descricao-problema').value;
    const opcaoSelecionada = document.querySelector('#selecionado').textContent;

    const dadosFormulario = {
      token: localStorage.getItem('token'),
      codigo: localStorage.getItem('codigoLivro'),
      titulo: tituloHistoria,
      historia: descricaoProblema,
      opcao: opcaoSelecionada
    };
    fetch('/email-ajuda', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(dadosFormulario)
    })
    .then(response => {
      if (response.ok) {
        alert('Formulário enviado com sucesso!');
      } else {
        alert('Falha ao enviar formulário');
      }
    })
    .catch(error => {
      alert('Erro ao enviar formulário:', error);
    });
  });
})();
