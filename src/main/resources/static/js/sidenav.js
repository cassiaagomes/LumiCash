document.addEventListener("DOMContentLoaded", function () {
  const navLinks = document.querySelectorAll(".sidenav .nav-link");
  const mainContent = document.querySelector(".content-box");

  function loadContent(page) {
    if (mainContent) {
      mainContent.innerHTML = `<h1>Conteúdo de ${page}</h1><p>Esta é a página de ${page}.</p>`;
    } else {
      console.log(`Carregando conteúdo para: ${page}`);
    }
  }

  navLinks.forEach((link) => {
    link.addEventListener("click", function (event) {
      event.preventDefault();

      navLinks.forEach((lnk) => {
        lnk.classList.remove("active");
        lnk.classList.add("inactive");
      });

      this.classList.add("active");
      this.classList.remove("inactive");

      const page = this.textContent.trim();
      loadContent(page);
    });

    if (link.textContent.trim() === "Dashboard") {
      link.classList.add("active");
    } else {
      link.classList.add("inactive");
    }
  });

  loadContent("Dashboard");
});
