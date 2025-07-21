package br.edu.ifpb.pweb2.lumicash.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.pweb2.lumicash.entity.Correntista;
import br.edu.ifpb.pweb2.lumicash.exception.EmailAlreadyExists;
import br.edu.ifpb.pweb2.lumicash.service.AuthService;
import br.edu.ifpb.pweb2.lumicash.service.CorrentistaService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    private final CorrentistaService service;

    public AuthController(CorrentistaService service) {
        this.service = service;
    }

    @GetMapping("/signup")
    public ModelAndView signUp(ModelAndView mav) {
        mav.addObject("correntista", new Correntista()); // "correntista" com letra minúscula para compatibilidade com o
                                                         // form
        mav.setViewName("auth/signup");
        return mav;
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("usuario") Correntista usuario, HttpSession session, Model model) {
        Correntista autenticado = authService.autenticar(usuario.getEmail(), usuario.getSenha());

        if (autenticado != null) {
            session.setAttribute("loggedCorrentista", autenticado);
            return "redirect:/home";
        } else {
            model.addAttribute("mensagem", "Email ou senha inválidos");
            return "auth/signin";
        }
    }

    // @PostMapping("/cadastrar")
    // public String registrarUsuario(@Valid @ModelAttribute("correntista") Correntista correntista,
    //         BindingResult result,
    //         HttpSession session) throws EmailAlreadyExists {
    //     if (result.hasErrors()) {
    //         return "auth/signup";
    //     }

    //     Correntista salvarCorrentista = authService.registrar(correntista);
    //     session.setAttribute("loggedCorrentista", salvarCorrentista);

    //     return "redirect:/home";
    // }

    @GetMapping("/signin")
    public ModelAndView signIn(ModelAndView mav) {
        mav.addObject("usuario", new Correntista()); // cria objeto vazio para o form
        mav.setViewName("auth/signin");
        return mav;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/signin";
    }

}