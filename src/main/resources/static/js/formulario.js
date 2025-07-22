// Função para inicializar a seleção do tipo de conta (Cartão / Corrente)
function inicializarTipoConta() {
  const opcoes = document.querySelectorAll(".account-type-option");
  const tipoCartaoInput = document.getElementById("tipoCartao");
  const tipoCorrenteInput = document.getElementById("tipoCorrente");

  if (!opcoes.length || !tipoCartaoInput || !tipoCorrenteInput) return;

  opcoes.forEach((option, index) => {
    option.addEventListener("click", function () {
      // Remove seleção anterior
      opcoes.forEach((opt) => opt.classList.remove("selected"));
      this.classList.add("selected");

      // Marca o input radio correspondente
      if (index === 0) tipoCartaoInput.checked = true;
      else tipoCorrenteInput.checked = true;

      atualizarVisibilidadeDiaFechamento();
      validar();
    });
  });
}

// Função que atualiza a visibilidade do campo "Dia do fechamento"
function atualizarVisibilidadeDiaFechamento() {
  const tipoCartaoInput = document.getElementById("tipoCartao");
  const diaFechamentoWrapper = document.getElementById("wrapperDiaFechamento");
  const diaFechamentoInput = document.getElementById("diaFechamento");

  if (!tipoCartaoInput || !diaFechamentoWrapper || !diaFechamentoInput) return;

  if (tipoCartaoInput.checked) {
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
  const btnSalvar = document.getElementById("btnSalvar");

  if (!form || !btnSalvar) return;

  const inputs = form.querySelectorAll("input[required]");
  let valido = true;

  inputs.forEach((input) => {
    if (!input.value.trim()) valido = false;
  });

  btnSalvar.disabled = !valido;
}

// Inicializa validação de inputs obrigatórios ao digitar
function inicializarValidacao() {
  const inputs = document.querySelectorAll("input[required]");
  if (!inputs.length) return;

  inputs.forEach((input) => {
    input.addEventListener("input", validar);
  });
}

// Inicialização geral ao carregar a página
document.addEventListener("DOMContentLoaded", () => {
  inicializarTipoConta();
  inicializarValidacao();
  atualizarVisibilidadeDiaFechamento();
  validar();
});
