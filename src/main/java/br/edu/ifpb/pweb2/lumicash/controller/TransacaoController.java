package br.edu.ifpb.pweb2.lumicash.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.pweb2.lumicash.entity.Comentario;
import br.edu.ifpb.pweb2.lumicash.entity.Transacao;
import br.edu.ifpb.pweb2.lumicash.service.TransacaoService;

@Controller
@RequestMapping("/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @GetMapping
    public String listarTransacoes(Model model) {
        model.addAttribute("listaTransacoes", transacaoService.findAllTransacao(null)); // ajustar se quiser por conta
        return "transacoes/lista";
    }

    @GetMapping("/form")
    public String mostrarFormularioDeTransacao(Model model) {
        model.addAttribute("transacao", new Transacao());
        return "transacoes/form";
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
    public String salvarTransacao(@ModelAttribute Transacao transacao, RedirectAttributes redirectAttributes) {
        transacaoService.salvar(transacao);
        redirectAttributes.addFlashAttribute("msg", "Transação salva com sucesso.");
        return "redirect:/transacoes"; // PRG
    }

    @GetMapping("/{id}/comentario")
    public String mostrarFormularioComentario(@PathVariable Long id, Model model) {
        Transacao transacao = transacaoService.buscarPorId(id);
        if (transacao == null) {
            return "redirect:/transacoes";
        }

        Comentario comentario = transacao.getComentario() != null ? transacao.getComentario() : new Comentario();
        comentario.setTransacao(transacao);

        model.addAttribute("comentario", comentario);
        return "transacoes/comentario-form"; 
    }

    @PostMapping("/{id}/comentario/salvar")
    public String salvarComentario(@PathVariable Long id, @ModelAttribute Comentario comentario, RedirectAttributes redirectAttributes) {
        comentario.setTransacao(transacaoService.buscarPorId(id));

        if (comentario.getId() == null) {
            transacaoService.adicionarComentario(id, comentario);
            redirectAttributes.addFlashAttribute("msg", "Comentário adicionado com sucesso.");
        } else {
            transacaoService.editarComentario(id, comentario.getTexto());
            redirectAttributes.addFlashAttribute("msg", "Comentário atualizado com sucesso.");
        }

        return "redirect:/transacoes";
    }

    @PostMapping("/{id}/comentario/remover")
    public String removerComentario(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        transacaoService.removerComentario(id);
        redirectAttributes.addFlashAttribute("msg", "Comentário removido.");
        return "redirect:/transacoes";
    }
}

