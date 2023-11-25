document.addEventListener('DOMContentLoaded', function() {
  let inicioExibicaoAcao = 0;
  let inicioExibicaoFiccao = 0;
  const quantidadeExibicao = 5;

  function exibirLivros(data, inicio, listaLivros) {
    listaLivros.innerHTML = '';

    const finalExibicao = Math.min(inicio + quantidadeExibicao, data.length);

    for (let i = inicio; i < finalExibicao; i++) {
      const livro = data[i];
      const novoLivro = document.createElement('li');
      novoLivro.classList.add('card-livro');

      const caminhoImagem = './images/livros/' + livro.imagem;

      novoLivro.innerHTML = `
        <div class="banner">
          <img src="${caminhoImagem}" alt="Produto" class="imagem">
        </div>
        <div class="detalhes-livro">
          <p class="titulo-livro">${livro.titulo}</p>
          <h3 class="preco">R$${livro.preco}</h3>
          <a href="#" class="ver-produto">Ver produto</a>
        </div>
      `;

      listaLivros.appendChild(novoLivro);
    }

    if (finalExibicao >= data.length) {
      const btnVerMais = listaLivros.parentElement.querySelector('.btn-ver-mais');
      btnVerMais.disabled = true;
    }
  }

  fetch('/livros/Ação')
    .then(response => response.json())
    .then(data => {
      const listaLivrosAcao = document.querySelector('.lista-livros-acao');
      exibirLivros(data, inicioExibicaoAcao, listaLivrosAcao);

      const btnVerMaisAcao = document.querySelector('#btn-ver-mais-acao');
      btnVerMaisAcao.addEventListener('click', function() {
        inicioExibicaoAcao += quantidadeExibicao;
        exibirLivros(data, inicioExibicaoAcao, listaLivrosAcao);
      });

      if (inicioExibicaoAcao >= data.length) {
        btnVerMaisAcao.disabled = true;
      }
    })
    .catch(error => {
      console.error('Ocorreu um erro ao obter os livros de Ação:', error);
    });

  fetch('/livros/Ficção')
    .then(response => response.json())
    .then(data => {
      const listaLivrosFiccao = document.querySelector('.lista-livros-ficcao');
      exibirLivros(data, inicioExibicaoFiccao, listaLivrosFiccao);

      const btnVerMaisFiccao = document.querySelector('#btn-ver-mais-ficcao');
      btnVerMaisFiccao.addEventListener('click', function() {
        inicioExibicaoFiccao += quantidadeExibicao;
        exibirLivros(data, inicioExibicaoFiccao, listaLivrosFiccao);
      });

      if (inicioExibicaoFiccao >= data.length) {
        btnVerMaisFiccao.disabled = true;
      }
    })
    .catch(error => {
      console.error('Ocorreu um erro ao obter os livros de Ficção:', error);
    });
});
