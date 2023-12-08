const pedido = document.querySelector(".pedido");
pedido.textContent = localStorage.getItem("pedidoId")

const dataCompra = document.querySelector(".data");
dataCompra.textContent = localStorage.getItem("dataPedido")
