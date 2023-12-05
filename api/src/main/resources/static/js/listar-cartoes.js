document.addEventListener("DOMContentLoaded", function () {
    const listaCartoes = document.querySelector(".lista-cartoes");
    const email = localStorage.getItem('email');

    fetch(`/cartoes/${email}`)
        .then((response) => response.json())
        .then((data) => {
            listaCartoes.innerHTML = "";

            data.forEach((cartao) => {
                const novoCartao = document.createElement("li");
                novoCartao.classList.add("card-cartao");
                novoCartao.innerHTML = `
                    <div class="detalhes-cartao">
                        <h2 class="numero-cartao">Cartão termina em ${cartao.numero.substr(-4)}</h2>
                        <p class="nome-cartao">${cartao.nome}</p>
                        <p class="data-expiracao">Expira em ${cartao.data}</p>
                    </div>
                    <div class="botoes">
                        <button onclick="editar(${cartao.codigo})" class="btn-editar">Editar</button>
                        <button onclick="excluir(${cartao.codigo})" class="btn-excluir">Excluir</button>
                    </div>
                `;
                listaCartoes.appendChild(novoCartao);
            });
        })
        .catch((error) => {
            console.error("Erro ao carregar os cartões:", error);
        });
});

function excluir(codigo) {
    const email = localStorage.getItem("email");
    const data = { email, codigo };

    fetch('/exclui-cartao', {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        }).then(response => {
            if(response.ok){
                window.location.href = "/carteira"
            }
        })
} 

function editar(codigo) {
    localStorage.setItem("codigoCartao", codigo);
    window.location.href = "/edita-cartao";
}