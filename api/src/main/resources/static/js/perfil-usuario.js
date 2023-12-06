(() => {
  const pictureImage = document.querySelector(".imagem-perfil");
  const email = localStorage.getItem("email");

  function buscarFotoUsuario(email) {
    fetch(`/busca-foto?email=${email}`)
      .then((response) => {
        if (!response.ok) {
          throw new Error("Erro ao buscar a foto do usuário.");
        }
        return response.text();
      })
      .then((imageUrl) => {
        pictureImage.src = "./images/usuarios/" + imageUrl;
      })
      .catch((error) => {
        console.error("Erro ao buscar a foto do usuário:", error);
      });
  }

  buscarFotoUsuario(email);

  const inputFile = document.querySelector("#picture__input");

  inputFile.addEventListener("change", function (e) {
    const inputTarget = e.target;
    const file = inputTarget.files[0];

    if (file) {
      const reader = new FileReader();

      reader.addEventListener("load", function (e) {
        const readerTarget = e.target;

        pictureImage.src = "";
        pictureImage.src = readerTarget.result;

        const formData = new FormData();
        formData.append("foto", file);
        formData.append("email", email);

        fetch("/foto", {
          method: "PUT",
          body: formData,
        })
          .then((response) => {
            if (!response.ok) {
              throw new Error("Erro ao enviar a imagem.");
            }
            return response.json();
          })
          .then((data) => {
            console.log("Resposta da API:", data);
          })
          .catch((error) => {
            console.error("Erro ao enviar a imagem:", error);
          });
      });

      reader.readAsDataURL(file);
    }
  });
})();
