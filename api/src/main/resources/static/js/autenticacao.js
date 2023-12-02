function autenticacao() {
    const usuarioAutenticado = localStorage.getItem("token");
    const nomeUsuario = localStorage.getItem("nome");
  
    if (usuarioAutenticado != null && nomeUsuario != null) {
      const suaContaButton = document.querySelector(".identificador");
      suaContaButton.innerHTML = `Olá, ${nomeUsuario} <br> Sua conta`
      document.getElementById('conta').addEventListener('click', function() {
        const userMenu = document.getElementById('userMenu');
        userMenu.style.display = (userMenu.style.display === 'block') ? 'none' : 'block';
      });
      document.getElementById('sair').addEventListener('click', function() {
        localStorage.removeItem("token")
  
        const cardIcons = document.querySelector(".card-icons");
  
        cardIcons.style.display = 'none';
       
      });
    }
}
document.addEventListener("DOMContentLoaded", function () {
    autenticacao();
});