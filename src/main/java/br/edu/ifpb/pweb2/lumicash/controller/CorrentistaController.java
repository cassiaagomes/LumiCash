
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
    public ModelAndView getForm(Correntista correntista, ModelAndView mav) {
        mav.addObject("correntista", correntista);
        mav.addObject("page", "correntistas"); // ADICIONADO
        mav.setViewName("correntistas/form");
        return mav;
    }

    @PostMapping
    public ModelAndView save(Correntista correntista, ModelAndView model, RedirectAttributes attr) {
        correntistaService.salvar(correntista);
        attr.addFlashAttribute("mensagem", "Correntista inserido com sucesso!");
        model.setViewName("redirect:correntistas");
        return model;
    }

    @GetMapping
    public ModelAndView listAll(ModelAndView model) {
        model.addObject("correntistas", correntistaService.listarCorrentistas());
        model.addObject("page", "correntistas"); // ADICIONADO
        model.setViewName("correntistas/listar");
        return model;
    }

}
