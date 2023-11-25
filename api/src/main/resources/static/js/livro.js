function obterParametrosURL() {
  const search = window.location.search.substring(1);
  const partes = search.split('&');
  const params = {};

  for (const parte of partes) {
      const [chave, valor] = parte.split('=');
      params[chave] = decodeURIComponent(valor);
  }

  return params;
}

document.addEventListener('DOMContentLoaded', function() {
  const parametrosURL = obterParametrosURL();
  const codigoLivro = parametrosURL.codigo;

  fetch(`/busca-livro/${codigoLivro}`)
      .then(response => response.json())
      .then(data => {
        const livroDestaque = document.querySelector('.livro');

        const imagemProduto = livroDestaque.querySelector('.imagem-produto');
        const nomeLivro = livroDestaque.querySelector('.nome-livro');
        const preco = livroDestaque.querySelector('.preco');
        const sinopse = livroDestaque.querySelector('.sinopse');
        const disponibilidade = livroDestaque.querySelector('.disponibilidade');

        imagemProduto.src = `./images/livros/${data.imagem}`;
        imagemProduto.alt = `Imagem de ${data.titulo}`;
        nomeLivro.textContent = data.titulo;
        preco.textContent = `R$ ${data.preco.toFixed(2)}`;
        sinopse.textContent = data.descricao;
        disponibilidade.textContent = 'Em estoque';
      })
      .catch(error => {
          console.error('Ocorreu um erro ao carregar os detalhes do livro:', error);
      });
});
