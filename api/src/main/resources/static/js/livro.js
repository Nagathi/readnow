const usuarioAutenticado = localStorage.getItem("token");
const nomeUsuario = localStorage.getItem("nome");
const modal = document.getElementById("modal");
const modalMessage = document.getElementById("modal-message");
const closeButton = document.querySelector(".close");

function closeModal() {
  modal.style.display = "none";
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
  const loginButton = document.querySelector(`a[href="${'login'}"]`);
  const cadastroButton = document.querySelector(`a[href="${'cadastro'}"]`);

  if (usuarioAutenticado != null && nomeUsuario != null) {
    loginButton.style.display = 'none';
    cadastroButton.style.display = 'none';
    const navIcons = document.createElement("nav");
    navIcons.classList.add("card-icons");
    const header = document.querySelector(".cabecalho");

    navIcons.innerHTML = `
       <div class="opcoes">
            <button id= "sua-conta" class="item">
            <a href="/conta-usuario">
                <img src="images/icons/user.svg" alt="Carrinho de compras" style="width: 3.6rem ;">
                <span class="identificador"> Olá, ${nomeUsuario} <br> Sua conta</span>
            </a>
            </button>
  
            <button class="item">
            <a href="#">
                <img src="images/icons/ouvidoria.svg" alt="Carrinho de compras" style="width: 3.6rem ;">
                <span class="identificador">Ouvidoria</span>
            </a>
            </button>
  
            <button class="item" id="carrinho-compras">
            <a href="carrinho">
                <img src="images/icons/iconCarrier.svg" alt="Carrinho de compras" style="width: 3.6rem ;">
                <span class="identificador">Carrinho</span>
    
                <div class="produtos-carrinho">
                <span class="quantidade">9</span>
                <span class="simbolo-mais">+</span>
                </div>
            </a>
            </button>
      </div>

    <div class="menu-list" id="userMenu">
      <ul>
        <li><a href="/conta-usuario">Sua conta</a></li>
        <li><a href="#">Seus pedidos</a></li>
        <li><a href="#">Seus endereços</a></li>
        <li id="sair"><a href="/">Sair da conta</a></li>
      </ul>
    </div>
  `;
    header.appendChild(navIcons);

    document.getElementById('sua-conta').addEventListener('click', function () {
      const userMenu = document.getElementById('userMenu');
      userMenu.style.display = (userMenu.style.display === 'block') ? 'none' : 'block';
    });
    document.getElementById('sair').addEventListener('click', function () {
      localStorage.removeItem("token")
      localStorage.removeItem("nome")
      localStorage.removeItem("email")

      const cardIcons = document.querySelector(".card-icons");

      cardIcons.style.display = 'none';
      loginButton.style.display = 'block';
      cadastroButton.style.display = 'block';
    });
  }
  else {
    loginButton.style.display = 'block';
    cadastroButton.style.display = 'block';
  }
}

const parametrosURL = obterParametrosURL();
const codigoLivro = parametrosURL.codigo;
document.addEventListener("DOMContentLoaded", function () {
  autenticacao();

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
                <a href="/livro?codigo=${produto.codigo
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
  }).then((response) => {
    if (response.ok) {
      modalMessage.textContent = "Livro adicionado ao carrinho!";
      modal.style.display = "block";

    } else {
      alert("Ocorreu um erro ao processar o cadastro");
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
window.addEventListener("click", function(event) {
  if (event.target === modal) {
    closeModal();
  }
});
