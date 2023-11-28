function obterParametrosURL() {
  const search = window.location.search.substring(1);
  const partes = search.split("&");
  const params = {};

  for (const parte of partes) {
    const [chave, valor] = parte.split("=");
    params[chave] = decodeURIComponent(valor);
  }

  return params;
}
const parametrosURL = obterParametrosURL();
const codigoLivro = parametrosURL.codigo;
document.addEventListener("DOMContentLoaded", function () {
  fetch(`/busca-livro/${codigoLivro}`)
    .then((response) => response.json())
    .then((data) => {
      const livroDestaque = document.querySelector(".livro");

      const imagemProduto = livroDestaque.querySelector(".imagem-produto");
      const nomeLivro = livroDestaque.querySelector(".nome-livro");
      const preco = livroDestaque.querySelector(".preco");
      const sinopse = livroDestaque.querySelector(".sinopse");
      const disponibilidade = livroDestaque.querySelector(".disponibilidade");

      imagemProduto.src = `./images/livros/${data.imagem}`;
      imagemProduto.alt = `Imagem de ${data.titulo}`;
      nomeLivro.textContent = data.titulo;
      preco.textContent = `R$ ${data.preco.toFixed(2)}`;
      sinopse.textContent = data.descricao;
      disponibilidade.textContent = "Em estoque";

      const categoria = data.categoria;
      document.title = data.titulo;

      fetch(`/livros/${categoria}`)
        .then((response) => response.json())
        .then((produtosSimilares) => {
          const listaLivros = document.querySelector(".lista-livros-acao");

          const produtosExibidos = produtosSimilares
            .filter((produto) => produto.codigo !== Number(codigoLivro))
            .slice(0, 6);

          produtosExibidos.forEach((produto) => {
            const novoLivro = document.createElement("li");
            novoLivro.classList.add("card-livro");

            const caminhoImagem = `./images/livros/${produto.imagem}`;

            novoLivro.innerHTML = `
              <div class="banner">
                <img src="${caminhoImagem}" alt="Produto" class="imagem">
              </div>
              <div class="detalhes-livro">
                <p class="titulo-livro">${produto.titulo}</p>
                <h3 class="preco">R$${produto.preco.toFixed(2)}</h3>
                <a href="/livro?codigo=${
                  produto.codigo
                }" class="ver-produto">Ver produto</a>
              </div>
            `;

            listaLivros.appendChild(novoLivro);
          });

          const linksVerProduto = document.querySelectorAll(".ver-produto");
          linksVerProduto.forEach((link) => {
            link.addEventListener("click", (event) => {
              event.preventDefault();
              const href = link.getAttribute("href");
              window.location.href = href;
            });
          });
        })
        .catch((error) => {
          console.error(
            `Ocorreu um erro ao exibir produtos similares de ${categoria}:`,
            error
          );
        });
    })
    .catch((error) => {
      console.error("Ocorreu um erro ao carregar os detalhes do livro:", error);
    });
});
const botaoAdicionarCarrinho = document.getElementsByClassName("adicionar-carrinho");
botaoAdicionarCarrinho[0].addEventListener("click", function () {
  fetch(`/adiciona-carrinho/${codigoLivro}`)
  
});
