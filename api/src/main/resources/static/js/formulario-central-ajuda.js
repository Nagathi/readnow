(() => {
  const listaProblemas = document.querySelectorAll('.problema');

  function activeButton() {
    listaProblemas.forEach((button) => {
      button.removeAttribute('id');
      this.id = 'selecionado';
    })
  }

  listaProblemas.forEach((item) => {
    item.addEventListener('click', activeButton);
  });

})();