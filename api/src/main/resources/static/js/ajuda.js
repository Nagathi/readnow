function obterPedidos(token) {
    fetch(`/pedidos-ajuda/${token}`)
      .then(response => response.json())
      .then(data => atualizarPedidosHTML(data))
      .catch(error => console.error('Ocorreu um erro ao obter os pedidos:', error));
  }
  
  function atualizarPedidosHTML(pedidos) {
    const containerPedidos = document.querySelector('.container-pedidos');
  
    containerPedidos.innerHTML = '';
  
    pedidos.forEach(pedido => {
      const cardPedido = document.createElement('div');
      cardPedido.classList.add('card-pedido-feito');
  
      const containerImagem = document.createElement('div');
      containerImagem.classList.add('container-imagem');
  
      const imagem = document.createElement('img');
      imagem.src = "images/livros/" + pedido.imagem;
      imagem.alt = 'Imagem do livro';
  
      containerImagem.appendChild(imagem);
  
      const textos = document.createElement('div');
      textos.classList.add('textos');
  
      const titulo = document.createElement('h2');
      titulo.textContent = pedido.titulo;
  
      const dataPedido = document.createElement('p');
      dataPedido.textContent = `Pedido em ${pedido.diaMes} de ${pedido.ano}`;
  
      textos.appendChild(titulo);
      textos.appendChild(dataPedido);
  
      cardPedido.appendChild(containerImagem);
      cardPedido.appendChild(textos);

      cardPedido.addEventListener('click', () => {
        redirecionarParaFormularioAjuda(pedido);
      });
  
      containerPedidos.appendChild(cardPedido);
    });
  }

  function redirecionarParaFormularioAjuda(pedido) {

    const {diaMes, ano, imagem, titulo } = pedido;

    localStorage.setItem("codigoLivro", pedido.codigo)
  
    const parametrosURL = `?diaMes=${diaMes}&ano=${ano}&imagem=${imagem}&titulo=${titulo}`;
    const url = `/formulario-ajuda${parametrosURL}`;
  
    window.location.href = url;
  }
  
  document.addEventListener('DOMContentLoaded', () => {
    const token = localStorage.getItem("token");
    obterPedidos(token);
  });
  