 document.addEventListener('DOMContentLoaded', function () {
    const rowsPerPage = 10;  // quantas linhas mostrar por página
    const table = document.querySelector('table tbody');
    const rows = table.querySelectorAll('tr');
    const totalRows = rows.length;
    const totalPages = Math.ceil(totalRows / rowsPerPage);
    let currentPage = 1;

    function showPage(page) {
      const start = (page - 1) * rowsPerPage;
      const end = start + rowsPerPage;

      rows.forEach((row, index) => {
        row.style.display = (index >= start && index < end) ? '' : 'none';
      });

      document.getElementById('pageInfo').textContent = `Página ${page} de ${totalPages}`;

      document.getElementById('prevPage').disabled = page === 1;
      document.getElementById('nextPage').disabled = page === totalPages;
    }

    document.getElementById('prevPage').addEventListener('click', () => {
      if (currentPage > 1) {
        currentPage--;
        showPage(currentPage);
      }
    });

    document.getElementById('nextPage').addEventListener('click', () => {
      if (currentPage < totalPages) {
        currentPage++;
        showPage(currentPage);
      }
    });

    showPage(currentPage); // mostra a primeira página ao carregar
  });
