package br.edu.ifpb.pweb2.lumicash.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import br.edu.ifpb.pweb2.lumicash.entity.Correntista;
import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("correntista")
    public Correntista getLoggedCorrentista(HttpSession session) {
        return (Correntista) session.getAttribute("loggedCorrentista");
    }

    @ModelAttribute("isAdmin")
    public boolean isAdmin(HttpSession session) {
        Correntista c = (Correntista) session.getAttribute("loggedCorrentista");
        return c != null && Boolean.TRUE.equals(c.getIsAdmin());
    }
}
