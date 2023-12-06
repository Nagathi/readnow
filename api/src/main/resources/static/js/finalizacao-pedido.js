(() => {
  const listaDeEnderecos = document.querySelectorAll(".endereco");
  const listaDeCartoes = document.querySelectorAll(".cartao");

  function selecionaEndereco() {
    listaDeEnderecos.forEach((endereco) => {
      endereco.removeAttribute("id");
      this.id = "endereco-selecionado";
    });
  }

  listaDeEnderecos.forEach((endereco) => {
    endereco.addEventListener("click", selecionaEndereco);
  });

  function selecionaCartao() {
    listaDeCartoes.forEach((cartao) => {
      cartao.removeAttribute("id");
      this.id = "cartao-selecionado";
    });
  }

  listaDeCartoes.forEach((cartao) => {
    cartao.addEventListener("click", selecionaCartao);
  });
})();

localStorage.setItem("paginaFinalizacao", window.location.href);
const email = localStorage.getItem("email");

function buscarEnderecos() {
  if (email) {
    fetch(`/enderecos/${email}`)
      .then((response) => response.json())
      .then((data) => {
        data.forEach((endereco) => {
          const enderecosDiv = document.querySelector(".enderecos");
          const element = document.createElement("div");
          element.classList.add("endereco");
          element.innerHTML = ` 
        <label>
          <input type="radio" name="endereco" value="1">
            <div class="informacoes">
              <span class="destinatario">${endereco.nomeDestino}<br></span>
              <span class="informacao-endereco">${endereco.logradouro}, ${endereco.numeroCasa}, ${endereco.complemento}, ${endereco.bairro}, ${endereco.cidade}, ${endereco.estado}<br>Telefone: ${endereco.telefone}</span>
            </div>
        <label>`;

          enderecosDiv.appendChild(element);
        });
      })
      .catch((error) => {
        console.error("Erro ao obter endereços:", error);
      });
  } else {
    console.error("E-mail não encontrado no localStorage");
  }
}

document.addEventListener("DOMContentLoaded", function () {
  buscarEnderecos();
  buscarCartoes();
  revisarItens();
});

function buscarCartoes() {
  fetch(`/cartoes/${email}`)
    .then((response) => response.json())
    .then((data) => {
      data.forEach((cartao) => {
        const cartoes = document.querySelector(".cartoes");
        const divCartoes = document.createElement("div");
        divCartoes.classList.add("cartao");
        divCartoes.innerHTML = `
                    
            <label>
              <input type="radio" name="cartao" value="1">
              <div class="informacoes">
                <span class="nome-cartao">${cartao.nome}<br></span>
                <span class="informacao-cartao">(Crédito) Cartão <br>Terminado em ${
                  cartao.numero % 10000
                }<br>Vencimento: ${cartao.data}</span>
              </div>
            </label>

                `;
        cartoes.appendChild(divCartoes);
      });
    })
    .catch((error) => {
      console.error("Erro ao carregar os cartões:", error);
    });
}

function revisarItens() {
  const carrinhoItens = JSON.parse(localStorage.getItem("carrinhoItens"));
  carrinhoItens.forEach((item, index) => {
    var listaLivros = document.querySelector(".confirmar-compra");
    var novoLivro = document.createElement("div");
    novoLivro.classList.add("confirmacao");
    novoLivro.innerHTML = `
          <label>
            <div class="container-imagem">
                <img src="./images/livros/${
                  item.livro.imagem
                }" alt="Imagem Produto">
            </div>

            <div class="direita">
              <div class="textos">
                <a href="#">
                  <h1 class="nome-livro">${item.livro.titulo}</h1>
                  <h3 class="nome-autor">Por ${item.livro.autor}</h3>
                </a>
                <p class="preco">R$ ${item.livro.preco.toFixed(2)}</p>
              </div>

              <div class="detalhes">
  
                <div class="botoes">
                  <select id="quantidade">
                    <option selected disabled value="">${
                      item.quantidade
                    }</option>
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
                </div>
              </div>
            <label>

              `;

    listaLivros.appendChild(novoLivro);
  });
}
const buttonConfirmacao = document.querySelector(".btn-finalizar-pedido");
buttonConfirmacao.addEventListener("click", function (event) {
  event.preventDefault();
  localStorage.removeItem("paginaFinalizacao");
});
