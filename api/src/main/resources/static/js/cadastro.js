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
    fetch("/efetua-cadastro", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    }).then(response => {
      if(response.ok){
        window.location.href = '/cadastrado';
      }else{
        alert("Ocorreu um erro ao processar o cadastro")
      }
    })
  }
  else{
    alert("Senhas não conferem!");
  }
}

function goToHome(){
  window.location.href = '/';
}


form.addEventListener("submit", function (event) {
  event.preventDefault();
  efetuarCadastro();
});
