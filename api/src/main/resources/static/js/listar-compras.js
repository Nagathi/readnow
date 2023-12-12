document.addEventListener("DOMContentLoaded", function () {

    const token = localStorage.getItem("token");

    fetch(`/pedidos-usuario/${token}`)
      .then(response => response.json())
      .then(pedidos => {
        const containerPedidos = document.querySelector(".container-pedidos");
  
        pedidos.forEach(pedido => {
          const novoCardPedido = document.createElement("div");
          novoCardPedido.classList.add("card-pedido-feito");
          novoCardPedido.innerHTML = `
            <div class="container-imagem">
              <img src="images/livros/${pedido.imagem}" alt="Imagem do livro">
            </div>
            <div class="textos">
              <h2>${pedido.titulo}</h2>
              <p>Pedido em ${pedido.data}</p>
            </div>
          `;
          containerPedidos.appendChild(novoCardPedido);
  
          novoCardPedido.addEventListener("click", function () {
            const titulo = encodeURIComponent(pedido.titulo);
            const imagem = encodeURIComponent(pedido.imagem);
            const codigo = encodeURIComponent(pedido.codigoLivro)
            window.location.href = `avaliacao-produto?codigo=${codigo}&titulo=${titulo}&imagem=${imagem}`;
          });
        });
      })
      .catch(error => {
        console.error('Erro ao obter pedidos:', error);
      });
  });
  