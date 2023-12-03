document.addEventListener("DOMContentLoaded", function () {
  const listaLivros = document.querySelector(".card-itens-carrinho");

  const email = localStorage.getItem("email");

  if (email) {
    fetch(`/mostra-carrinho/${email}`)
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
        data.forEach((item) => {
          const novoLivro = document.createElement("li");
          novoLivro.classList.add("item-carrinho");
          novoLivro.innerHTML = `
            <div class="container-imagem">
                <img src="./images/livros/${item.livro.imagem}" alt="Imagem Produto">
            </div>

            <div class="direita">
              <div class="textos">
                <a href="#">
                  <h1 class="nome-livro">${item.livro.titulo}</h1>
                </a>
                <p class="preco">R$ ${item.livro.preco.toFixed(2)}</p>
              </div>

              <div class="detalhes">
                <ul class="classificacao">
                  <li>
                    <img src="images/icons/star-with-fill.svg" alt="">
                  </li>
  
                  <li>
                    <img src="images/icons/star-with-fill.svg" alt="">
                  </li>
  
                  <li>
                    <img src="images/icons/star-with-fill.svg" alt="">
                  </li>
  
                  <li>
                    <img src="images/icons/star-with-fill.svg" alt="">
                  </li>
  
                  <li>
                    <img src="images/icons/star-with-fill.svg" alt="">
                  </li>
                </ul>
    
                <p class="disponibilidade disponivel">Em estoque</p>
  
                <div class="botoes">
                  <select id="quantidade">
                    <option selected disabled value="">${
                      item.quantidade
                    }</option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                    <option value="6">6</option>
                    <option value="7">7</option>
                    <option value="8">8</option>
                    <option value="9">9</option>
                    <option value="10">10</option>
                  </select>
                  <button class="btn-excluir">Excluir</button>
                </div>
              </div>
            </div>  
            `;
          listaLivros.appendChild(novoLivro);
        });
      })
      .catch((error) => {
        console.error("Erro ao obter livros:", error);
      });
  } else {
    console.error("E-mail não encontrado no localStorage");
  }
});
