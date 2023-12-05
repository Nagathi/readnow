const form = document.getElementById("formulario");
const modal = document.getElementById("modal");
const modalMessage = document.getElementById("modal-message");
const closeButton = document.querySelector(".close");

function closeModal() {
  modal.style.display = "none";
}

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
        modalMessage.textContent = "Ocorreu um erro ao processar o cadastro.";
        modal.style.display = "block";
      }
    })
  }
  else{
    modalMessage.textContent = "Senhas não conferem!";
    modal.style.display = "block";
  }
}

function goToHome(){
  window.location.href = '/';
}

form.addEventListener("submit", function (event) {
  event.preventDefault();
  efetuarCadastro();
});

closeButton.addEventListener("click", closeModal);
window.addEventListener("click", function (event) {
  if (event.target === modal) {
    closeModal();
  }
});