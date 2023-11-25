document.addEventListener('DOMContentLoaded', function() {
  const quantidadeExibicao = 5;

  const categorias = ['acao', 'ficcao', 'cultura', 'aventura', 'literatura'];

  const categoriasLivros = {};

  function exibirLivros(data, inicio, listaLivros, btnVerMais) {
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
      btnVerMais.disabled = true;
    }
  }

  categorias.forEach(categoria => {
    const listaLivros = document.querySelector(`.lista-livros-${categoria}`);
    const btnVerMais = document.querySelector(`#btn-ver-mais-${categoria}`);
    categoriasLivros[categoria] = { listaLivros, btnVerMais, inicioExibicao: 0 };

    fetch(`/livros/${categoria}`)
      .then(response => response.json())
      .then(data => {
        const { listaLivros, btnVerMais } = categoriasLivros[categoria];
        exibirLivros(data, 0, listaLivros, btnVerMais);

        btnVerMais.addEventListener('click', function() {
          categoriasLivros[categoria].inicioExibicao += quantidadeExibicao;
          exibirLivros(data, categoriasLivros[categoria].inicioExibicao, listaLivros, btnVerMais);

          if (categoriasLivros[categoria].inicioExibicao >= data.length) {
            btnVerMais.disabled = true;
          }
        });
      })
      .catch(error => {
        console.error(`Ocorreu um erro ao obter os livros de ${categoria}:`, error);
      });
  });
});
