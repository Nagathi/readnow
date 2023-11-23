const form = document.getElementById("formulario");

function efetuarLogin() {
  const email = document.getElementById("email").value;
  const senha = document.getElementById("senha").value;

  const data = {
    email: email,
    senha: senha,
  };
  fetch("http://localhost:8080/login", {
    method: "POST",
    headers: {
      "Accept": "application/json",
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data),
  });
}

form.addEventListener("submit", function (event) {
  event.preventDefault();
  efetuarLogin();
  alert("Login efetuado");
});
