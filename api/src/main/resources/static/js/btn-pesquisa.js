document.addEventListener('DOMContentLoaded', () => {
  const buttonSearch = document.getElementById('btn-pesquisa');
  const campoPesquisa = document.getElementById('campo-pesquisa');

  buttonSearch.addEventListener('click', () => {
  const valorPesquisa = campoPesquisa.value;
  window.location.href = `/resultado-pesquisa?palavra=${encodeURIComponent(valorPesquisa)}`;
  });
});