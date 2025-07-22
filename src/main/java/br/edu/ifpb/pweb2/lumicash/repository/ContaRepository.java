package br.edu.ifpb.pweb2.lumicash.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.pweb2.lumicash.entity.Conta;
import br.edu.ifpb.pweb2.lumicash.entity.Correntista;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {

    List<Conta> findByCorrentista(Correntista correntista);

}

