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
  var valorTotalLivros = 0;
  var listaLivros = "";

  const carrinhoItens = JSON.parse(localStorage.getItem("carrinhoItens"));
  carrinhoItens.forEach((item, index) => {
    valorTotalLivros += item.quantidade * item.livro.preco;
    listaLivros = document.querySelector(".itens-carrinho");
    var novoLivro = document.createElement("li");

    novoLivro.classList.add("item-carrinho");
    novoLivro.innerHTML = `
    <div class="container-imagem">
    <img src="./images/livros/${item.livro.imagem}" alt="Imagem Produto">
    </div>
    
    <div class="direita">
      <div class="textos">
        <div class="detalhes-livro">
          <h1 id = "${item.livro.isbn}" class="nome-livro">${item.livro.titulo}</h1>
          <p class="nome-autor">${item.livro.autor}</p>
        </div>

        <p class="preco">R$ ${item.livro.preco.toFixed(2)}</p>

      </div>

      <div class="detalhes">
        <p class="estimativa-entrega">Entrega estimada: XX dias</p>
      </div>

      <select id="quantidade" class="quantidade-${index}">
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
    </div>`;

    listaLivros.appendChild(novoLivro);
  });

  const valorPedido = document.querySelector(".valor-pedido");
  valorPedido.innerHTML = `Total do pedido: R$ ${valorTotalLivros.toFixed(2)}`;

  const selectQuantidade = document.querySelectorAll(`#quantidade`);
  selectQuantidade.forEach(function (selectElement) {
    selectElement.addEventListener("change", function () {
      const classeDoSelect = selectElement.classList.value.toString().split("-")[1];
      const carrinhoItens = JSON.parse(localStorage.getItem("carrinhoItens"));
      carrinhoItens.forEach((item, index) => {
        if(classeDoSelect === index.toString()) {
          item.quantidade = parseInt(selectElement.value, 10);

        }
      });

      localStorage.setItem("carrinhoItens", JSON.stringify(carrinhoItens));

    });
  });


}
const buttonConfirmacao = document.querySelector(".btn-finalizar-pedido");
buttonConfirmacao.addEventListener("click", function (event) {
  event.preventDefault();
  localStorage.removeItem("paginaFinalizacao");
});