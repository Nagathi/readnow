(() => {

  class HeaderPrincipal extends HTMLElement {
    constructor() {
      super();
    }

    connectedCallback() {
      this.innerHTML = `
      
        <header class="cabecalho">
          <img src="images/icons/logo.svg" alt="Logo" class="logo">
          
          <div class="box-search">
            <input type="text" placeholder="O que deseja encontrar?" class="box-input">
            <button class="button-search">
              <img src="images/icons/lupa.png" alt="Lupa">
            </button>
          </div>
      
          <nav class="opcoes">
            <button class="item">
              <a href="#">
                <img src="images/icons/user.svg" alt="Carrinho de compras" style="width: 3.6rem ;">
                <span class="identificador">Sua conta</span>
              </a>
            </button>
      
            <button class="item">
              <a href="#">
                <img src="images/icons/ouvidoria.svg" alt="Carrinho de compras" style="width: 3.6rem ;">
                <span class="identificador">Ouvidoria</span>
              </a>
            </button>
      
            <button class="item" id="carrinho-compras">
              <a href="#">
                <img src="images/icons/iconCarrier.svg" alt="Carrinho de compras" style="width: 3.6rem ;">
                <span class="identificador">Carrinho</span>
      
                <div class="produtos-carrinho">
                  <span class="quantidade">9</span>
                  <span class="simbolo-mais">+</span>
                </div>
              </a>
            </button>
          </nav>
        </header>

      `;
    }

  }

  customElements.define('header-principal', HeaderPrincipal);

})();