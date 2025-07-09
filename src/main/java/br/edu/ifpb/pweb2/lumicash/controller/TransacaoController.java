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

import br.edu.ifpb.pweb2.lumicash.entity.Conta;
import br.edu.ifpb.pweb2.lumicash.entity.Correntista;
import br.edu.ifpb.pweb2.lumicash.entity.Transacao;
import br.edu.ifpb.pweb2.lumicash.service.CategoriaService;
import br.edu.ifpb.pweb2.lumicash.service.ContaService;
import br.edu.ifpb.pweb2.lumicash.service.CorrentistaService;
import br.edu.ifpb.pweb2.lumicash.service.TransacaoService;

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
    public String listarTransacoes(Model model) {
        model.addAttribute("transacoes", transacaoService.buscarTodas());
        model.addAttribute("page", "transacoes");
        return "transacoes/listar"; // certifique-se de ter o template em templates/transacoes/list.html
    }

    @GetMapping("/form")
    public String mostrarFormularioDeTransacao(Model model, Principal principal) throws Exception {

        Correntista correntista = correntistaService.encontrarporEmail("paulo@gmail.com");

        if (principal != null) {
            String email = principal.getName();
            try {
                correntista = correntistaService.encontrarporEmail(email);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Caso não tenha usuário autenticado, você pode criar um correntista "genérico"
        // Ou simplesmente seguir sem contas associadas para não quebrar a página
        List<Conta> contas = new ArrayList<>();
        if (correntista != null) {
            contas = contaService.findByCorrentista(correntista);

        }
        for (Conta c : contas) {
            System.out.println("Conta id=" + c.getId() + ", número=" + c.getNumero());
        }

        Transacao transacao = new Transacao();
        model.addAttribute("transacao", transacao);
        model.addAttribute("categorias", categoriaService.buscarTodas());
        model.addAttribute("contas", contas);

        // Você pode passar também o ID da conta selecionada, se quiser
        if (!contas.isEmpty()) {
            model.addAttribute("contaId", contas.get(0).getId());
        } else {
            model.addAttribute("contaId", null);
        }

        return "transacoes/form";
    }

    @GetMapping("/editar/{id}")
    public String editarTransacao(@PathVariable Long id, Model model) {
        Transacao transacao = transacaoService.buscarPorId(id);

        if (transacao == null) {
            return "redirect:/transacoes";
        }

        System.out.println("Movimento da transação: " + transacao.getMovimento());
        model.addAttribute("transacao", transacao);
        model.addAttribute("categorias", categoriaService.buscarTodas());

        // Passar o contaId para o template:
        if (transacao.getConta() != null) {
            model.addAttribute("contaId", transacao.getConta().getId());
        } else {
            model.addAttribute("contaId", null); // ou trate caso não tenha conta
        }

        return "transacoes/form";
    }

    @PostMapping("/salvar")
    public String salvarTransacao(@ModelAttribute Transacao transacao) {
        transacaoService.salvar(transacao);
        return "redirect:/transacoes";
    }
}
