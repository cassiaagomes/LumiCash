package br.edu.ifpb.pweb2.lumicash.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.edu.ifpb.pweb2.lumicash.entity.Conta;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {

}

