document.addEventListener("DOMContentLoaded", function () {
  const rowsPerPage = 10; // quantas linhas mostrar por página
  const table = document.querySelector("table tbody");
  const rows = table.querySelectorAll("tr");
  const totalRows = rows.length;
  const totalPages = Math.max(Math.ceil(totalRows / rowsPerPage), 1); // garante que totalPages nunca seja menor que 1
  let currentPage = 1;

  function showPage(page) {
    const start = (page - 1) * rowsPerPage;
    const end = start + rowsPerPage;

    rows.forEach((row, index) => {
      row.style.display = index >= start && index < end ? "" : "none";
    });

    // Exibir página correta, tratar caso não haja linhas
    const displayPage = totalRows === 0 ? 0 : page;
    const displayTotalPages = totalRows === 0 ? 0 : totalPages;

    document.getElementById("pageInfo").textContent =
      totalRows === 0
        ? "Nenhum registro encontrado"
        : `Página ${displayPage} de ${displayTotalPages}`;

    document.getElementById("prevPage").disabled = page <= 1;
    document.getElementById("nextPage").disabled = page >= totalPages;
  }

  document.getElementById("prevPage").addEventListener("click", () => {
    if (currentPage > 1) {
      currentPage--;
      showPage(currentPage);
    }
  });

  document.getElementById("nextPage").addEventListener("click", () => {
    if (currentPage < totalPages) {
      currentPage++;
      showPage(currentPage);
    }
  });

  showPage(currentPage); // mostra a primeira página ao carregar
});

// --- Funções do Correntista ---

// ✅ CORRIGIDO: Agora recebe a URL completa
function bloquearCorrentista(url) {
  if (confirm("Deseja realmente bloquear este correntista?")) {
    // Usa o mesmo método de formulário dinâmico para consistência e robustez
    let form = document.createElement("form");
    form.method = "POST";
    form.action = url; // Usa a URL completa passada pelo HTML

    document.body.appendChild(form);
    form.submit();
  }
}

// ✅ CORRIGIDO: Agora recebe a URL completa
function deletarCorrentista(url) {
  if (confirm("Você tem certeza que deseja apagar este correntista?")) {
    let form = document.createElement("form");
    form.method = "POST";
    form.action = url; // Usa a URL completa passada pelo HTML

    document.body.appendChild(form);
    form.submit();
  }
}
function verificarContaSelecionada() {
  const select = document.getElementById("conta");
  const contaSelecionada = select.value;

  if (!contaSelecionada) {
    alert(
      "Por favor, selecione uma conta antes de cadastrar uma nova transação."
    );
  } else {
    // Redireciona para a URL com contaId como parâmetro
    window.location.href = "/transacoes/form?contaId=" + contaSelecionada;
  }
}
