// fetch('/enderecos', {
//   method: 'GET',
//   headers: {
//     Authorization: `Bearer ${localStorage.getItem("token")}`,
//   }
// })
// .then(response => {
//   // Lógica de tratamento da resposta
// })
// .catch(error => {
//   console.error('Erro na solicitação:', error);
// });

document.addEventListener("DOMContentLoaded", function () {
  const listaEnderecos = document.querySelector(".lista-enderecos");

  const token = localStorage.getItem("token");

  if (token) {
    fetch(`/enderecos/${token}`)
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
                <p class="telefone">${endereco.telefone}</p>
                <p class="numero-complemento">${endereco.numeroCasa}, ${endereco.complemento}</p>
                <p class="bairro">${endereco.bairro}</p>
                <p class="localidade">${endereco.cidade}, ${endereco.estado}</p>
                <p class="telefone">Telefone: ${endereco.telefone}</p>
              </div>
              <div class="botoes">
                <button onclick="editar('${endereco.codigo}')" class="btn-editar">Editar</button>
                <button onclick="excluir(${endereco.codigo})" class="btn-excluir">Excluir</button>
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

function editar(codigo) {
  localStorage.setItem("codigo", codigo);
  window.location.href = "/editar-endereco";
}

function excluir(codigo) {
  fetch(`/exclui-endereco/${codigo}`, {
    method: "DELETE",
    headers: {
      "Content-Type": "application/json",
    },
  }).then((response) => {
    window.location.href = "/enderecos";
  });
}


