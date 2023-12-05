(() => {

  const inputFile = document.querySelector("#picture__input");
  const pictureImage = document.querySelector(".imagem-perfil");

  inputFile.addEventListener("change", function (e) {
    const inputTarget = e.target;
    const file = inputTarget.files[0];
  
    if (file) {
      const reader = new FileReader();
  
      reader.addEventListener("load", function (e) {
        const readerTarget = e.target;
        
        pictureImage.src = "";
        pictureImage.src = readerTarget.result;
      });
  
      reader.readAsDataURL(file);
    }
  });
  

})();