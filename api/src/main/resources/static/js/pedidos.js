document.addEventListener("DOMContentLoaded", function () {
    const token = localStorage.getItem("token");
  
    fetch(`/pedidos-pendentes/${token}`)
      .then(response => response.json())
      .then(pedidos => {
        const listaPedidos = document.querySelector(".lista-pedidos");
  
        pedidos.forEach(pedido => {
          const novoPedido = document.createElement("div");
          novoPedido.classList.add("painel-interno");
  
          novoPedido.innerHTML = `
            <div class="container-imagem">
              <img src="images/livros/${pedido.imagem}" alt="Imagem do livro" class="imagem">
            </div>
  
            <div class="div-textos-internos">
              <h3 id="textoInternoTempoDias">Data aproximada de entrega: ${pedido.dataEntrega}</h3>
              <div class="textoInternoDescricao">
                <p>Pedido em ${pedido.data}</p>
                <p>${pedido.titulo}</p>
              </div>
            </div>
  
            <div class="div-botao">
              <button class="bnt" type="button" onclick="ajuda()">Precisa de ajuda?</button>
            </div>
          `;
  
          listaPedidos.appendChild(novoPedido);
        });
      })
      .catch(error => {
        console.error('Erro ao obter pedidos pendentes:', error);
      });
  });

  function ajuda(){
    window.location.href = "/central-ajuda"
  }
  