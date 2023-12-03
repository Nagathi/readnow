
// const imagemProduto = livroDestaque.querySelector(".imagem-produto");
const nomeLivro = livroDestaque.querySelector(".nome-livro");
// const preco = livroDestaque.querySelector(".preco");
// const sinopse = livroDestaque.querySelector(".sinopse");
// const disponibilidade = livroDestaque.querySelector(".disponibilidade");

// imagemProduto.src = `./images/livros/${data.imagem}`;
// imagemProduto.alt = `Imagem de ${data.titulo}`;
nomeLivro.textContent = data.titulo;
// preco.textContent = `R$ ${data.preco.toFixed(2)}`;
// sinopse.textContent = data.descricao;
// disponibilidade.textContent = "Em estoque";

document.addEventListener("DOMContentLoaded", function () {
    const listaLivros = document.querySelector(".lista-livros");
  
    const email = localStorage.getItem('email');
  
    if (email) {
      fetch(`/carrinho/${email}`)
        .then((response) => response.json())
        .then((data) => {
            listaLivros.innerHTML = "";
  
          data.forEach((livro) => {
            const novoLivro = document.createElement("li");
            novoLivro.classList.add("card-livro");
            novoLivro.innerHTML = `
            <div class="container-imagem">
            <img src="images/icons/imagem_produto.png" alt="Imagem Produto">
          </div>

          <div class="direita">
            <div class="textos">
              <a href="#">
                <h1 class="nome-livro">${livro.nome}</h1>
              </a>
              <p class="preco">RS 60,00</p>
            </div>
            `;
            listaEnderecos.appendChild(novoLivro);
          });
        })
        .catch((error) => {
          console.error("Erro ao obter endereços:", error);
        });
    } else {
      console.error("E-mail não encontrado no localStorage");
    }
  });