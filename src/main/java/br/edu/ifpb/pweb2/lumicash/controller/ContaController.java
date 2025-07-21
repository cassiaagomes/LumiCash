package br.edu.ifpb.pweb2.lumicash.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/contas/edit/{id}")
    public String editarConta(@PathVariable Long id, Model model, HttpSession session) {
        Correntista correntista = (Correntista) session.getAttribute("loggedCorrentista");

        Conta conta = contaService.findById(id);

        // Verifica se a conta pertence ao correntista logado (recomendado para
        // segurança)
        if (conta == null || !conta.getCorrentista().getId().equals(correntista.getId())) {
            return "redirect:/contas?erro=acesso-nao-autorizado";
        }

        model.addAttribute("conta", conta);
        model.addAttribute("page", "contas");

        return "contas/form"; // Mesmo formulário usado para criar também pode servir para editar
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

    @GetMapping("/contas/delete/{id}")

    public String excluirConta(@PathVariable Long id, HttpSession session, Model model) {
        Correntista correntista = (Correntista) session.getAttribute("loggedCorrentista");

        Conta conta = contaService.findById(id);

        if (conta == null || !conta.getCorrentista().getId().equals(correntista.getId())) {
            model.addAttribute("mensagem", "Conta não encontrada ou acesso negado.");
            return "redirect:/contas";
        }

        contaService.excluirConta(conta);
        return "redirect:/contas";
    }

    @PostMapping("/contas/{id}")
    public String atualizarConta(@PathVariable Long id,
            @Valid @ModelAttribute("conta") Conta conta,
            BindingResult result,
            HttpSession session) {

        Correntista correntista = (Correntista) session.getAttribute("loggedCorrentista");

        if (result.hasErrors()) {
            return "contas/form";
        }

        Conta contaExistente = contaService.findById(id);

        if (contaExistente == null || !contaExistente.getCorrentista().getId().equals(correntista.getId())) {
            return "redirect:/contas?erro=acesso-nao-autorizado";
        }

        try {
            conta.setId(id); // garante que atualize a conta existente
            conta.setCorrentista(correntista);
            contaService.atualizarConta(conta, correntista); // <-- aqui, passe os dois!
        } catch (IllegalArgumentException e) {
            result.rejectValue("diaFechamento", null, e.getMessage());
            return "contas/form";
        }

        return "redirect:/contas";
    }

}
