fetch("http://localhost:8080/", { mode: "no-cors" })
  .then((data) => data.json())
  .then(console.log)
  .catch((error) => console.log(error));