document.addEventListener('DOMContentLoaded', function () {
  var stars = document.querySelectorAll('.star-icon');
  var formularioAvaliacao = document.getElementById('formulario-avaliacao');
  var starSelected = 0;

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

  document.getElementById('selectImageButton').addEventListener('click', function () {
    document.getElementById('fileInput').click();
  });

  formularioAvaliacao.addEventListener('submit', function (e) {
    e.preventDefault();

    const qtdEstrelas = starSelected;
    const descricao = document.getElementById('descricao-problema').value;

    const emailLivro = "ragbr7070@gmail.com";
    const codigoLivro = 1;

    const data = {
      email: emailLivro,
      codigo: codigoLivro,
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
        window.location.href = "/";
      }
    })
    .catch(error => {
      console.error('Erro:', error);
    });
  });
});
