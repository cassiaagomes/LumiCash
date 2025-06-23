package br.edu.ifpb.pweb2.lumicash.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.ifpb.pweb2.lumicash.entity.Transacao;
import br.edu.ifpb.pweb2.lumicash.service.TransacaoService;

@Controller
@RequestMapping("/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    // Exibe o formulário de criação de nova transação
    @GetMapping("/form")
    public String mostrarFormularioDeTransacao(Model model) {
        model.addAttribute("transacao", new Transacao());
        return "transacoes/form";
    }

    // Exibe o formulário de edição de transação existente
    @GetMapping("/editar/{id}")
    public String editarTransacao(@PathVariable Long id, Model model) {
        Transacao transacao = transacaoService.buscarPorId(id);
        if (transacao == null) {
            // Redirecionar ou tratar erro, por exemplo, redirecionar para uma página de
            // erro ou lista
            return "redirect:/transacoes";
        }
        model.addAttribute("transacao", transacao);
        return "transacoes/form";
    }

    // Salva nova ou atualiza transação
    @PostMapping("/salvar")
    public String salvarTransacao(@ModelAttribute Transacao transacao) {
        transacaoService.salvar(transacao);
        return "redirect:/transacoes";
    }
}
