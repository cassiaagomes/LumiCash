package br.edu.ifpb.pweb2.lumicash.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.ifpb.pweb2.lumicash.entity.Comentario;
import br.edu.ifpb.pweb2.lumicash.entity.Conta;
import br.edu.ifpb.pweb2.lumicash.entity.Correntista;
import br.edu.ifpb.pweb2.lumicash.entity.Transacao;
import br.edu.ifpb.pweb2.lumicash.service.CategoriaService;
import br.edu.ifpb.pweb2.lumicash.service.ContaService;
import br.edu.ifpb.pweb2.lumicash.service.CorrentistaService;
import br.edu.ifpb.pweb2.lumicash.service.TransacaoService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private CorrentistaService correntistaService;

    @Autowired
    private ContaService contaService;

    @GetMapping
    public String listarTransacoes(Model model,
            @RequestParam(required = false) Long contaId,
            HttpSession session) {

        // Pegar o correntista da sessão (consistente com ContaController)
        Correntista correntista = (Correntista) session.getAttribute("loggedCorrentista");

        if (correntista == null) {
            // Se não houver correntista na sessão, redirecionar para login
            return "redirect:/auth/signin";
        }

        // Buscar apenas as contas do correntista logado
        List<Conta> contas = contaService.findByCorrentista(correntista);

        List<Transacao> transacoes = new ArrayList<>();

        if (contaId != null) {
            // Verificar se a conta selecionada pertence ao correntista
            boolean contaPertenceAoCorrentista = contas.stream()
                    .anyMatch(conta -> conta.getId().equals(contaId));

            if (contaPertenceAoCorrentista) {
                // Buscar transações da conta específica ordenadas por data
                transacoes = transacaoService.buscarPorContaOrderByData(contaId);
            } else {
                model.addAttribute("mensagem", "Conta não encontrada ou não pertence ao usuário.");
            }
        } else {
            // Se nenhuma conta foi selecionada, buscar transações de todas as contas do
            // correntista
            transacoes = transacaoService.buscarPorCorrentista(correntista);
        }

        model.addAttribute("contas", contas);
        model.addAttribute("transacoes", transacoes);
        model.addAttribute("contaSelecionadaId", contaId);
        model.addAttribute("page", "transacoes");

        return "transacoes/listar";
    }

    @GetMapping("/form")
    public String mostrarFormularioDeTransacao(@RequestParam(required = false) Long contaId,
            Model model,
            HttpSession session) {
        Correntista correntista = (Correntista) session.getAttribute("loggedCorrentista");

        if (correntista == null) {
            return "redirect:/auth/signin";
        }

        List<Conta> contas = contaService.findByCorrentista(correntista);
        Transacao transacao = new Transacao();

        if (contaId != null) {
            // ⚠️ Verificar se a conta pertence ao usuário
            Conta contaSelecionada = contaService.findById(contaId);
            boolean pertence = contas.stream().anyMatch(c -> c.getId().equals(contaId));
            if (contaSelecionada != null && pertence) {
                transacao.setConta(contaSelecionada);
                model.addAttribute("contaId", contaId);
            } else {
                // Redirecionar ou tratar erro
                model.addAttribute("mensagem", "Conta inválida ou não pertence a você.");
                return "redirect:/transacoes";
            }
        }

        model.addAttribute("transacao", transacao);
        model.addAttribute("contas", contas);
        model.addAttribute("categorias", categoriaService.buscarTodas());
        model.addAttribute("contaId", contaId);
        model.addAttribute("page", "transacoes");

        return "transacoes/form";
    }

    @GetMapping("/editar/{id}")
    public String editarTransacao(@PathVariable Long id, Model model, HttpSession session) {

        // Pegar o correntista da sessão
        Correntista correntista = (Correntista) session.getAttribute("loggedCorrentista");

        if (correntista == null) {
            return "redirect:/auth/signin";
        }

        Transacao transacao = transacaoService.buscarPorId(id);

        if (transacao == null) {
            return "redirect:/transacoes";
        }

        // Verificar se a transação pertence ao correntista logado
        if (!transacao.getConta().getCorrentista().getId().equals(correntista.getId())) {
            model.addAttribute("mensagem", "Você não tem permissão para editar esta transação.");
            return "redirect:/transacoes";
        }

        // Buscar contas do correntista para o formulário
        List<Conta> contas = contaService.findByCorrentista(correntista);

        model.addAttribute("transacao", transacao);
        model.addAttribute("categorias", categoriaService.buscarTodas());
        model.addAttribute("contas", contas);

        // Passar o contaId para o template
        if (transacao.getConta() != null) {
            model.addAttribute("contaId", transacao.getConta().getId());
        } else {
            model.addAttribute("contaId", null);
        }

        return "transacoes/form";
    }

    @PostMapping("/salvar")
    public String salvarTransacao(@ModelAttribute Transacao transacao, HttpSession session) {

        // Pegar o correntista da sessão
        Correntista correntista = (Correntista) session.getAttribute("loggedCorrentista");

        if (correntista == null) {
            return "redirect:/auth/signin";
        }

        // Verificar se a conta da transação pertence ao correntista
        if (transacao.getConta() != null) {
            List<Conta> contas = contaService.findByCorrentista(correntista);
            boolean contaPertenceAoCorrentista = contas.stream()
                    .anyMatch(conta -> conta.getId().equals(transacao.getConta().getId()));

            if (!contaPertenceAoCorrentista) {
                // Conta não pertence ao correntista - não salvar
                return "redirect:/transacoes";
            }
        }

        transacaoService.salvar(transacao);
        return "redirect:/transacoes?contaId=" + transacao.getConta().getId();
    }

    @GetMapping("/delete/{id}")
    public String excluirTransacao(@PathVariable Long id, HttpSession session) {

        // Pegar o correntista da sessão
        Correntista correntista = (Correntista) session.getAttribute("loggedCorrentista");

        if (correntista == null) {
            return "redirect:/auth/signin";
        }

        Transacao transacao = transacaoService.buscarPorId(id);

        if (transacao != null) {
            // Verificar se a transação pertence ao correntista logado
            if (transacao.getConta().getCorrentista().getId().equals(correntista.getId())) {
                transacaoService.excluir(id);
            }
        }

        return "redirect:/transacoes";
    }

    @GetMapping("/comentario/{id}")
    public String adicionarComentario(@PathVariable Long id, Model model, HttpSession session) {
        Correntista correntista = (Correntista) session.getAttribute("loggedCorrentista");

        if (correntista == null) {
            return "redirect:/auth/signin";
        }

        Transacao transacao = transacaoService.buscarPorId(id);

        if (transacao == null || !transacao.getConta().getCorrentista().getId().equals(correntista.getId())) {
            return "redirect:/transacoes";
        }

        model.addAttribute("transacaoId", transacao.getId());
        model.addAttribute("contaId", transacao.getConta().getId());

        // Passa o comentário existente, se houver. Se não, passa um novo objeto
        // Comentario
        if (transacao.getComentario() != null) {
            model.addAttribute("comentario", transacao.getComentario());
        } else {
            model.addAttribute("comentario", new Comentario());
        }

        model.addAttribute("page", "transacoes");
        return "comentarios/form";
    }

    @PostMapping("/{id}/comentario")
    public String salvarComentario(
            @PathVariable Long id,
            @ModelAttribute("comentario") Comentario comentarioForm,
            HttpSession session) {

        Correntista correntista = (Correntista) session.getAttribute("loggedCorrentista");
        if (correntista == null) {
            return "redirect:/auth/signin";
        }

        Transacao transacao = transacaoService.buscarPorId(id);
        if (transacao == null || !transacao.getConta().getCorrentista().getId().equals(correntista.getId())) {
            return "redirect:/transacoes";
        }

        Comentario comentario = transacao.getComentario();

        if (comentario == null) {
            comentario = new Comentario();
            comentario.setTransacao(transacao);
        }

        comentario.setTexto(comentarioForm.getTexto());
        transacao.setComentario(comentario);

        transacaoService.salvar(transacao);

        return "redirect:/transacoes?contaId=" + transacao.getConta().getId();
    }

    @GetMapping("/{id}/comentario/delete")
    public String apagarComentario(@PathVariable Long id, HttpSession session) {
        Correntista correntista = (Correntista) session.getAttribute("loggedCorrentista");

        if (correntista == null) {
            return "redirect:/signin";
        }

        Transacao transacao = transacaoService.buscarPorId(id);

        if (transacao == null || !transacao.getConta().getCorrentista().getId().equals(correntista.getId())) {
            return "redirect:/transacoes";
        }

        transacaoService.apagarComentario(transacao);

        return "redirect:/transacoes?contaId=" + transacao.getConta().getId();
    }

}