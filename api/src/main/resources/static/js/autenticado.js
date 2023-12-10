function autenticacao() {
  const usuarioAutenticado = localStorage.getItem("token");
  const nomeUsuario = localStorage.getItem("nome");

  if (usuarioAutenticado != null && nomeUsuario != null) {
    const suaContaButton = document.querySelector(".identificador");
    suaContaButton.innerHTML = `Olá, ${
      nomeUsuario.split(" ")[0]
    } <br> Sua conta`;

    const navIcons = document.createElement("div");
    navIcons.classList.add("menu-list");
    const header = document.querySelector(".nav-icons");
    navIcons.innerHTML = `
        <ul>
            <li>
              <a href="/conta-usuario">Conta</a>
            </li>

            <li>
              <a href="#">Pedidos</a>
            </li>

            <li>
              <a href="/enderecos">Endereços</a>
            </li>

            <li id="sair">
              <a href="/">Sair</a>
            </li>
        </ul>
      `;
    header.appendChild(navIcons);

    document.getElementById("conta").addEventListener("mouseover", () => {
      navIcons.classList.add("ativo");
    });

    window.addEventListener("click", () => {
      navIcons.classList.remove("ativo");
    });

    document.getElementById("sair").addEventListener("click", function () {
      if (localStorage.getItem("carrinhoItens") != []) {
        salvarEstadoCarrinho(localStorage.getItem("carrinhoItens"));
      }
      limparLocalStorage();

      const cardIcons = document.querySelector(".nav-icons");
      cardIcons.style.display = "none";
    });
  }
}
document.addEventListener("DOMContentLoaded", function () {
  autenticacao();
});

function salvarEstadoCarrinho(data) {
  fetch("/atualiza-carrinho", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: data,
  });
}

function removerItemCarrinho(codigoLivro, usuarioAutenticado) {
  fetch(`/remove-livro?codigo=${codigoLivro}`, {
    method: "POST",
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json",
      Authorization: `Bearer ${usuarioAutenticado}`,
    },
  })
    .then((response) => {
      return response.json();
    })
    .then((data) => {
      console.log(data);
    });
}

function limparLocalStorage(){
  localStorage.removeItem("token");
  localStorage.removeItem("email");
  localStorage.removeItem("nome");
  localStorage.removeItem("carrinhoItens");
}
