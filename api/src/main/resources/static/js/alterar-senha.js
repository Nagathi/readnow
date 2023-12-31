const urlParams = new URLSearchParams(window.location.search);

const linkParam = urlParams.get('link');

if (linkParam) {
    fetch(`redefine-senha/${linkParam}`)
        .then(response => {
            if (response.ok) {
            } else {
                console.log(response)
                console.error('Link inválido. Redirecionando para outra página.');
                window.location.href = '/';
            }
        })
        .catch(error => {
            console.error('Erro ao fazer requisição:', error);

            window.location.href = '/';
        });
} else {
    console.error('Parâmetro "link" não encontrado na URL.');
    window.location.href = '/';
}

const btnSubmit = document.querySelector('.btn-submit');

btnSubmit.addEventListener('click', () => {
    const novaSenha = document.querySelector('input[placeholder="Nova Senha"]').value;
    const confirmacaoSenha = document.querySelector('input[placeholder="Confirmar nova senha"]').value;

    if (novaSenha !== confirmacaoSenha) {
        alert('As senhas não coincidem.');
        return;
    }

    const dados = {
        token: linkParam,
        senha: novaSenha
    };

    fetch('/altera-senha', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(dados)
    })
    .then(response => {
        if (response.ok) {
            alert('Senha alterada com sucesso.');
        } else {
            alert('Erro ao alterar a senha.');
        }
    })
    .catch(error => {
        console.error('Erro ao fazer requisição:', error);
    });
});
