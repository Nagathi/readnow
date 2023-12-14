(() => {
  const pictureImage = document.querySelector(".imagem-perfil");
  const containerImagem = document.querySelector(".container-imagem");
  const nomeInput = document.querySelector("#nome");
  const inputFile = document.querySelector("#picture__input");
  const cancelBtn = document.querySelector(".btn-cancelar");
  const continuarBtn = document.querySelector(".btn-continuar");
  const token = localStorage.getItem("token");

  function buscarDadosUsuario(token) {
    fetch(`/busca-foto/${token}`)
      .then((response) => {
        if (!response.ok) {
          throw new Error("Erro ao buscar os dados do usuário.");
        }
        return response.json();
      })
      .then((data) => {
        if(data.foto != null){
          pictureImage.src = "./images/usuarios/" + data.foto;
          containerImagem.classList.add("container-border");
        }

        nomeInput.value = data.nome;
      })
      .catch((error) => {
        console.error("Erro ao buscar os dados do usuário:", error);
      });
  }

  buscarDadosUsuario(token);

  inputFile.addEventListener("change", function (e) {
    const inputTarget = e.target;
    const file = inputTarget.files[0];

    if (file) {
      const reader = new FileReader();

      reader.addEventListener("load", function (e) {
        const readerTarget = e.target;

        pictureImage.src = readerTarget.result;

        containerImagem.classList.add("container-border");
      });

      reader.readAsDataURL(file);
    }
  });

  continuarBtn.addEventListener("click", function () {
    const formData = new FormData();
    const nome = nomeInput.value;
    const file = inputFile.files[0];

    if(file != null){
      formData.append("foto", file);
    }
    
    formData.append("token", token);
    formData.append("nome", nome);

    fetch("/foto", {
      method: "PUT",
      body: formData,
    })
      .then((response) => {
        if (response.ok) {
          window.location.href = "/conta-usuario"
        }
        return response.json();
      })
      .then((data) => {
        console.log("Dados do usuário atualizados:", data);
      })
      .catch((error) => {
        console.error("Erro ao salvar os dados do usuário:", error);
      });
  });

  cancelBtn.addEventListener("click", function () {
    buscarDadosUsuario(email);
  });

  cancelBtn.addEventListener("click", function () {
    window.location.href = "/conta-usuario";
  });
})();
