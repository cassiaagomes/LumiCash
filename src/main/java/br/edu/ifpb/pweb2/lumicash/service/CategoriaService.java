package br.edu.ifpb.pweb2.lumicash.service;

import java.util.List;

import br.edu.ifpb.pweb2.lumicash.entity.Categoria;
import br.edu.ifpb.pweb2.lumicash.repository.CategoriaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    @Autowired
    private final CategoriaRepository categoriaRepository;

    public List<Categoria> buscarTodas() {
        return categoriaRepository.findAll();
    }

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    // Método para salvar uma nova categoria
    public void salvarCategoria(Categoria categoria) {
        categoriaRepository.save(categoria);
    }

    // Método para listar todas as categorias
    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    public Categoria buscarPorId(Long id) {
    return categoriaRepository.findById(id).orElse(null);
}

}
