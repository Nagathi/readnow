const modalMessage = document.getElementById("modal-message");
const closeButton = document.querySelector(".close");
const modal = document.getElementById("modal");

function autenticacao() {
  try {
    // if (localStorage.getItem("token")) {
    //   fetch("/encerra-sessao", {
    //     method: "POST",
    //     headers: {
    //       Authorization: `Bearer ${localStorage.getItem("token")}`,
    //     },
    //   })
    //     .then(async (response) => {
    //       if (response.status === 200) {
    //         // Sucesso: o token não está expirado
    //         return response.json();
    //       } else if (response.status === 400) {
    //         // Token expirado
    //         const errorMessage = await response.text();
    //         throw new Error(`Token expirado: ${errorMessage}`);
    //       } else {
    //         // Outro erro no servidor
    //         throw new Error(`Erro na solicitação: ${response.status}`);
    //       }
    //     })

    //     .catch((error) => {
    //       if (error.message.includes("500")) {
    //         localStorage.removeItem("token");
    //         modalMessage.textContent = "Sessão expirada! Faça login novamente.";
    //         modal.style.display = "block";
    //       }
    //     });
    // }

    const nomeUsuario = localStorage.getItem("nome");
    const loginButton = document.querySelector("#login");
    const cadastroButton = document.querySelector("#cadastro");

    if (localStorage.getItem("token") != null && nomeUsuario != "") {
      loginButton.style.display = "none";
      cadastroButton.style.display = "none";

      const navIcons = document.createElement("nav");
      navIcons.classList.add("card-icons");
      const header = document.querySelector(".cabecalho");

      navIcons.innerHTML = `
        <div class="opcoes">
                <div id= "sua-conta" class="item">
                <a href="/conta-usuario">
                    <img src="images/icons/user.svg" alt="Carrinho de compras">
                    <span class="identificador"> Olá, ${nomeUsuario} <br> Sua conta</span>
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
    
                <div class="item">
                <a href="/central-ajuda">
                    <img src="images/icons/ouvidoria.svg" alt="Carrinho de compras">
                    <span class="identificador">Ajuda</span>
                </a>
                </div>
          </div>
    
        <div class="menu-list" id="userMenu">
        <ul>
          <li><a href="/conta-usuario">Conta</a></li>
          <li><a href="/seus-pedidos">Pedidos</a></li>
          <li><a href="/enderecos">Endereços</a></li>
          <li id="sair"><a href="/index">Sair</a></li>
        </ul>
        </div>
      `;
      header.appendChild(navIcons);
      document
        .getElementById("sua-conta")
        .addEventListener("mouseover", function () {
          const userMenu = document.getElementById("userMenu");
          userMenu.style.display =
            userMenu.style.display === "block" ? "none" : "block";
        });
      document.getElementById("sair").addEventListener("click", function () {
        if (localStorage.getItem("carrinhoItens") != "[]") {
          salvarEstadoCarrinho(localStorage.getItem("carrinhoItens"));
        }
        limparLocalStorage();

        const cardIcons = document.querySelector(".card-icons");

        cardIcons.style.display = "none";
        loginButton.style.display = "block";
        cadastroButton.style.display = "block";

        fetch("/logout", {
          method: "POST",
        })
          .then((response) => response.json)
          .then((data) => console.log(data));
      });
    } else {
      loginButton.style.display = "block";
      cadastroButton.style.display = "block";
    }
  } catch (error) {
    console.log(error);
  }
}

document.addEventListener("DOMContentLoaded", function () {
  autenticacao();

  const quantidadeExibicao = 5;

  const categorias = ["acao", "ficcao", "cultura", "aventura", "literatura"];

  const categoriasLivros = {};

  function exibirLivros(data, inicio, listaLivros, btnVerMais) {
    listaLivros.innerHTML = "";

    const finalExibicao = Math.min(inicio + quantidadeExibicao, data.length);

    for (let i = inicio; i < finalExibicao; i++) {
      const livro = data[i];
      const novoLivro = document.createElement("li");
      novoLivro.classList.add("card-livro");

      const caminhoImagem = "./images/livros/" + livro.imagem;

      novoLivro.innerHTML = `
        <div class="banner">
          <img src="${caminhoImagem}" alt="Produto" class="imagem">
        </div>
        <div class="detalhes-livro">
          <p class="titulo-livro">${livro.titulo}</p>
          <h3 class="preco">R$ ${livro.preco.toFixed(2)}</h3>
          <a href="#" class="ver-produto">Ver produto</a>
        </div>
      `;

      novoLivro.dataset.codigo = livro.codigo;
      novoLivro.dataset.titulo = livro.titulo;
      novoLivro.dataset.preco = livro.preco;

      listaLivros.appendChild(novoLivro);
    }

    if (finalExibicao >= data.length) {
      btnVerMais.disabled = true;
    }
  }

  function redirecionarParaLivro(codigo) {
    window.location.href = `/livro?codigo=${codigo}`;
  }

  categorias.forEach((categoria) => {
    const listaLivros = document.querySelector(`.lista-livros-${categoria}`);
    const btnVerMais = document.querySelector(`#btn-ver-mais-${categoria}`);
    categoriasLivros[categoria] = {
      listaLivros,
      btnVerMais,
      inicioExibicao: 0,
    };

    fetch(`/livros/${categoria}`)
      .then((response) => response.json())
      .then((data) => {
        const { listaLivros, btnVerMais } = categoriasLivros[categoria];
        exibirLivros(data, 0, listaLivros, btnVerMais);

        btnVerMais.addEventListener("click", function () {
          categoriasLivros[categoria].inicioExibicao += quantidadeExibicao;
          exibirLivros(
            data,
            categoriasLivros[categoria].inicioExibicao,
            listaLivros,
            btnVerMais
          );

          if (categoriasLivros[categoria].inicioExibicao >= data.length) {
            btnVerMais.disabled = true;
          }
        });
      })
      .catch((error) => {
        console.error(
          `Ocorreu um erro ao obter os livros de ${categoria}:`,
          error
        );
      });

    listaLivros.addEventListener("click", function (event) {
      const verProdutoLink = event.target.closest(".ver-produto");
      if (verProdutoLink) {
        const cardLivro = verProdutoLink.closest(".card-livro");
        const codigoLivro = cardLivro.dataset.codigo;

        redirecionarParaLivro(codigoLivro);
      }
    });
  });
});
function limparLocalStorage() {
  localStorage.removeItem("token");
  localStorage.removeItem("email");
  localStorage.removeItem("nome");
  localStorage.removeItem("carrinhoItens");
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
function closeModal() {
  modal.style.display = "none";
}
closeButton.addEventListener("click", closeModal);
window.addEventListener("click", function (event) {
  if (event.target === modal) {
    closeModal();
    window.location.href = "/";
  }
});
