package br.edu.ifpb.pweb2.lumicash.controller;

import br.edu.ifpb.pweb2.lumicash.dtos.OrcamentoCategoriaDTO;
import br.edu.ifpb.pweb2.lumicash.entity.Categoria;
import br.edu.ifpb.pweb2.lumicash.entity.Correntista;
import br.edu.ifpb.pweb2.lumicash.service.CategoriaService;
import br.edu.ifpb.pweb2.lumicash.service.TransacaoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/orcamento-anual")
public class OrcamentoAnualController {

    @Autowired
    private TransacaoService transacaoService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public String listarOrcamento(@RequestParam(value = "ano", required = false) Integer ano, Model model, HttpSession session) {
        
        System.out.println("\n--- INICIANDO DIAGNÓSTICO DO ORÇAMENTO ANUAL ---");

        Correntista correntista = (Correntista) session.getAttribute("loggedCorrentista");
        if (correntista == null) {
            System.out.println("[DIAGNÓSTICO] Correntista não encontrado na sessão. Redirecionando para login.");
            return "redirect:/auth/signin";
        }
        System.out.println("[DIAGNÓSTICO] Correntista logado: " + correntista.getEmail() + " (ID: " + correntista.getId() + ")");

        int anoCorrente = ano != null ? ano : LocalDate.now().getYear();
        model.addAttribute("anoSelecionado", anoCorrente);
        System.out.println("[DIAGNÓSTICO] Ano selecionado para o orçamento: " + anoCorrente);

        List<Integer> anosDisponiveis = transacaoService.obterAnosComTransacoes(correntista);
        model.addAttribute("anosDisponiveis", anosDisponiveis);
        System.out.println("[DIAGNÓSTICO] Anos com transações encontrados para este usuário: " + anosDisponiveis);

        List<String> meses = Arrays.asList("Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez");
        model.addAttribute("meses", meses);

        List<Categoria> entradas = categoriaService.listarPorNatureza("ENTRADA");
        List<Categoria> saidas = categoriaService.listarPorNatureza("SAIDA");
        List<Categoria> investimentos = categoriaService.listarPorNatureza("INVESTIMENTO");
        System.out.println("[DIAGNÓSTICO] Categorias de ENTRADA encontradas: " + entradas.size());
        System.out.println("[DIAGNÓSTICO] Categorias de SAIDA encontradas: " + saidas.size());
        System.out.println("[DIAGNÓSTICO] Categorias de INVESTIMENTO encontradas: " + investimentos.size());

        List<OrcamentoCategoriaDTO> orcamentoEntradas = transacaoService.gerarOrcamentoPorCategoria(entradas, anoCorrente, correntista);
        List<OrcamentoCategoriaDTO> orcamentoSaidas = transacaoService.gerarOrcamentoPorCategoria(saidas, anoCorrente, correntista);
        List<OrcamentoCategoriaDTO> orcamentoInvestimentos = transacaoService.gerarOrcamentoPorCategoria(investimentos, anoCorrente, correntista);
        System.out.println("[DIAGNÓSTICO] Itens de orçamento (ENTRADAS) gerados: " + orcamentoEntradas.size());
        System.out.println("[DIAGNÓSTICO] Itens de orçamento (SAÍDAS) gerados: " + orcamentoSaidas.size());
        System.out.println("[DIAGNÓSTICO] Itens de orçamento (INVESTIMENTOS) gerados: " + orcamentoInvestimentos.size());
        
        // Vamos inspecionar os valores retornados para SAÍDAS
        for (OrcamentoCategoriaDTO dto : orcamentoSaidas) {
            System.out.println("--> Categoria (SAÍDA): " + dto.getNome() + " | Total Anual: " + dto.getTotal());
        }

        model.addAttribute("orcamentoEntradas", orcamentoEntradas);
        model.addAttribute("orcamentoSaidas", orcamentoSaidas);
        model.addAttribute("orcamentoInvestimentos", orcamentoInvestimentos);
        model.addAttribute("page", "orcamento-anual");

        System.out.println("--- FIM DO DIAGNÓSTICO ---\n");
        return "orcamento-anual/listar";
    }
}