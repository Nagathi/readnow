function cadastrar(){
    const nome = document.getElementById('nome').value;
    const numero = document.getElementById('numero-cartao').value;
    const mes = document.getElementById('mes-expiracao').value;
    const ano = document.getElementById('ano-expiracao').value;
    const email = localStorage.getItem('email');

    const novoCartao = {
        nome: nome,
        numero: numero,
        data: mes + "/" + ano,
        email: email
    }

    fetch('/cadastra-cartao', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(novoCartao)
    })
    .then(response => {
    if (response.ok) {
            window.location.href = "/conta-usuario";
    } else {
          throw new Error('Erro ao cadastrar endereço');
    }
    })
    .catch(error => {
        console.error('Erro:', error);
    });
}