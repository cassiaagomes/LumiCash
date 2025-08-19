package br.edu.ifpb.pweb2.lumicash.controller;

import br.edu.ifpb.pweb2.lumicash.entity.Conta;
import br.edu.ifpb.pweb2.lumicash.entity.Correntista;
import br.edu.ifpb.pweb2.lumicash.entity.Transacao;
import br.edu.ifpb.pweb2.lumicash.service.ContaService;
import br.edu.ifpb.pweb2.lumicash.service.TransacaoService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/extrato")
public class ExtratoController {

    private final ContaService contaService;
    private final TransacaoService transacaoService;

    @Autowired
    public ExtratoController(ContaService contaService, TransacaoService transacaoService) {
        this.contaService = contaService;
        this.transacaoService = transacaoService;
    }

    @GetMapping()
    public String listarContas(Model model, HttpSession session) {
        Correntista correntista = (Correntista) session.getAttribute("loggedCorrentista");

        List<Conta> contas = contaService.listarContasDoCorrentista(correntista);
        model.addAttribute("contasCorrentista", contas);
        model.addAttribute("contaSelecionadaId", null);
        model.addAttribute("page", "extrato");
        return "extrato/listar";
    }

    @PostMapping()
    public String showForm(Model model,
            @Valid @ModelAttribute("contaId") String contaId,
            @ModelAttribute("dataInicio") String dataInicio,
            @ModelAttribute("dataFim") String dataFim,
            RedirectAttributes redirectAttributes) {
        if (Objects.isNull(contaId) || contaId.isBlank()) {
            return "redirect:/extrato";
        }

        String parametros = contaId.concat("?");
        if (Objects.nonNull(dataInicio) && !dataInicio.isBlank()) {
            parametros += "dataInicio=" + dataInicio + "&";
        }
        if (Objects.nonNull(dataFim) && !dataFim.isBlank()) {
            parametros += "dataFim=" + dataFim;
        }
        model.addAttribute("page", "extrato");

        return "redirect:/extrato/contas/" + parametros;
    }

    @GetMapping("/contas/{id}")
    public String listarTransacoes(Model model,
            HttpSession session,
            // BindingResult result,
            @PathVariable @Valid Long id,
            @RequestParam(required = false) @Valid LocalDate dataInicio,
            @RequestParam(required = false) @Valid LocalDate dataFim) {

        // if (result.hasErrors()) {
        // model.addAttribute("mensagem", "Formato de dado inválido, revise as
        // informações");
        // return "extrato/listar";
        // }

        Correntista correntista = (Correntista) session.getAttribute("loggedCorrentista");
        List<Conta> contas = contaService.listarContasDoCorrentista(correntista);
        model.addAttribute("contasCorrentista", contas);

        if (Objects.isNull(dataInicio)) {
            dataInicio = LocalDate.now().withDayOfMonth(1);
        }
        if (Objects.isNull(dataFim)) {
            dataFim = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        }
        if (dataInicio.isAfter(dataFim)) {
            model.addAttribute("mensagem", "Data de início tem que ser anterior a data de fim");
        } else {
            List<Transacao> transacoes = transacaoService.filtrarTransacoes(id, dataInicio, dataFim);
            model.addAttribute("transacoes", transacoes);
        }

        model.addAttribute("dataInicio", dataInicio);
        model.addAttribute("dataFim", dataFim);
        model.addAttribute("contaSelecionadaId", id);
        model.addAttribute("page", "extrato");

        return "extrato/listar";
    }
}