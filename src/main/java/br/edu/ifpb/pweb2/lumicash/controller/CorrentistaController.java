
package br.edu.ifpb.pweb2.lumicash.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.pweb2.lumicash.service.CorrentistaService;
import br.edu.ifpb.pweb2.lumicash.entity.Correntista;

@Controller
@RequestMapping("/correntistas")
public class CorrentistaController {

    @Autowired
    private CorrentistaService correntistaService;

    @GetMapping("/form")
    public ModelAndView getForm(ModelAndView mav) { // 1. Remova o Correntista dos parâmetros
        // 2. Crie explicitamente um novo objeto Correntista
        mav.addObject("correntista", new Correntista());
        mav.addObject("page", "correntistas");
        mav.setViewName("correntistas/form");
        return mav;
    }

    @PostMapping
    public ModelAndView save(Correntista correntista, ModelAndView model, RedirectAttributes attr) {
        if (correntista.getId() == null) {
            // Novo correntista
            correntistaService.salvar(correntista);
            attr.addFlashAttribute("mensagem", "Correntista criado com sucesso!");
        } else {
            // Edição
            correntistaService.salvar(correntista);
            attr.addFlashAttribute("mensagem", "Correntista atualizado com sucesso!");
        }
        model.setViewName("redirect:/correntistas");
        return model;
    }

    @GetMapping
    public ModelAndView listAll(ModelAndView model) {
        model.addObject("correntistas", correntistaService.listarCorrentistas());
        model.addObject("page", "correntistas"); // ADICIONADO
        model.setViewName("correntistas/listar");
        return model;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editar(@PathVariable Long id, ModelAndView mav) {
        Correntista correntista = correntistaService.encontrarPorId(id);
        mav.addObject("correntista", correntista);
        mav.addObject("page", "correntistas");
        mav.setViewName("correntistas/form");
        return mav;
    }

    // Troque @GetMapping por @PostMapping

    @PostMapping("/delete/{id}")
    public String deletar(@PathVariable Long id, RedirectAttributes attr) {
        System.out.println("[CONTROLLER] Requisição recebida para deletar o ID: " + id);
        try {
            correntistaService.apagarPorId(id);
            System.out.println("[CONTROLLER] SUCESSO no TRY: O serviço executou sem lançar exceção.");
            attr.addFlashAttribute("mensagem", "Correntista removido com sucesso!");
        } catch (Exception e) {
            System.out.println("[CONTROLLER] ERRO no CATCH: O serviço lançou uma exceção.");
            System.out.println("[CONTROLLER] Mensagem da Exceção: " + e.getMessage());
            attr.addFlashAttribute("erro", "Erro ao remover: " + e.getMessage());
        }
        System.out.println("[CONTROLLER] Redirecionando para a página de listagem.");
        return "redirect:/correntistas";
    }

    @PostMapping("/bloquear/{id}") // <-- Altere para @PostMapping
    public String bloquear(@PathVariable Long id, RedirectAttributes attr) {
        correntistaService.bloquear(id);
        attr.addFlashAttribute("mensagem", "Correntista bloqueado com sucesso!");
        return "redirect:/correntistas";
    }

    @PostMapping("/desbloquear/{id}") // <-- Altere também o de desbloquear
    public String desbloquear(@PathVariable Long id, RedirectAttributes attr) {
        correntistaService.desbloquear(id);
        attr.addFlashAttribute("mensagem", "Correntista desbloqueado com sucesso!");
        return "redirect:/correntistas";
    }

}
