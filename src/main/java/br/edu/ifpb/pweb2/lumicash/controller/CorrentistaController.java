package br.edu.ifpb.pweb2.lumicash.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.pweb2.lumicash.service.CorrentistaService;
import br.edu.ifpb.pweb2.lumicash.entity.Correntista;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/correntistas")
public class CorrentistaController {

    @Autowired
    private CorrentistaService correntistaService;

    @GetMapping("/form")
    public ModelAndView getForm(ModelAndView mav) {
        // ✅ CORREÇÃO: Criar objeto completamente novo e limpo
        Correntista correntista = new Correntista();
        correntista.setIsAdmin(false);
        correntista.setAtivo(true);

        mav.addObject("correntista", correntista);
        mav.addObject("page", "correntistas");
        mav.setViewName("correntistas/form");
        return mav;
    }

    @PostMapping
    public ModelAndView save(@Valid Correntista correntista, BindingResult result,
            ModelAndView model, RedirectAttributes attr) {

        if (result.hasErrors()) {
            model.addObject("correntista", correntista);
            model.addObject("page", "correntistas");
            model.setViewName("correntistas/form");
            return model;
        }

        try {
            // ✅ CORREÇÃO: Log detalhado para debug
            System.out.println("[CONTROLLER] Salvando correntista - ID: " + correntista.getId());
            System.out.println("[CONTROLLER] Nome: " + correntista.getNome());
            System.out.println("[CONTROLLER] Email: " + correntista.getEmail());
            System.out.println("[CONTROLLER] Contas size: " +
                    (correntista.getContas() != null ? correntista.getContas().size() : "null"));

            if (correntista.getId() == null) {
                // ✅ CORREÇÃO: Garantir que é um objeto completamente novo
                if (correntista.getIsAdmin() == null) {
                    correntista.setIsAdmin(false);
                }
                if (correntista.getAtivo() == null) {
                    correntista.setAtivo(true);
                }

                correntistaService.salvar(correntista);
                attr.addFlashAttribute("mensagem", "Correntista criado com sucesso!");
            } else {
                correntistaService.salvar(correntista);
                attr.addFlashAttribute("mensagem", "Correntista atualizado com sucesso!");
            }

            System.out.println("[CONTROLLER] Correntista salvo com sucesso!");

        } catch (Exception e) {
            System.err.println("[CONTROLLER] Erro ao salvar correntista: " + e.getMessage());
            e.printStackTrace();

            // ✅ CORREÇÃO: Criar novo objeto para evitar problemas de estado
            Correntista novoCorrentista = new Correntista();
            novoCorrentista.setNome(correntista.getNome());
            novoCorrentista.setEmail(correntista.getEmail());
            novoCorrentista.setSenha(correntista.getSenha());
            novoCorrentista.setIsAdmin(correntista.getIsAdmin());
            novoCorrentista.setAtivo(correntista.getAtivo());

            model.addObject("correntista", novoCorrentista);
            model.addObject("page", "correntistas");
            model.addObject("erro", "Erro ao salvar: " + e.getMessage());
            model.setViewName("correntistas/form");
            return model;
        }

        model.setViewName("redirect:/correntistas");
        return model;
    }

    @GetMapping
    public ModelAndView listAll(
            @RequestParam(defaultValue = "0") int page, // página atual (0-based)
            @RequestParam(defaultValue = "5") int size, // quantidade de itens por página
            ModelAndView model) {

        Page<Correntista> correntistasPage = correntistaService.listarCorrentistasPaginados(
                page, size, "nome", "asc"); // ordenar por nome ascendente

        model.addObject("correntistas", correntistasPage.getContent());
        model.addObject("currentPage", page);
        model.addObject("totalPages", correntistasPage.getTotalPages());
        model.addObject("pageSize", size);
        model.addObject("page", "correntistas");
        model.setViewName("correntistas/listar");
        return model;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editar(@PathVariable Long id, ModelAndView mav) {
        try {
            Correntista correntista = correntistaService.encontrarPorIdComContas(id);
            mav.addObject("correntista", correntista);
            mav.addObject("page", "correntistas");
            mav.setViewName("correntistas/form");
            return mav;
        } catch (Exception e) {
            mav.addObject("erro", "Correntista não encontrado");
            mav.setViewName("redirect:/correntistas");
            return mav;
        }
    }

    @PostMapping("/delete/{id}")
    public String deletar(@PathVariable Long id, RedirectAttributes attr) {
        System.out.println("[CONTROLLER] Requisição recebida para deletar o ID: " + id);
        try {
            correntistaService.apagarPorId(id);
            System.out.println("[CONTROLLER] SUCESSO: Correntista deletado.");
            attr.addFlashAttribute("mensagem", "Correntista removido com sucesso!");
        } catch (Exception e) {
            System.err.println("[CONTROLLER] ERRO ao deletar: " + e.getMessage());
            e.printStackTrace();
            attr.addFlashAttribute("erro", "Erro ao remover: " + e.getMessage());
        }
        return "redirect:/correntistas";
    }

    @PostMapping("/bloquear/{id}")
    public String bloquear(@PathVariable Long id, RedirectAttributes attr) {
        try {
            correntistaService.bloquear(id);
            attr.addFlashAttribute("mensagem", "Correntista bloqueado com sucesso!");
        } catch (Exception e) {
            attr.addFlashAttribute("erro", "Erro ao bloquear: " + e.getMessage());
        }
        return "redirect:/correntistas";
    }

    @PostMapping("/desbloquear/{id}")
    public String desbloquear(@PathVariable Long id, RedirectAttributes attr) {
        try {
            correntistaService.desbloquear(id);
            attr.addFlashAttribute("mensagem", "Correntista desbloqueado com sucesso!");
        } catch (Exception e) {
            attr.addFlashAttribute("erro", "Erro ao desbloquear: " + e.getMessage());
        }
        return "redirect:/correntistas";
    }

}