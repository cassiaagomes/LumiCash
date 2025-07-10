package br.edu.ifpb.pweb2.lumicash.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import br.edu.ifpb.pweb2.lumicash.entity.Transacao;
import br.edu.ifpb.pweb2.lumicash.service.TransacaoService;

import java.time.LocalDate;
import java.util.List;


@Controller
@RequestMapping("/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @GetMapping("/form")
    public String mostrarFormularioDeTransacao(Model model) {
        model.addAttribute("transacao", new Transacao());
        return "transacoes/form";
    }

    @GetMapping
    public String listarTransacoes (@RequestParam (name = "contaId") Long contaId, Model model) {
        List<Transacao> transacoes = transacaoService.findAllTransacao(contaId);
        model.addAttribute("transacoes", transacoes);
        return "transacoes/lista";
    }

    @GetMapping("/editar/{id}")
    public String editarTransacao(@PathVariable Long id, Model model) {
        Transacao transacao = transacaoService.buscarPorId(id);
        if (transacao == null) {
            return "redirect:/transacoes";
        }
        model.addAttribute("transacao", transacao);
        return "transacoes/form";
    }

    @PostMapping("/salvar")
    public String salvarTransacao(@ModelAttribute Transacao transacao, Model model) {
        try {
            transacaoService.salvar(transacao);
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("transacao", transacao);
            return "transacoes/form";
        }
        return "redirect:/transacoes?contaId=" + transacao.getConta().getId();
    }

    @GetMapping
    public String excluirTransacao(@PathVariable Long id) {
        transacaoService.excluir(id);
        return "redirect:/transacoes";
    }
    
    @GetMapping("/filtro/tipo")
    public String filtrarPorTipo(@RequestParam Long contaId, @RequestParam String tipo, Model model) {
        List<Transacao> transacoes = transacaoService.filtrarPorTipo(contaId, tipo);
        model.addAttribute("transacoes", transacoes);
        return "transacoes/lista";
    }

    @GetMapping("/filtro/data")
    public String filtrarPorData(
        @RequestParam Long contaId,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim,
        Model model
    ) {
        List<Transacao> transacoes = transacaoService.buscarPorData(contaId, inicio, fim);
        model.addAttribute("transacoes", transacoes);
        return "transacoes/lista";
    }
}
