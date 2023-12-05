document.addEventListener("DOMContentLoaded", async function () {
    const codigo = localStorage.getItem('codigo');

    if (codigo) {
        try {
            const response = await fetch(`/endereco/${codigo}`);
            if (response.ok) {
                const endereco = await response.json();
                preencherCampos(endereco);
            } else {
                console.error("Erro ao obter endereço:", response.status);
            }
        } catch (error) {
            console.error("Erro ao obter endereço:", error);
        }
    } else {
        console.error("Código não encontrado na URL");
    }
});

function preencherCampos(endereco) {
    document.getElementById('nome').value = endereco.nomeDestino;
    document.getElementById('bairro').value = endereco.bairro;
    document.getElementById('numero-telefone').value = endereco.telefone;
    document.getElementById('endereco').value = endereco.logradouro;
    document.getElementById('numero-residencia').value = endereco.numeroCasa;
    document.getElementById('cep').value = endereco.cep;
    document.getElementById('complemento').value = endereco.complemento;
    document.getElementById('cidade').value = endereco.cidade;
    document.getElementById('estado').value = endereco.estado;
    document.getElementById('pais').value = endereco.pais;
}

async function editar() {

    const codigo = localStorage.getItem('codigo');
    const nomeDestino = document.getElementById('nome').value;
    const bairro = document.getElementById('bairro').value;
    const logradouro = document.getElementById('endereco').value;
    const telefone = document.getElementById('numero-telefone').value;
    const numeroCasa = document.getElementById('numero-residencia').value;
    const cep = document.getElementById('cep').value;
    const complemento = document.getElementById('complemento').value;
    const cidade = document.getElementById('cidade').value;
    const estado = document.getElementById('estado').value;
    const pais = document.getElementById('pais').value;

    const data = {
        codigo,
        nomeDestino,
        logradouro,
        telefone,
        bairro,
        numeroCasa,
        cep,
        complemento,
        cidade,
        estado,
        pais
    };

    try {
        const response = await fetch(`/edita-endereco`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            localStorage.removeItem('codigo');
            window.location.href = "/enderecos";
        } else {
            console.error("Erro ao editar endereço:", response.status);
        }
    } catch (error) {
        console.error("Erro ao editar endereço:", error);
    }
}
