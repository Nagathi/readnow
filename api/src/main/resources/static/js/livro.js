const usuarioAutenticado = localStorage.getItem("token");
const nomeUsuario = localStorage.getItem("nome");
const modal = document.getElementById("modal");
const modalMessage = document.getElementById("modal-message");
const closeButton = document.querySelector(".close");
const comprarAgoraButton = document.querySelector(".comprar-agora");

function closeModal() {
  modal.style.display = "none";
  window.location.href = "/";
}

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

function autenticacao() {
  const loginButton = document.querySelector(`a[href="${"/login"}"]`);
  const cadastroButton = document.querySelector(
    `a[href="${"/cadastro-cliente"}"]`
  );

  if (usuarioAutenticado != null && nomeUsuario != null) {
    loginButton.style.display = "none";
    cadastroButton.style.display = "none";
    const navIcons = document.createElement("nav");
    navIcons.classList.add("card-icons");
    const header = document.querySelector(".cabecalho");

    navIcons.innerHTML = `
       <div class="opcoes">
            <div id= "sua-conta" class="item">
            <a href="/conta-usuario">
                <img src="images/icons/user.svg" alt="Carrinho de compras" >
                <span class="identificador"> Olá, ${
                  nomeUsuario.split(" ")[0]
                } <br> Sua conta</span>
            </a>
            </div>
  
            <div class="item">
            <a href="/central-ajuda">
                <img src="images/icons/ouvidoria.svg" alt="Carrinho de compras">
                <span class="identificador">Ajuda</span>
            </a>
            </div>
  
            <div class="item" id="carrinho-compras">
            <a href="carrinho">
                <img src="images/icons/iconCarrier.svg" alt="Carrinho de compras">
                <span class="identificador">Carrinho</span>
    
                <div class="produtos-carrinho">
                <span class="quantidade">9</span>
                <span class="simbolo-mais">+</span>
                </div>
            </a>
            </div>
      </div>

    <div class="menu-list" id="userMenu">
      <ul>
        <li><a href="/conta-usuario">Sua conta</a></li>
        <li><a href="/seus-pedidos">Seus pedidos</a></li>
        <li><a href="/enderecos">Seus endereços</a></li>
        <li id="sair"><a href="/">Sair da conta</a></li>
      </ul>
    </div>
  `;
    header.appendChild(navIcons);

    document.getElementById("sua-conta").addEventListener("click", function () {
      const userMenu = document.getElementById("userMenu");
      userMenu.style.display =
        userMenu.style.display === "block" ? "none" : "block";
    });
    document.getElementById("sair").addEventListener("click", function () {
      localStorage.removeItem("token");
      localStorage.removeItem("nome");
      localStorage.removeItem("email");

      const cardIcons = document.querySelector(".card-icons");

      cardIcons.style.display = "none";
      loginButton.style.display = "block";
      cadastroButton.style.display = "block";
    });
  } else {
    loginButton.style.display = "block";
    cadastroButton.style.display = "block";
  }
}

const parametrosURL = obterParametrosURL();
const codigoLivro = parametrosURL.codigo;

document.querySelector(".comprar-agora").addEventListener("mouseover", function () {
    verificarSessaoExpirada();
  });
document.querySelector(".adicionar-carrinho").addEventListener("mouseover", function () {
    verificarSessaoExpirada();
});

