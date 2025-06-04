document.addEventListener("DOMContentLoaded", () => {
  const navLinks = document.querySelectorAll(".sidenav .nav-link");

  // Pega o path da URL, ex: "/app/pages/correntistas/list.html"
  const currentPath = window.location.pathname;

  navLinks.forEach((link) => {
    const href = link.getAttribute("href");

    // Verifica se o href está contido na URL atual
    if (currentPath.includes(href)) {
      link.classList.add("active");
      link.classList.remove("inactive");
    } else {
      link.classList.remove("active");
      link.classList.add("inactive");
    }
  });
});
