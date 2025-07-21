package br.edu.ifpb.pweb2.lumicash.controller;

import java.util.List;

import br.edu.ifpb.pweb2.lumicash.entity.Categoria;
import br.edu.ifpb.pweb2.lumicash.service.CategoriaService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping("")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @Autowired
    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping("/categorias")
    public String cadastrarCategoria(@Valid @ModelAttribute("categoria") Categoria categoria,
            BindingResult result) {

        if (result.hasErrors()) {
            return "categorias/form";
        }

        categoriaService.salvarCategoria(categoria);
        return "redirect:/categorias";
    }

    @GetMapping("/categorias/form")
    public String showForm(Model model) {
        model.addAttribute("categoria", new Categoria());
        model.addAttribute("page", "categorias");
        return "categorias/form";
    }

    @GetMapping("/categorias")
    public String listarCategorias(Model model) {
        List<Categoria> categorias = categoriaService.listarCategorias();
        model.addAttribute("categorias", categorias);
        model.addAttribute("page", "categorias");
        return "categorias/listar";
    }

    @GetMapping("/categorias/edit/{id}")
    public String editarCategoria(@PathVariable("id") Long id, Model model) {
        Categoria categoria = categoriaService.buscarPorId(id);

        if (categoria == null) {
            model.addAttribute("mensagem", "Categoria não encontrada.");
            return "redirect:/categorias";
        }

        model.addAttribute("categoria", categoria);
        model.addAttribute("page", "categorias");
        return "categorias/form";
    }

    @GetMapping("/categorias/desativar/{id}")
    public String desativarCategoria(@PathVariable("id") Long id) {
        Categoria categoria = categoriaService.buscarPorId(id);
        if (categoria != null) {
            categoria.setAtivo(false);
            categoriaService.salvarCategoria(categoria); // mesmo método de salvar serve
        }
        return "redirect:/categorias";
    }

}
