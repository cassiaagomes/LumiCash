// Scripts específicos do formulário
function selectAccount(accountType) {
  document.querySelectorAll(".account-card").forEach((card) => {
    card.classList.remove("selected");
  });
  event.currentTarget.classList.add("selected");
}

function selectMovement(type, element) {
  document.querySelectorAll(".movement-btn").forEach((btn) => {
    btn.classList.remove("selected");
  });
  element.classList.add("selected");

  if (type === "debito") {
    document.getElementById("movDebito").checked = true;
  } else {
    document.getElementById("movCredito").checked = true;
  }
}

document
  .getElementById("formTransacao")
  .addEventListener("submit", function (e) {
    const debito = document.getElementById("movDebito");
    const credito = document.getElementById("movCredito");

    if (!debito.checked && !credito.checked) {
      e.preventDefault(); // Impede o envio do formulário
      alert("Selecione o tipo de movimento (Débito ou Crédito).");
    }
  });

function selectMovement(tipo, element) {
  const debitoRadio = document.getElementById("movDebito");
  const creditoRadio = document.getElementById("movCredito");

  // Remove classe 'selected' de todos os botões
  document
    .querySelectorAll(".movement-btn")
    .forEach((btn) => btn.classList.remove("selected"));

  // Adiciona a classe 'selected' ao botão clicado
  element.classList.add("selected");

  // Marca o radio correspondente
  if (tipo === "debito") {
    debitoRadio.checked = true;
  } else {
    creditoRadio.checked = true;
  }
}
