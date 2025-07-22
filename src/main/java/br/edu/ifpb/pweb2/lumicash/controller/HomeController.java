package br.edu.ifpb.pweb2.lumicash.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.pweb2.lumicash.entity.Correntista;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @GetMapping("/")
    public String redirecionarParaLogin(HttpSession session) {
        // Se já estiver logado, pode mandar para /home ou /contas
        if (session.getAttribute("loggedCorrentista") != null) {
            return "redirect:/home"; // ou /contas
        }

        return "redirect:/auth/signin";
    }

    @GetMapping("/home")
    public ModelAndView home(HttpSession session) {
        Correntista correntista = (Correntista) session.getAttribute("loggedCorrentista");

        if (correntista == null) {
            return new ModelAndView("redirect:/auth/signin");
        }

        ModelAndView mav = new ModelAndView("pages/home");
        mav.addObject("correntista", correntista);
        return mav;
    }

}