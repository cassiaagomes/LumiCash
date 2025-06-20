// Para Conta

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

document.addEventListener("DOMContentLoaded", () => {
  const form = document.querySelector("form");
  const inputs = form.querySelectorAll("input[required]");
  const btnSalvar = document.getElementById("btnSalvar");

  const validar = () => {
    let valido = true;
    inputs.forEach((input) => {
      if (!input.value.trim()) valido = false;
    });
    btnSalvar.disabled = !valido;
  };

  inputs.forEach((input) => {
    input.addEventListener("input", validar);
  });

  validar(); // inicial
});

// Para Correntista

document.addEventListener("DOMContentLoaded", () => {
  const form = document.querySelector("form");
  const inputs = form.querySelectorAll("input[required]");
  const btnSalvar = document.getElementById("btnSalvar");

  const validar = () => {
    let valido = true;

    inputs.forEach((input) => {
      const tipo = input.type;
      const valor = input.value.trim();

      if (!valor) {
        valido = false;
        return;
      }

      if (tipo === "email" && !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(valor)) {
        valido = false;
        return;
      }

      // pode adicionar validação mínima da senha, se quiser
      if (tipo === "password" && valor.length < 4) {
        valido = false;
        return;
      }
    });

    btnSalvar.disabled = !valido;
  };

  inputs.forEach((input) => {
    input.addEventListener("input", validar);
  });

  validar(); // valida no carregamento
});
