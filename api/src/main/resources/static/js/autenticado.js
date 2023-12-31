const mensagemModal = document.getElementById("modal-message");
const botaoFechar = document.querySelector(".close");
const modalAviso = document.getElementById("modal");

function autenticacao() {
  const nomeUsuario = localStorage.getItem("nome");

  if (localStorage.getItem("token") != null && nomeUsuario != null) {
    const suaContaButton = document.querySelector(".identificador");
    suaContaButton.innerHTML = `Olá, ${nomeUsuario.split(" ")[0]} <br> Sua conta`;

    const navIcons = document.createElement("div");
    navIcons.classList.add("menu-list");
    const header = document.querySelector(".nav-icons");
    navIcons.innerHTML = `
        <ul>
            <li>
              <a href="/conta-usuario">Conta</a>
            </li>

            <li>
              <a href="/seus-pedidos">Pedidos</a>
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
      logout();
    });
  }
}


function logout() {
  if (localStorage.getItem("carrinhoItens") != [] && localStorage.getItem("carrinhoItens") != null) {
    salvarEstadoCarrinho(localStorage.getItem("carrinhoItens"));
  }
  fetch("/efetua-logout", {
    method: "POST",
    headers: {
      Authorization: `Bearer ${localStorage.getItem("token")}`,
    },
  });
  limparLocalStorage();

  const cardIcons = document.querySelector(".nav-icons");
  cardIcons.style.display = "none";
}

document.addEventListener("DOMContentLoaded", function () {
  verificarSessaoExpirada();
  autenticacao();
});
document.getElementById("linkFinalizarPedido").addEventListener("mouseover", function () {
  if (localStorage.getItem("token")) {
    verificarSessaoExpirada();
  }
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
        document.querySelector(".image-modal").src = "/images/icons/error.svg";
        mensagemModal.textContent = "Sessão expirada! Faça login novamente.";
        modalAviso.style.display = "block";
        localStorage.setItem("sessao-expirada", "true");
      } else {
        mensagemModal.textContent =
          "Ocorreu um erro. Repita a ação novamente.";
        modalAviso.style.display = "block";
      }
    });
}

function limparLocalStorage() {
  localStorage.removeItem("token");
  localStorage.removeItem("email");
  localStorage.removeItem("nome");
  localStorage.removeItem("carrinhoItens");
  localStorage.removeItem("pedidoId");
  localStorage.removeItem("carrinhoItens");
  localStorage.removeItem("sessao-expirada");

}
function closeModal() {
  modal.style.display = "none";
  if(localStorage.getItem("sessao-expirada") === "true"){
    window.location.href = "/";
  }
}
botaoFechar.addEventListener("click", closeModal);
window.addEventListener("click", function (event) {
  if (event.target === modal) {
    closeModal();
  }
});

modalAviso.addEventListener("onblur", function(){
  if(localStorage.getItem("sessao-expirada")  === "true"){
    window.location.href = "/";
  }
});
