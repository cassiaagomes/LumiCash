document.querySelectorAll(".account-type-option").forEach((option, index) => {
  option.addEventListener("click", function () {
    // Remove a seleção de todos
    document
      .querySelectorAll(".account-type-option")
      .forEach((opt) => opt.classList.remove("selected"));
    // Adiciona a seleção no clicado
    this.classList.add("selected");
    // Marca o radio button escondido correspondente
    document.getElementById(
      index === 0 ? "tipoCartao" : "tipoCorrente"
    ).checked = true;
  });
});
