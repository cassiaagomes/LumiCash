package br.edu.ifpb.pweb2.lumicash.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.ifpb.pweb2.lumicash.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
