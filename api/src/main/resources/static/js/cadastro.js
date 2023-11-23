const form = document.getElementById("formulario");

function efetuarCadastro() {
  const nome = document.getElementById("nome").value;
  const email = document.getElementById("email").value;
  const senha = document.getElementById("senha").value;
  const confirmacaoSenha = document.getElementById("confirmacao-senha").value;

  if (senha === confirmacaoSenha) {
    const data = {
      nome: nome,
      email: email,
      senha: senha,
    };
    fetch("http://localhost:8080/cadastro", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    });
    alert("Cadastro realizado");

  }
  else{
    alert("Senhas não conferem!");
  }
}


form.addEventListener("submit", function (event) {
  event.preventDefault();
  efetuarCadastro();
});
