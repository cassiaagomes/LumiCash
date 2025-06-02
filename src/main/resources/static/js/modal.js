const modal = document.getElementById("modalCadastro");
const openBtn = document.querySelector(".cadastrar");
const closeBtn = document.querySelector(".modal-close");

openBtn.addEventListener("click", () => {
  modal.style.display = "flex";
});

closeBtn.addEventListener("click", () => {
  modal.style.display = "none";
});

// Fecha o modal ao clicar fora dele
window.addEventListener("click", (event) => {
  if (event.target === modal) {
    modal.style.display = "none";
  }
});
