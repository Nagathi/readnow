const pedido = document.querySelector(".pedido");
pedido.textContent = localStorage.getItem("pedidoId");

const dataCompra = document.querySelector(".data");
dataCompra.textContent = localStorage.getItem("dataPedido");

const horaCompra = document.querySelector(".horario");
horaCompra.textContent = localStorage.getItem("horaPedido");

localStorage.removeItem("paginaFinalizacao");
localStorage.removeItem("pagina-livro");
localStorage.removeItem("livroComprarAgora");