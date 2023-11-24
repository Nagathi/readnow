fetch('/livros')
  .then(response => response.json())
  .then(data => {
    const listaLivros = document.querySelector('.lista-livros');

    data.forEach(livro => {
      const novoLivro = document.createElement('li');
      novoLivro.classList.add('card-livro');

      const caminhoImagem = './images/' + livro.imagem;

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
    });
  })
  .catch(error => {
    console.error('Ocorreu um erro ao obter os livros:', error);
  });
