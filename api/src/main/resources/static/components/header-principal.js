(() => {

    class HeaderPrincipal extends HTMLElement {
      constructor() {
        super();
      }
  
      connectedCallback() {
        this.innerHTML = `
  
          <header class="cabecalho">
            <a href="/"
              ><img src="images/icons/logo.svg" alt="Logo" class="logo"
            /></a>
      
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
            <a href="/login" class="button-login">Login</a>
            <a href="/cadastro-cliente" class="button-login">Cadastro</a>  
          </header>

        `;
      }
    }
  
    customElements.define('header-principal', HeaderPrincipal);
  
  })();
