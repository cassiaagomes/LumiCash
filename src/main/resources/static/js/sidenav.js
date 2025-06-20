document.addEventListener("DOMContentLoaded", () => {
  const navLinks = document.querySelectorAll(".sidenav .nav-item .nav-link");
  const currentPath = window.location.pathname;

  navLinks.forEach((link) => {
    const href = link.getAttribute("href");
    
    // Remove todas as classes primeiro
    link.classList.remove("active", "inactive");
    
    // Se o link já tem 'active' definido pelo Thymeleaf
    if (link.classList.contains("active")) {
      return; // Já está ativo, não precisa fazer nada
    }
    
    // Verifica se é o link atual
    if (href && currentPath === href) {
      link.classList.add("active");
    } else {
      // Se não é o link atual, deixa com aparência padrão (cinza)
      // Não precisa adicionar classe, pois o CSS já define a cor padrão
    }
  });
});