function autenticacao() {
    const usuarioAutenticado = localStorage.getItem("token");
    const nomeUsuario = localStorage.getItem("nome");
  
    if (usuarioAutenticado != null && nomeUsuario != null) {
      const suaContaButton = document.querySelector(".identificador");
      suaContaButton.innerHTML = `<br> Olá, ${nomeUsuario} <br> Sua conta`

      const navIcons = document.createElement("div");
      navIcons.classList.add("menu-list");
      const header = document.querySelector(".nav-icons");
      navIcons.innerHTML = `
        <ul>
            <li><a href="#">Seus pedidos</a></li>
            <li><a href="#">Seus endereços</a></li>
            <li><a href="#">Configurações</a></li>
            <li id="sair"><a href="/">Sair da conta</a></li>
        </ul>
      `
      header.appendChild(navIcons);

      document.getElementById('conta').addEventListener('mouseover', function() {
        const userMenu = document.querySelector('.menu-list');
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