document.addEventListener("DOMContentLoaded", function () {
  autenticacao();
  verificarSessaoExpirada();
  fetch(`/busca-livro/${codigoLivro}`)
    .then((response) => response.json())
    .then((data) => {
      const livroDestaque = document.querySelector(".livro");

      const imagemProduto = livroDestaque.querySelector(".imagem-produto");
      const nomeLivro = livroDestaque.querySelector(".nome-livro");
      const preco = livroDestaque.querySelector(".preco");
      const sinopse = livroDestaque.querySelector(".sinopse");
      const disponibilidade = livroDestaque.querySelector(".disponibilidade");
      const notaLivro = livroDestaque.querySelector(".nota");

      imagemProduto.src = `./images/livros/${data.imagem}`;
      imagemProduto.alt = `Imagem de ${data.titulo}`;
      nomeLivro.textContent = data.titulo;
      preco.textContent = `R$ ${data.preco.toFixed(2)}`;
      sinopse.textContent = data.descricao;
      disponibilidade.textContent = "Em estoque";
      addEstrelasAvaliacao(notaLivro, data.nota);

      const categoria = data.categoria;
      document.title = data.titulo;

      fetch(`/livros/${categoria}`)
        .then((response) => response.json())
        .then((produtosSimilares) => {
          const listaLivros = document.querySelector(".lista-livros");

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

function adicionarItemCarrinho() {
  fetch(`/adiciona-livro?codigo=${codigoLivro}&quantidade=1`, {
    method: "POST",
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json",
      Authorization: `Bearer ${usuarioAutenticado}`,
    },
  })
    .then((response) => {
      if (response.ok) {
        modalMessage.textContent = "Livro adicionado ao carrinho!";
        modal.style.display = "block";
      } else {
        alert("Ocorreu um erro ao processar o cadastro");
      }
      return response.json();
    })
    .then((data) => {
      var livros = [];
      if (localStorage.getItem("carrinhoItens") != null) {
        var recuperado = Array.from(
          JSON.parse(localStorage.getItem("carrinhoItens"))
        );
        var indice = encontrarIndiceLivro(recuperado, data);
        if (indice == -1) {
          recuperado.push(data);
        } else {
          recuperado[indice].quantidade += 1;
        }
        localStorage.setItem("carrinhoItens", JSON.stringify(recuperado));
      } else {
        livros.push(data);
        localStorage.setItem("carrinhoItens", JSON.stringify(livros));
      }
    });
}
function verificarSessaoExpirada() {
  fetch("/encerra-sessao", {
    method: "POST",
    headers: {
      Authorization: `Bearer ${localStorage.getItem("token")}`,
    },
  })
    .then((response) => {
      if (response.status != 200) {
        throw new Error(response.status);
      }
    })
    .catch((error) => {
      if (error.message.includes("500")) {
        console.clear();
        localStorage.removeItem("token");
        modalMessage.textContent = "Sessão expirada! Faça login novamente.";
        modal.style.display = "block";
      } else {
        modalMessage.textContent = "Ocorreu um erro. Repita a ação novamente.";
        modal.style.display = "block";
      }
    });
}
const botaoAdicionarCarrinho = document.querySelector(".adicionar-carrinho");
botaoAdicionarCarrinho.addEventListener("click", function () {
  if (usuarioAutenticado != null && nomeUsuario != null) {
    adicionarItemCarrinho();
  }
});
closeButton.addEventListener("click", closeModal);
window.addEventListener("click", function (event) {
  if (event.target === modal) {
    closeModal();
  }
});

modal.addEventListener("onblur", function () {
  window.location.href = "/";
});

function encontrarIndiceLivro(array, novoLivro) {
  return array.findIndex((item) => sãoIguais(item, novoLivro));
}

function sãoIguais(livro1, livro2) {
  console.log(livro1.livro.codigo);
  console.log(livro2.livro.codigo);
  return livro1.livro.codigo === livro2.livro.codigo;
}

comprarAgoraButton.addEventListener("click", function (event) {
  event.preventDefault();
  localStorage.setItem("pagina-livro", window.location.href);
  window.location.href = "/finalizar-pedido";
});

function addEstrelasAvaliacao(elementoPai, nota) {
  const qtdEstrelasNormais = 5 - nota;
  const qtdEstrelasPreenchidas = nota;

  /*Criando estrelas preenchidas */
  for (var i = 0; i < qtdEstrelasPreenchidas; i++) {
    var estrela = document.createElement("li");
    estrela.innerHTML = `
      <img src="images/icons/star-with-fill.svg" alt="">
    `;
    elementoPai.appendChild(estrela);
  }

  /*Criando estrelas normais */
  for (var i = 0; i < qtdEstrelasNormais; i++) {
    var estrela = document.createElement("li");
    estrela.innerHTML = `
      <img src="images/icons/star-normal.svg" alt="">
    `;
    elementoPai.appendChild(estrela);
  }
}
