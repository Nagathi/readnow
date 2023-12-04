document.addEventListener("DOMContentLoaded", function () {
    const codigo = localStorage.getItem('codigo');

    if (codigo) {
        fetch(`/endereco/${codigo}`)
            .then((response) => response.json())
            .then((endereco) => {
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
            })
            .catch((error) => {
                console.error("Erro ao obter endereço:", error);
            });
    } else {
        console.error("Código não encontrado na URL");
    }
});

function editar() {
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

    fetch(`/edita-endereco`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (response.ok) {
            localStorage.removeItem('codigo')
            window.location.href = "/enderecos";
        }
    })
    .catch(error => {
        alert(error);
    });
}
