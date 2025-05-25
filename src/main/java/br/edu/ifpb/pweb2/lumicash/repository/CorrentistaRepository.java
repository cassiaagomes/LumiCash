package br.edu.ifpb.pweb2.lumicash.repository;

import br.edu.ifpb.pweb2.lumicash.entity.Correntista;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface CorrentistaRepository extends JpaRepository<Correntista, Long> {

    //Optional<Correntista> retornarPorEmail(String email);

    //Optional<Correntista> retornarPorNome(String nome);

    //Optional<Correntista> retornarPorId(Long id);

    //List<Correntista> listarPorOrdemAsc();

    //List<Correntista> listarCorrentistas();
}
