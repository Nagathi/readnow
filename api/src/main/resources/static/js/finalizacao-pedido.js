localStorage.setItem("paginaFinalizacao", window.location.href);

const token = localStorage.getItem("token");
const modalMessage = document.getElementById("modal-message");
const closeButton = document.querySelector(".close");

function buscarEnderecos() {
  if (token) {
    fetch(`/enderecos/${token}`)
      .then((response) => response.json())
      .then((data) => {
        data.forEach((endereco) => {
          const enderecosDiv = document.querySelector(".enderecos");
          const element = document.createElement("div");
          element.classList.add("endereco");
          element.innerHTML = ` 
        <label>
          <input type="radio" id="${endereco.codigo}"  name="endereco" value="1">
            <div class="informacoes">
              <span class="destinatario">${endereco.nomeDestino}<br></span>
              <span class="informacao-endereco">${endereco.logradouro}, ${endereco.numeroCasa}, ${endereco.complemento}, ${endereco.bairro}, ${endereco.cidade}, ${endereco.estado}<br> CEP: ${endereco.cep}<br>Telefone: ${endereco.telefone}</span>
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

function buscarCartoes() {
  fetch(`/cartoes/${token}`)
    .then((response) => response.json())
    .then((data) => {
      data.forEach((cartao) => {
        const cartoes = document.querySelector(".cartoes");
        const divCartoes = document.createElement("div");
        divCartoes.classList.add("cartao");
        divCartoes.innerHTML = `
                    
            <label>
              <input type="radio" id="${cartao.codigo}" name="cartao" value="1">
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

  if (localStorage.getItem("pagina-livro")) {
    const livroComprarAgora = localStorage.getItem("livroComprarAgora");
    if (livroComprarAgora != null) {
      const livro = JSON.parse(livroComprarAgora);
      showCardLivros(livro.livro, livro.quantidade, 1);
      atualizarValorTotal(livro);
    } else {
      const parametrosURL = obterParametrosURL();
      const codigoLivro = parametrosURL.codigo;
      fetch(`/busca-livro/${codigoLivro}`)
        .then((response) => response.json())
        .then((data) => {
          var livro = {
            livro: data,
            quantidade: 1,
          };
          if (localStorage.getItem("livroComprarAgora") == null) {
            localStorage.setItem("livroComprarAgora", JSON.stringify(livro));
          }
          showCardLivros(data, 1, codigoLivro);
          atualizarValorTotal(livro);
        });
    }
  } else {
    const carrinhoItens = JSON.parse(localStorage.getItem("carrinhoItens"));
    carrinhoItens.forEach((item, index) => {
      valorTotalLivros += item.quantidade * item.livro.preco;
      atualizarValorTotal(carrinhoItens);
      showCardLivros(item.livro, item.quantidade, index);
    });
  }
}

function atualizarValorTotal(data) {
  const valorTotalFrete = 10.0;
  var valorTotalLivros = 0;

  if (localStorage.getItem("pagina-livro")) {
    valorTotalLivros = data.quantidade * data.livro.preco;
  } else {
    data.forEach((item, index) => {
      valorTotalLivros += item.quantidade * item.livro.preco;
    });
  }

  const valorTotal = valorTotalLivros + valorTotalFrete;

  const valorPedido = document.querySelector(".valor-pedido");
  const valorItens = document.querySelector(".valor-itens");
  const valorFrete = document.querySelector(".valor-frete");
  valorItens.innerHTML = `R$ ${valorTotalLivros.toFixed(2)}`;
  valorFrete.innerHTML = `R$ ${valorTotalFrete.toFixed(2)}`;
  valorPedido.innerHTML = `Total do pedido: R$ ${valorTotal.toFixed(2)}`;
  localStorage.setItem("valorTotalCarrinho", valorTotal.toFixed(2));
}
function obterParametrosURL() {
  const search = localStorage.getItem("pagina-livro");
  const partes = search.split("?");
  const params = {};

  for (const parte of partes) {
    const [chave, valor] = parte.split("=");
    params[chave] = decodeURIComponent(valor);
  }

  return params;
}

function salvarPedido() {
  if (localStorage.getItem("token") != null) {
    const dataAtual = new Date();

    const dia = dataAtual.getDate();
    const mes = dataAtual.getMonth() + 1;
    const ano = dataAtual.getFullYear();
    const horas = addZero(dataAtual.getHours());
    const minutos = addZero(dataAtual.getMinutes());

    const dataPedido = `${dia}/${mes}/${ano}`;
    const horaPedido = `${horas}:${minutos}`;
    localStorage.setItem("dataPedido", dataPedido);
    localStorage.setItem("horaPedido", horaPedido);

    const valorTotal = Number(localStorage.getItem("valorTotalCarrinho"));
    const dataEntregaPrevista = "15/12/2023";
    const codigoCartao = Number(
      localStorage.getItem("cartao-selecionado-finalizacao")
    );
    const codigoEndereco = Number(
      localStorage.getItem("endereco-selecionado-finalizacao")
    );
    const email = localStorage.getItem("email");
    var listaLivros = [];

    if (localStorage.getItem("livroComprarAgora")) {
      const parametrosURL = obterParametrosURL();
      const codigoLivro = parametrosURL.codigo;
      var livro = {
        codigoLivro: codigoLivro,
        quantidade: JSON.parse(localStorage.getItem("livroComprarAgora"))
          .quantidade,
      };
      listaLivros.push(livro);
    } else {
      const carrinhoItens = JSON.parse(localStorage.getItem("carrinhoItens"));
      listaLivros = carrinhoItens.map((item) => {
        return {
          codigoLivro: item.livro.codigo,
          quantidade: item.quantidade,
        };
      });
    }

    var data = {
      dataPedido: dataPedido,
      valorTotal: valorTotal,
      dataEntregaPrevista: dataEntregaPrevista,
      codigoCartao: codigoCartao,
      codigoEndereco: codigoEndereco,
      email: email,
      livros: listaLivros,
    };
    if (isEnderecoSelecionado() && isCartaoSelecionado()) {
      fetch("/salva-pedido", {
        method: "POST",
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
          "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
      })
        .then((response) => {
          if (!response.ok) {
            throw new Error("Erro ao processar pedido");
          }
          return response.json();
        })
        .then((data) => {
          localStorage.setItem("pedidoId", data);
          window.location.href = "/agradecimento-compra";
          if (localStorage.getItem("pagina-livro") == null) {
            localStorage.setItem("carrinhoItens", "[]");
          }
          limparLocalStorage();

          salvarEstadoCarrinho(localStorage.getItem("carrinhoItens"));
        })
        .catch((error) => {
          console.error("Erro durante a requisição:", error);
        });
    } else {
      modalMessage.textContent = "Selecione um endereço e/ou cartão!";
      modal.style.display = "block";
    }
  }
}

function addZero(numero) {
  return numero < 10 ? `0${numero}` : numero;
}

const buttonConfirmacao = document.querySelector(".btn-finalizar-pedido");
buttonConfirmacao.addEventListener("click", function (event) {
  event.preventDefault();
  localStorage.removeItem("paginaFinalizacao");
});

document
  .getElementById("linkFinalizarPedido")
  .addEventListener("click", function (event) {
    event.preventDefault();
    salvarPedido();
  });

document.addEventListener("change", function (event) {
  const radio = event.target;

  if (radio.type === "radio" && radio.name === "endereco" && radio.checked) {
    const enderecoSelecionado = radio.closest(".endereco");
    if (enderecoSelecionado) {
      localStorage.setItem("endereco-selecionado-finalizacao", radio.id);
    }
  } else {
    const cartaoSelecionado = radio.closest(".cartao");
    if (cartaoSelecionado) {
      localStorage.setItem("cartao-selecionado-finalizacao", radio.id);
    }
  }
});

function isEnderecoSelecionado() {
  const enderecoSelecionado = localStorage.getItem(
    "endereco-selecionado-finalizacao"
  );
  if (enderecoSelecionado == null) {
    return false;
  }
  return true;
}

function isCartaoSelecionado() {
  const cartaoSelecionado = localStorage.getItem(
    "cartao-selecionado-finalizacao"
  );
  if (cartaoSelecionado == null) {
    return false;
  }
  return true;
}

function limparLocalStorage() {
  localStorage.removeItem("endereco-selecionado-finalizacao");
  localStorage.removeItem("cartao-selecionado-finalizacao");
}

document.addEventListener("DOMContentLoaded", function () {
  buscarEnderecos();
  buscarCartoes();
  revisarItens();
});
function closeModal() {
  modal.style.display = "none";
}

function showCardLivros(item, quantidade, index) {
  var listaLivros = document.querySelector(".itens-carrinho");
  var novoLivro = document.createElement("li");

  novoLivro.classList.add("item-carrinho");
  novoLivro.innerHTML = `
  <div class="container-imagem">
  <img src="./images/livros/${item.imagem}" alt="Imagem Produto">
  </div>
  
  <div class="direita">
    <div class="textos">
      <div class="detalhes-livro">
        <h1 id = "${item.isbn}" class="nome-livro">${item.titulo}</h1>
        <p class="nome-autor">Por ${item.autor}</p>
      </div>

      <p class="preco">R$ ${item.preco.toFixed(2)}</p>

    </div>

    <div class="detalhes">
      <p class="estimativa-entrega">Entrega estimada: XX dias</p>
    </div>

    <select id="quantidade" class="quantidade-${index}">
      <option selected disabled value="">${quantidade}</option>
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

  const selectQuantidade = document.querySelectorAll("#quantidade");
  selectQuantidade.forEach(function (selectElement) {
    selectElement.addEventListener("change", function () {
      const classeDoSelect = selectElement.classList.value
        .toString()
        .split("-")[1];

      if (localStorage.getItem("livroComprarAgora")) {
        const livroComprarAgora = JSON.parse(
          localStorage.getItem("livroComprarAgora")
        );
        livroComprarAgora.quantidade = parseInt(selectElement.value, 10);
        localStorage.setItem(
          "livroComprarAgora",
          JSON.stringify(livroComprarAgora)
        );
        atualizarValorTotal(livroComprarAgora);
      } else {
        const carrinhoItens = JSON.parse(localStorage.getItem("carrinhoItens"));
        carrinhoItens.forEach((item, index) => {
          if (classeDoSelect === index.toString()) {
            item.quantidade = parseInt(selectElement.value, 10);
            localStorage.setItem(
              "carrinhoItens",
              JSON.stringify(carrinhoItens)
            );
          }
          atualizarValorTotal(carrinhoItens);
        });
      }
    });
  });
}

function salvarEstadoCarrinho(data) {
  fetch("/atualiza-carrinho", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: data,
  });
}
closeButton.addEventListener("click", closeModal);
window.addEventListener("click", function (event) {
  if (event.target === modal) {
    closeModal();
  }
});
