(() => {
  class HeaderAutenticado extends HTMLElement {
    constructor() {
      super();
    }

    connectedCallback() {
      this.innerHTML = `
  
        <header class="cabecalho">
            <a href="/">
                <img src="images/icons/logo.svg" alt="Logo" class="logo"/>
            </a>
    
            <div class="box-search">
                <input
                    type="text"
                    placeholder="O que deseja encontrar?"
                    class="box-input"
                    id="campo-pesquisa"
                />
                <button id="btn-pesquisa" class="button-search">
                    <img src="images/icons/lupa.png" alt="Lupa" />
                </button>
            </div>
           
            <nav class="nav-icons">
            
                <div class=opcoes>
                    <div id ="conta" class="item">
                        <a href="/conta-usuario">
                            <img src="images/icons/user.svg" alt="Perfil do usuário">
                            <span class="identificador">Sua conta</span>
                        </a>
                    </div>
        
                    <div class="item" id="carrinho-compras">
                        <a href="/carrinho">
                            <img src="images/icons/iconCarrier.svg" alt="Carrinho de compras">
                            <span class="identificador">Carrinho</span>
                
                            <div class="produtos-carrinho">
                            <span class="quantidade">9</span>
                            <span class="simbolo-mais">+</span>
                            </div>
                        </a>
                    </div>

                    <div class="item">
                        <a href="/central-ajuda">
                            <img src="images/icons/ouvidoria.svg" alt="Fale conosco">
                            <span class="identificador">Ajuda</span>
                        </a>
                    </div>
                    
                </div>
            </nav>
        </header>
        `;
    }
  }

  customElements.define("header-autenticado", HeaderAutenticado);
})();
