document.addEventListener("DOMContentLoaded", () => {
    const sidenavContainer = document.getElementById("sidenav-container");

    fetch("../../resources/templates/fragments/sidenav.html")
        .then((response) => response.text())
        .then((html) => {
            sidenavContainer.innerHTML = html;

            // Inicializa a lógica do sidenav APÓS o HTML ser carregado
            const navLinks = sidenavContainer.querySelectorAll('.nav-link');

            navLinks.forEach(link => {
                link.addEventListener('click', function(event) {
                    navLinks.forEach(lnk => lnk.classList.remove('active'));
                    this.classList.add('active');
                });

                // Define o Dashboard como ativo inicialmente (se existir)
                if (link.textContent === 'Dashboard' && !document.querySelector('.sidenav .nav-link.active')) {
                    link.classList.add('active');
                }
            });
        })
        .catch((err) => console.error("Erro ao carregar o sidenav:", err));
});