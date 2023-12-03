document.addEventListener("DOMContentLoaded", function () {
  const email = localStorage.getItem("email");

  if (email) {
    var qtdTotalLivros = 0;
    var valorTotalLivros = 0;
    JSON.parse(localStorage.getItem("carrinhoItens")).forEach((it) => {
        qtdTotalLivros+= it.quantidade;
        valorTotalLivros += it.livro.preco *it.quantidade;
    });

    if (localStorage.getItem("carrinhoItens") != null) {
      const carrinhoItens =
        JSON.parse(localStorage.getItem("carrinhoItens")) || [];
      carrinhoItens.forEach((item, index) => {
        const listaLivros = document.querySelector(".card-itens-carrinho");
        const novoLivro = document.createElement("li");
        listarLivrosCarrinho(novoLivro, item, listaLivros);
        mostrarSubtotal(qtdTotalLivros, valorTotalLivros);
        alterarQuantidadeLivro(novoLivro, carrinhoItens, index);

      });

    } else {
      fetch(`/mostra-carrinho/${email}`)
        .then((response) => response.json())
        .then((data) => {
          localStorage.setItem("carrinhoItens", JSON.stringify(data));
          data.forEach((item, index) => {
            const listaLivros = document.querySelector(".card-itens-carrinho");
            const novoLivro = document.createElement("li");
            listarLivrosCarrinho(novoLivro, item, listaLivros);
            alterarQuantidadeLivro(novoLivro, data, index);
          });
        })
        .catch((error) => {
          console.error("Erro ao obter livros:", error);
        });
    }
  } else {
    console.error("E-mail não encontrado no localStorage");
  }
});

function listarLivrosCarrinho(novoLivro, item, listaLivros) {
  novoLivro.classList.add("item-carrinho");
  novoLivro.innerHTML = `
            <div class="container-imagem">
                <img src="./images/livros/${
                  item.livro.imagem
                }" alt="Imagem Produto">
            </div>

            <div class="direita">
              <div class="textos">
                <a href="#">
                  <h1 class="nome-livro">${item.livro.titulo}</h1>
                </a>
                <p class="preco">R$ ${item.livro.preco.toFixed(2)}</p>
              </div>

              <div class="detalhes">
                <ul class="classificacao">
                  <li>
                    <img src="images/icons/star-with-fill.svg" alt="">
                  </li>
  
                  <li>
                    <img src="images/icons/star-with-fill.svg" alt="">
                  </li>
  
                  <li>
                    <img src="images/icons/star-with-fill.svg" alt="">
                  </li>
  
                  <li>
                    <img src="images/icons/star-with-fill.svg" alt="">
                  </li>
  
                  <li>
                    <img src="images/icons/star-with-fill.svg" alt="">
                  </li>
                </ul>
    
                <p class="disponibilidade disponivel">Em estoque</p>
  
                <div class="botoes">
                  <select id="quantidade">
                    <option selected disabled value="">${item.quantidade}</option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                    <option value="6">6</option>
                    <option value="7">7</option>
                    <option value="8">8</option>
                    <option value="9">9</option>
                    <option value="10">10</option>
                  </select>
                  <button class="btn-excluir">Excluir</button>
                </div>
              </div>
            </div>  
            `;
  listaLivros.appendChild(novoLivro);
}

function alterarQuantidadeLivro(livro, data, index) {
  const selectQuantidade = livro.querySelector("#quantidade");
  selectQuantidade.addEventListener("change", function () {
    const novoQuantidade = parseInt(this.value);
    data[index].quantidade = novoQuantidade;
    localStorage.setItem("carrinhoItens", JSON.stringify(data));
  });
}

function mostrarSubtotal(qtdTotalLivros, valorTotalLivros) {
  var subtotal = document.querySelector(".subtotal");
  var precoTotal = document.querySelector("#valor-subtotal");

  subtotal.innerHTML = `Subtotal (${qtdTotalLivros} livros) `;
  precoTotal.innerHTML = `R$ ${valorTotalLivros.toFixed(2)}`
}
