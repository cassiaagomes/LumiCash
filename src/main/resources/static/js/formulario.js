// Seleciona as opções de tipo de conta e gerencia seleção visual + rádio oculto
document.querySelectorAll(".account-type-option").forEach((option, index) => {
  option.addEventListener("click", function () {
    // Remove seleção de todas as opções
    document
      .querySelectorAll(".account-type-option")
      .forEach((opt) => opt.classList.remove("selected"));
    // Seleciona a clicada
    this.classList.add("selected");
    // Marca o input radio correspondente
    document.getElementById(index === 0 ? "tipoCartao" : "tipoCorrente").checked = true;
    // Atualiza visibilidade do campo dia de fechamento
    atualizarVisibilidadeDiaFechamento();
    // Atualiza validação
    validar();
  });
});

// Função que atualiza a visibilidade do campo "Dia do fechamento"
function atualizarVisibilidadeDiaFechamento() {
  const tipoCartao = document.getElementById("tipoCartao").checked;
  const diaFechamentoWrapper = document.getElementById("wrapperDiaFechamento");
  const diaFechamentoInput = document.getElementById("diaFechamento");

  if (tipoCartao) {
    diaFechamentoWrapper.style.display = "block";
    diaFechamentoInput.required = true;
  } else {
    diaFechamentoWrapper.style.display = "none";
    diaFechamentoInput.required = false;
    diaFechamentoInput.value = "";
  }
}

// Função para validar os inputs obrigatórios do formulário
function validar() {
  const form = document.querySelector("form");
  const inputs = form.querySelectorAll("input[required]");
  const btnSalvar = document.getElementById("btnSalvar");

  let valido = true;
  inputs.forEach((input) => {
    if (!input.value.trim()) valido = false;
  });

  btnSalvar.disabled = !valido;
}

// Valida inputs ao digitar
document.querySelectorAll("input[required]").forEach((input) => {
  input.addEventListener("input", validar);
});

// Atualiza visibilidade e validação ao carregar a página
document.addEventListener("DOMContentLoaded", () => {
  atualizarVisibilidadeDiaFechamento();
  validar();
});
