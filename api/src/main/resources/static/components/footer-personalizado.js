(() => {

  class FooterPersonalizado extends HTMLElement {
    constructor() {
      super();
    }

    connectedCallback() {
      this.innerHTML = `

      <footer class="roda-pe">
        <ul>
          <li>
            <img src="images/icons/logo.svg" alt="Logo">
          </li>
          <li class="links">
            <a href="#" class="link-rodape">Quem somos nós</a>
            <a href="#" class="link-rodape">Política de privacidade</a>
          </li>
          <li class="links">
            <a href="#" class="link-rodape">Programa de fidelidade</a> 
            <a href="#" class="link-rodape">Atendimento ao cliente</a>
          </li>
          <li class="links">
            <a href="#" class="link-rodape">Quero ser franqueado</a> 
            <a href="#" class="link-rodape">Anuncie aqui</a>
          </li>
          <li>
            <div class="redes-sociais">
              <a href="#">
                <img src="images/icons/facebook-icon.svg" alt="Logo Facebook" class="icon-rede-social">
              </a>
    
              <a href="#">
                <img src="images/icons/twitter-icon.svg" alt="Logo Twitter" class="icon-rede-social">
              </a>
    
              <a href="#">
                <img src="images/icons/instagram-icon.svg" alt="" class="icon-rede-social">
              </a>
            </div>
          </li>
        </ul>
      </footer>

      `;
    }
  }

  customElements.define('footer-personalizado', FooterPersonalizado);

})();