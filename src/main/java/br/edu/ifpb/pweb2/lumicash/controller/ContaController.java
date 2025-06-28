package br.edu.ifpb.pweb2.lumicash.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import br.edu.ifpb.pweb2.lumicash.service.ContaService;
import br.edu.ifpb.pweb2.lumicash.entity.Conta;
import br.edu.ifpb.pweb2.lumicash.entity.Correntista;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("")
public class ContaController {

    private final ContaService contaService;

    @Autowired
    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @PostMapping("/criarConta")
    public String cadastrarConta(@Valid @ModelAttribute("conta") Conta conta,
                                 BindingResult result,
                                 HttpSession session) {

        Correntista correntista = (Correntista) session.getAttribute("loggedCorrentista");

        if (result.hasErrors()) {
            return "contas/form";
        }

        try {
            contaService.CriarConta(conta, correntista);
        } catch (IllegalArgumentException e) {
            result.rejectValue("diaFechamento", null, e.getMessage());
            return "contas/form";
        }

        return "redirect:/contas";
    }

    @GetMapping("/contas/form")
    public String showForm(Model model) {
        model.addAttribute("conta", new Conta());
        model.addAttribute("page", "contas");
        return "contas/form";
    }

    @GetMapping("/contas")
    public String listarContas(Model model, HttpSession session) {
        Correntista correntista = (Correntista) session.getAttribute("loggedCorrentista");

        List<Conta> contas = contaService.listarContasDoCorrentista(correntista);
        model.addAttribute("contas", contas);
        model.addAttribute("page", "contas");
        return "contas/listar";
    }
}
