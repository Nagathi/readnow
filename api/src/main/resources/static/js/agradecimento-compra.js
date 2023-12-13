const pedido = document.querySelector(".pedido");
pedido.textContent = localStorage.getItem("pedidoId");

const dataCompra = document.querySelector(".data");
dataCompra.textContent = localStorage.getItem("dataPedido");

const horaCompra = document.querySelector(".horario");
horaCompra.textContent = localStorage.getItem("horaPedido");

localStorage.removeItem("paginaFinalizacao");
localStorage.removeItem("pagina-livro");
localStorage.removeItem("livroComprarAgora");
localStorage.removeItem("valorTotalCarrinho");

document.addEventListener("visibilitychange", function () {
  if (document.visibilityState === "hidden") {
    localStorage.removeItem("dataPedido");
    localStorage.removeItem("horaPedido");
    localStorage.removeItem("pedidoId");
  }
});
