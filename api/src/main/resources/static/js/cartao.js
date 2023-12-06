function cadastrar() {
    var pagina;
    const paginaFinalizacaoPedido =
        localStorage.getItem("paginaFinalizacao");

    if (paginaFinalizacaoPedido != null) {
        pagina = paginaFinalizacaoPedido;
    }
    else{
        pagina = "/carteira";
    }

    const nome = document.getElementById("nome").value;
    const numero = document.getElementById("numero-cartao").value;
    const mes = document.getElementById("mes-expiracao").value;
    const ano = document.getElementById("ano-expiracao").value;
    const email = localStorage.getItem("email");

    const novoCartao = {
        nome: nome,
        numero: numero,
        data: mes + "/" + ano,
        email: email,
    };

    fetch("/cadastra-cartao", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(novoCartao),
    })
        .then((response) => {
            if (response.ok) {
                window.location.href = pagina;
            } else {
                throw new Error("Erro ao cadastrar cartão");
            }
        })
        .catch((error) => {
            console.error("Erro:", error);
        });
}

function editar() {
    const codigo = localStorage.getItem("codigoCartao");
    const email = localStorage.getItem("email");

    const nome = document.getElementById("nome").value;
    const numero = document.getElementById("numero-cartao").value;
    const mesExpiracao = document.getElementById("mes-expiracao").value;
    const anoExpiracao = document.getElementById("ano-expiracao").value;

    const data = {
        codigo,
        email,
        nome,
        numero,
        data: `${mesExpiracao}/${anoExpiracao}`,
    };

    fetch("/edita-cartao", {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
    }).then((response) => {
        if (response.ok) {
            window.location.href = "/carteira";
        }
    });
}
