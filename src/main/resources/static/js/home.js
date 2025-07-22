document.addEventListener("DOMContentLoaded", () => {
  const sidenavContainer = document.getElementById("sidenav-container");

  if (!sidenavContainer) return;

  const navLinks = sidenavContainer.querySelectorAll(".nav-link");
  const currentPage = window.location.pathname.split("/").pop();

  navLinks.forEach((link) => {
    const href = link.getAttribute("href");

    if (
      href === currentPage ||
      (currentPage === "" && href.includes("index"))
    ) {
      link.classList.add("active");
    }
  });
});
