var stars = document.querySelectorAll('.star-icon');

document.addEventListener('click', function (e) {
  var classStar = e.target.classList;
  if (!classStar.contains('ativo')) {
    stars.forEach(function (star) {
      star.classList.remove('ativo');
    });
    classStar.add('ativo');
    console.log(e.target.getAttribute('data-avaliacao'));
  }
});
document.getElementById('selectImageButton').addEventListener('click', function () {
  document.getElementById('fileInput').click();
});

function handleFileSelect(event) {
  const fileInput = event.target;
  const selectedImageContainer = document.getElementById('selectedImageContainer');
  const selectedImage = document.getElementById('selectedImage');

  if (fileInput.files && fileInput.files[0]) {
    const reader = new FileReader();

    reader.onload = function (e) {
      selectedImage.src = e.target.result;
      selectedImageContainer.style.display = 'block';
    };

    reader.readAsDataURL(fileInput.files[0]);
  }
}

