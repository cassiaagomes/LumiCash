package br.edu.ifpb.pweb2.lumicash.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.ifpb.pweb2.lumicash.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    // Administrador pode usar esse
    List<Categoria> findByNaturezaOrderByOrdem(String natureza);

    // Correntista só vê as categorias ativas
    List<Categoria> findByAtivoTrueOrderByOrdem();
}
