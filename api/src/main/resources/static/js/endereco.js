document.addEventListener("DOMContentLoaded", function () {
    const listaEnderecos = document.querySelector(".lista-enderecos");
  
    const email = localStorage.getItem('email');
  
    if (email) {
      fetch(`/enderecos/${email}`)
        .then((response) => response.json())
        .then((data) => {
          listaEnderecos.innerHTML = "";
  
          data.forEach((endereco) => {
            const novoEndereco = document.createElement("li");
            novoEndereco.classList.add("card-endereco");
            novoEndereco.innerHTML = `
              <div class="detalhes-enderecos">
                <h2 class="nome-destinatario">${endereco.nomeDestino}</h2>
                <p class="endereco">${endereco.logradouro}</p>
                <p class="numero-complemento">${endereco.numeroCasa}, ${endereco.complemento}</p>
                <p class="bairro">${endereco.bairro}</p>
                <p class="localidade">${endereco.cidade}, ${endereco.estado}</p>
                <p class="telefone">Telefone: ${endereco.telefone}</p>
              </div>
              <div class="botoes">
                <button class="btn-editar">Editar</button>
                <button class="btn-excluir">Excluir</button>
              </div>
            `;
            listaEnderecos.appendChild(novoEndereco);
          });
        })
        .catch((error) => {
          console.error("Erro ao obter endereços:", error);
        });
    } else {
      console.error("E-mail não encontrado no localStorage");
    }
  });