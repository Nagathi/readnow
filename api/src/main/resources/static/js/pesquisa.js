document.addEventListener('DOMContentLoaded', () => {
    const params = new URLSearchParams(window.location.search);
    const palavra = params.get('palavra');

    if (palavra) {
        fetch(`/pesquisa?palavra=${encodeURIComponent(palavra)}`)
            .then((response) => response.json())
            .then((livros) => {
                const listaLivros = document.querySelector('.lista-livros');

                livros.forEach((livro) => {
                    const novoLivro = document.createElement('li');
                    novoLivro.classList.add('card-livro');
                    novoLivro.innerHTML = `
                        <div class="banner">
                            <img src="images/livros/${livro.imagem}" alt="Produto" class="imagem">
                        </div>
                        <div class="detalhes-livro">
                            <p class="titulo-livro">${livro.titulo}</p>
                            <h3 class="preco">R$ ${livro.preco}</h3>
                            <a href="/livro?codigo=${livro.codigo}" class="ver-produto">Ver produto</a>
                        </div>
                    `;
                    listaLivros.appendChild(novoLivro);
                });
            })
            .catch((error) => {
                console.error('Erro ao buscar livros:', error);
            });
    }
});
