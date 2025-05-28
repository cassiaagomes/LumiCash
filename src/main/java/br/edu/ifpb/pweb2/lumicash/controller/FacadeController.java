package br.edu.ifpb.pweb2.lumicash.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import br.edu.ifpb.pweb2.lumicash.service.ContaService;
import br.edu.ifpb.pweb2.lumicash.service.FacadeService;
import br.edu.ifpb.pweb2.lumicash.entity.Conta;
import br.edu.ifpb.pweb2.lumicash.entity.Correntista;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("")
public class FacadeController {

    private final ContaService service;

    private final FacadeService facadeService;

    @Autowired
    public FacadeController(ContaService service, FacadeService facadeService) {
        this.service = service;
        this.facadeService = facadeService;
    }

    @PostMapping("/criarConta")
    public String cadastrarConta(@Valid @ModelAttribute("conta") Conta conta,
                             BindingResult result,
                             HttpSession session) {

    if (result.hasErrors()) {
        return "contas/form"; 
    }

    Correntista correntista = (Correntista) session.getAttribute("usuarioLogado");
    if (correntista == null) {
        return "redirect:/login";
    }

    try {
        facadeService.CriarConta(conta, correntista);
    } catch (IllegalArgumentException e) {
        result.rejectValue("diaFechamento", null, e.getMessage());
        return "contas/form"; 
    }

    return "redirect:/contas"; 
}

    @GetMapping("/contas/form")
    public String showForm(Model model) {
        model.addAttribute("conta", new Conta());
        return "contas/form";
    }
}
