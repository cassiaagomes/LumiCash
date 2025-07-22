package br.edu.ifpb.pweb2.lumicash.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.pweb2.lumicash.entity.Correntista;
import br.edu.ifpb.pweb2.lumicash.repository.CorrentistaRepository;
import br.edu.ifpb.pweb2.lumicash.service.CorrentistaService;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private CorrentistaRepository correntistaRepository;

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
        Optional<Correntista> correntistaAtualizado = correntistaRepository.findById(correntista.getId());
        session.setAttribute("loggedCorrentista", correntistaAtualizado.get());

        if (correntista == null) {
            return new ModelAndView("redirect:/auth/signin");
        }

        ModelAndView mav = new ModelAndView("pages/home");
        mav.addObject("correntista", correntistaAtualizado.get());
        return mav;
    }

}