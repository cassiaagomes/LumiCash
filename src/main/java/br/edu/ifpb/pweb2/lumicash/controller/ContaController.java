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

import jakarta.validation.Valid;

@Controller
@RequestMapping("/contas")
public class ContaController {

    private final ContaService contaService;

    @Autowired
    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @PostMapping
    public String cadastrarConta(@Valid @ModelAttribute("conta") Conta conta,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "contas/form";
        }

        try {
            contaService.CriarConta(conta, null); // passando null se CriarConta ainda exigir um Correntista
        } catch (IllegalArgumentException e) {
            result.rejectValue("diaFechamento", null, e.getMessage());
            return "contas/form";
        }

        return "redirect:/contas";
    }

    @GetMapping("/form")
    public String showForm(Model model) {
        model.addAttribute("conta", new Conta());
        return "contas/form";
    }

    @GetMapping
    public String listarContas(Model model) {
        List<Conta> contas = contaService.findAll();
        model.addAttribute("contas", contas);
        return "contas/listar";
    }
}
