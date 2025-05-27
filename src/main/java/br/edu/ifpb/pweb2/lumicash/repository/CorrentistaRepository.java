package br.edu.ifpb.pweb2.lumicash.repository;

import br.edu.ifpb.pweb2.lumicash.entity.Correntista;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface CorrentistaRepository extends JpaRepository<Correntista, Long> {

    Optional<Correntista> findByEmail(String email);

    Optional<Correntista> findByNome(String nome);

    Optional<Correntista> findById(Long id);

    List<Correntista> listByOrdemAsc();

    List<Correntista> listCorrentistas();
}
