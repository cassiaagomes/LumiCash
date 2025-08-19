package br.edu.ifpb.pweb2.lumicash.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.ifpb.pweb2.lumicash.dtos.OrcamentoCategoriaDTO;
import br.edu.ifpb.pweb2.lumicash.entity.Categoria;
import br.edu.ifpb.pweb2.lumicash.service.CategoriaService;
import br.edu.ifpb.pweb2.lumicash.service.TransacaoService;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/orcamento-anual")
public class OrcamentoAnualController {

    @Autowired
    private TransacaoService transacaoService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public String listarOrcamento(@RequestParam(value = "ano", required = false) Integer ano, Model model) {

        int anoCorrente = ano != null ? ano : LocalDate.now().getYear();
        model.addAttribute("anoSelecionado", anoCorrente);

        // Disponíveis para selecionar
        List<Integer> anosDisponiveis = transacaoService.obterAnosComTransacoes();
        model.addAttribute("anosDisponiveis", anosDisponiveis);

        // Meses do ano
        List<String> meses = Arrays.asList("Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov",
                "Dez");
        model.addAttribute("meses", meses);

        // Obter categorias por natureza
        List<Categoria> entradas = categoriaService.listarPorNatureza("ENTRADA");
        List<Categoria> saidas = categoriaService.listarPorNatureza("SAIDA");
        List<Categoria> investimentos = categoriaService.listarPorNatureza("INVESTIMENTO");

        // Preencher valores mensais e total
        List<OrcamentoCategoriaDTO> orcamentoEntradas = transacaoService.gerarOrcamentoPorCategoria(entradas,
                anoCorrente);
        List<OrcamentoCategoriaDTO> orcamentoSaidas = transacaoService.gerarOrcamentoPorCategoria(saidas, anoCorrente);
        List<OrcamentoCategoriaDTO> orcamentoInvestimentos = transacaoService.gerarOrcamentoPorCategoria(investimentos,
                anoCorrente);

        // ===== TESTE: imprimir no console =====

        entradas.forEach(c -> System.out.println("Entrada: " + c.getId() + " - " + c.getNome()));
        saidas.forEach(c -> System.out.println("Saída: " + c.getId() + " - " + c.getNome()));
        investimentos.forEach(c -> System.out.println("Investimento: " + c.getId() + " - " + c.getNome()));

        // System.out.println("=== ORÇAMENTO ENTRADAS ===");
        // orcamentoEntradas.forEach(dto -> System.out
        //         .println(dto.getNome() + " -> " + dto.getValoresMensais() + " | Total: " + dto.getTotal()));

        // System.out.println("=== ORÇAMENTO SAIDAS ===");
        // orcamentoSaidas.forEach(dto -> System.out
        //         .println(dto.getNome() + " -> " + dto.getValoresMensais() + " | Total: " + dto.getTotal()));

        // System.out.println("=== ORÇAMENTO INVESTIMENTOS ===");
        // orcamentoInvestimentos.forEach(dto -> System.out
        //         .println(dto.getNome() + " -> " + dto.getValoresMensais() + " | Total: " + dto.getTotal()));
        // ======================================

        model.addAttribute("orcamentoEntradas", orcamentoEntradas);
        model.addAttribute("orcamentoSaidas", orcamentoSaidas);
        model.addAttribute("orcamentoInvestimentos", orcamentoInvestimentos);

        return "orcamento-anual/listar";
    }

}
