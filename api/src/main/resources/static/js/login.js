const form = document.getElementById("formulario");
const modal = document.getElementById("modal");
const modalMessage = document.getElementById("modal-message");
const closeButton = document.querySelector(".close");

function closeModal() {
  modal.style.display = "none";
}

function efetuarLogin() {
  const email = document.getElementById("email").value;
  const senha = document.getElementById("senha").value;

  const data = {
    email: email,
    senha: senha,
  };
  fetch("/efetua-login", {
    method: "POST",
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data),
  })
    .then((response) => {
      if (response.ok) {
      } else {
        modalMessage.textContent =
          "Erro ao efetuar login. Email ou senha incorretos.";
        modal.style.display = "block";
      }
      return response.json();
    })
    .then((data) => {
      const token = data.token;
      // localStorage.setItem("token", token);
      autenticar(token);

    })
    .catch((error) => {
      modalMessage.textContent = "Ocorreu um erro ao processar a solicitação.";
      modal.style.display = "block";
      console.error("Erro:", error);
    });
}

function autenticar(uuid) {

  fetch(`/autenticacao?uuid=${uuid}` ,{
    method: "GET",
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json",
    },
  })
    .then((response) => {
      // if (response.ok) {
      //   window.location.href = "/";
      // } else {
      //   modalMessage.textContent =
      //     "Erro ao efetuar login. Email ou senha incorretos.";
      //   modal.style.display = "block";
      // }
      return response.json();
    })
    .then((data) => {
      const token = data.token;
      const nome = data.nome;
      const email = data.email;

      localStorage.setItem("token", token);
      localStorage.setItem("nome", nome);
      localStorage.setItem("email", email);
      window.location.href = "/";

     

    })
    .catch((error) => {
      modalMessage.textContent = "Ocorreu um erro ao processar a solicitação.";
      modal.style.display = "block";
      console.error("Erro:", error);
    });
}

form.addEventListener("submit", function (event) {
  event.preventDefault();
  efetuarLogin();
});

closeButton.addEventListener("click", closeModal);
window.addEventListener("click", function (event) {
  if (event.target === modal) {
    closeModal();
  }
});
