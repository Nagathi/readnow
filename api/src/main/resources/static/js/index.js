document.addEventListener('DOMContentLoaded', function() {
  const listaLivros = document.querySelector('.lista-livros');
  let inicioExibicao = 0;
  const quantidadeExibicao = 5;

  function exibirLivros(data, inicio) {

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
      const btnVerMais = document.querySelector('.btn-ver-mais');
      btnVerMais.disabled = true;
    }
  }

  fetch('/livros')
    .then(response => response.json())
    .then(data => {
      exibirLivros(data, inicioExibicao);

      const btnVerMais = document.querySelector('.btn-ver-mais');
      btnVerMais.addEventListener('click', function() {
        inicioExibicao += quantidadeExibicao;
        exibirLivros(data, inicioExibicao);
      });

      if (inicioExibicao >= data.length) {
        btnVerMais.disabled = true;
      }
    })
    .catch(error => {
      console.error('Ocorreu um erro ao obter os livros:', error);
    });
});
