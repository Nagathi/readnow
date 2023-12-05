document.addEventListener("DOMContentLoaded", function () {
  const token = localStorage.getItem("email");

  if (token) {
    if (localStorage.getItem("carrinhoItens") != null) {
      mostrarLivrosLocalStorage();
    } else {
      mostrarLivrosBd();
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
                    <option selected disabled value="">1</option>
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
                  <button data-livro-titulo="${
                    item.livro.isbn
                  }" id="excluir" class="btn-excluir">Excluir</button>
                </div>
              </div>
            </div>  
            `;
  listaLivros.appendChild(novoLivro);
  var botoesExcluir = document.querySelectorAll(".btn-excluir");
  botoesExcluir.forEach(function (botaoExcluir) {
    botaoExcluir.addEventListener("click", function () {
      var livroTitulo = botaoExcluir.dataset.livroTitulo;
      var itemLista = document.querySelector(`.${item.livro.isbn}`);

      if (itemLista.classList.contains(livroTitulo)) {
        itemLista.remove();
        removerLivro(livroTitulo, item);
      }     
    });
  });
}

function removerLivro(itemParaRemover, item) {
  var livrosNoCarrinho =
    JSON.parse(localStorage.getItem("carrinhoItens")) || [];

  var index = livrosNoCarrinho.findIndex(function (livro) {
    return livro.livro.isbn === itemParaRemover;
  });

  if (index !== -1) {
    livrosNoCarrinho.splice(index, 1);

    localStorage.setItem("carrinhoItens", JSON.stringify(livrosNoCarrinho));
  } else {
    console.error("Livro não encontrado no carrinho.");
  }

  mostrarSubtotal(livrosNoCarrinho);
}

function alterarQuantidadeLivro(livro, data, index) {
  const selectQuantidade = livro.querySelector("#quantidade");
  selectQuantidade.addEventListener("change", function () {
    const novoQuantidade = parseInt(this.value);
    data[index].quantidade = novoQuantidade;
    localStorage.setItem("carrinhoItens", JSON.stringify(data));
    mostrarSubtotal(data);
  });
}

function mostrarSubtotal(data) {
  var qtdTotalLivros = 0;
  var valorTotalLivros = 0;
  const carrinhoItens = JSON.parse(localStorage.getItem("carrinhoItens"));

  data.forEach((item) => {
    qtdTotalLivros += item.quantidade;
    valorTotalLivros += item.quantidade * item.livro.preco;
  });
  var subtotal = document.querySelector(".subtotal");
  var precoTotal = document.querySelector("#valor-subtotal");

  subtotal.innerHTML = `Subtotal (${qtdTotalLivros} livros) `;
  precoTotal.innerHTML = `R$ ${valorTotalLivros.toFixed(2)}`;
}

function mostrarLivrosLocalStorage() {
  const carrinhoItens = JSON.parse(localStorage.getItem("carrinhoItens"));
  carrinhoItens.forEach((item, index) => {
    var listaLivros = document.querySelector(".card-itens-carrinho");
    var novoLivro = document.createElement("li");
    novoLivro.classList.add(item.livro.isbn);
    listarLivrosCarrinho(novoLivro, item, listaLivros);
    alterarQuantidadeLivro(novoLivro, carrinhoItens, index);
    mostrarSubtotal(carrinhoItens);
  });
}

function mostrarLivrosBd() {
  const token = localStorage.getItem("token");

  fetch(`/mostra-carrinho/${token}`)
    .then((response) => {
      return response.json();
    })
    .then((data) => {
      localStorage.setItem("carrinhoItens", JSON.stringify(data));

      data.forEach((item, index) => {
        const listaLivros = document.querySelector(".card-itens-carrinho");
        const novoLivro = document.createElement("li");

        listarLivrosCarrinho(novoLivro, item, listaLivros);
        alterarQuantidadeLivro(novoLivro, data, index);
      });
      mostrarSubtotal(data);
    })
    .catch((error) => {
      console.error("Erro ao obter livros:", error);
    });
}
// const excluir = document.querySelector("#excluir");
// excluir.addEventListener("click", function() {
//   console.log("oi");
// });
