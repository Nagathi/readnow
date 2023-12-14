document.addEventListener('DOMContentLoaded', function () {
  var stars = document.querySelectorAll('.star-icon');
  var starSelected = 0;

  const email = localStorage.getItem("email");

  const queryString = window.location.search;
  const urlParams = new URLSearchParams(queryString);
  const titulo = urlParams.get('titulo');
  const imagem = urlParams.get('imagem');
  const data = urlParams.get('data');
  const codigo = localStorage.getItem('codigoAvaliacao');

  const containerImagem = document.querySelector('.container-imagem img');
  const descricaoProduto = document.querySelector('.descricao-produto h2');
  const dataPedido = document.querySelector('.descricao-produto p');

  containerImagem.src = `images/livros/${imagem}`;
  descricaoProduto.textContent = titulo;
  dataPedido.textContent = `Pedido em ${data}`

  stars.forEach(function(star) {
    star.addEventListener('click', function(e) {
      var classStar = e.target.classList;
      if (!classStar.contains('ativo')) {
        stars.forEach(function(star) {
          star.classList.remove('ativo');
        });
        classStar.add('ativo');
        starSelected = parseInt(e.target.getAttribute('data-avaliacao'));
      }
    });
  });

  document.querySelector('.btn-submit').addEventListener('click', function () {
    
    const qtdEstrelas = starSelected;
    const descricao = document.getElementById('descricao-problema').value;

    const data = {
      email: email,
      codigo: codigo,
      qtdEstrelas: qtdEstrelas,
      descricao: descricao
    };

    fetch('/avaliacao', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(data)
    })
    .then(response => {
      if(response.ok){
        localStorage.removeItem("codigoAvaliacao")
        alert("Produto avaliado, tentar avaliar novamente esse produto irá alterar a avaliação antiga.")
        window.location.href = "/suas-compras";
      }
    })
    .catch(error => {
      console.error('Erro:', error);
    });
  });
});
