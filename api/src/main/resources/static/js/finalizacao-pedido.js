const email = localStorage.getItem("email");

(() => {

  const listaDeEnderecos = document.querySelectorAll('.endereco');
  const listaDeCartoes = document.querySelectorAll('.cartao');

  function selecionaEndereco() {
    listaDeEnderecos.forEach((endereco) => {
      endereco.removeAttribute('id');
      this.id = 'endereco-selecionado';
    })
  }

  listaDeEnderecos.forEach((endereco) => {
    endereco.addEventListener('click', selecionaEndereco);
  });

  function selecionaCartao() {
    listaDeCartoes.forEach((cartao) => {
      cartao.removeAttribute('id');
      this.id = 'cartao-selecionado';
    })
  }

  listaDeCartoes.forEach((cartao) => {
    cartao.addEventListener('click', selecionaCartao);
  });

})();

function buscarEnderecos() {
  if (email) {
    fetch(`/enderecos/${email}`)
      .then((response) => response.json())
      .then((data) => {

        data.forEach((endereco) => {
          const enderecosDiv = document.querySelector(".enderecos");
          const element = document.createElement("div");
          element.classList.add("endereco");
          element.innerHTML = ` 
        <label>
          <input type="radio" name="endereco" value="1">
            <div class="informacoes">
              <span class="destinatario">${endereco.nomeDestino}<br></span>
              <span class="informacao-endereco">${endereco.logradouro}, ${endereco.numeroCasa}, ${endereco.complemento}, ${endereco.bairro}, ${endereco.cidade}, ${endereco.estado}<br>Telefone: ${endereco.telefone}</span>
            </div>
        <label>`;

          enderecosDiv.appendChild(element);
         
        });
      })
      .catch((error) => {
        console.error("Erro ao obter endereços:", error);
      });
  } else {
    console.error("E-mail não encontrado no localStorage");
  }
}

document.addEventListener("DOMContentLoaded", function() {
  buscarEnderecos();

});

