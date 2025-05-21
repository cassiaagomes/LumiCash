package br.edu.ifpb.pweb2.lumicash.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.pweb2.lumicash.entity.Correntista;
import br.edu.ifpb.pweb2.lumicash.exception.EmailAlreadyExists;
import br.edu.ifpb.pweb2.lumicash.service.AuthService;
import br.edu.ifpb.pweb2.lumicash.service.CorrentistaService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
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
        mav.addObject("Correntista", new Correntista());
        mav.setViewName("auth/signup");
        return mav;
    }

    @PostMapping("/cadastrar")
    public String registrarUsuario(@Valid @ModelAttribute("photographer") Correntista correntista,
                               BindingResult result,
                               HttpSession session) throws EmailAlreadyExists {
        if (result.hasErrors()) {
            return "auth/signup";
        }

        Correntista salvarCorrentista = authService.register(correntista);
        session.setAttribute("loggedCorrentista", salvarCorrentista);

        return "redirect:/home";
    }

}
