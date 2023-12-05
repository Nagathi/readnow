(() => {

  const listaDeEnderecos = document.querySelectorAll('.endereco');
  const listaDeCartoes = document.querySelectorAll('.cartao');

  function selecionaEndereco() {
    listaDeEnderecos.forEach((endereco) => {
      endereco.removeAttribute('id');
      this.id = 'endereco-selecionado';
    })
  }

  listaDeEnderecos.forEach((endereco) => {
    endereco.addEventListener('click', selecionaEndereco);
  });

  function selecionaCartao() {
    listaDeCartoes.forEach((cartao) => {
      cartao.removeAttribute('id');
      this.id = 'cartao-selecionado';
    })
  }

  listaDeCartoes.forEach((cartao) => {
    cartao.addEventListener('click', selecionaCartao);
  });

})();