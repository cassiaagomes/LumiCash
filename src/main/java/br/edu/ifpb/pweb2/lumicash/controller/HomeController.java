package br.edu.ifpb.pweb2.lumicash.controller;

import br.edu.ifpb.pweb2.lumicash.entity.Correntista;
import br.edu.ifpb.pweb2.lumicash.service.CorrentistaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class HomeController {

    @Autowired
    private CorrentistaService correntistaService;
    @GetMapping("/")
    public String redirecionarParaLogin(Principal principal) {
        if (principal != null) {
            return "redirect:/home";
        }
        return "redirect:/auth/signin";
    }
    @GetMapping("/home")
    public ModelAndView home(Principal principal, HttpSession session) {
        if (principal == null) {
            return new ModelAndView("redirect:/auth/signin");
        }

        try {
            String email = principal.getName();
            Correntista correntista = correntistaService.encontrarporEmail(email);
            session.setAttribute("loggedCorrentista", correntista);

            ModelAndView mav = new ModelAndView("pages/home");
            mav.addObject("correntista", correntista);
            return mav;

        } catch (Exception e) {
            return new ModelAndView("redirect:/auth/signin");
        }
    }
}