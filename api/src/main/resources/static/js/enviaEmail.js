document.addEventListener('DOMContentLoaded', function() {
    const btnEnviarEmail = document.querySelector('.btn-submit');
  
    btnEnviarEmail.addEventListener('click', function() {
      const email = document.querySelector('input[type="text"]').value;
  
      if (email.trim() !== '') {
        const data = { email };
  
        fetch('/envia-email', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(data),
        })
        .then(response => {
          if (response.ok) {
            showModal("Email enviado com sucesso!");
          } else if (response.status === 404) {
            showModal("E-mail não encontrado!");
          } else {
            throw new Error('Falha ao enviar o email');
          }
        })
        .catch(error => {
          showModal('Erro ao enviar o email: ' + error.message);
        });
      } else {
        showModal('Por favor, preencha o campo de email.');
      }
    });
  
    function showModal(message) {
      const modal = document.getElementById("modal");
      const modalMessage = document.getElementById("modal-message");
      const closeButton = document.querySelector(".close");
  
      modalMessage.textContent = message;
      modal.style.display = "block";
  
      closeButton.addEventListener("click", function() {
        modal.style.display = "none";
      });
  
      window.addEventListener("click", function(event) {
        if (event.target === modal) {
          modal.style.display = "none";
        }
      });
    }
  });
  