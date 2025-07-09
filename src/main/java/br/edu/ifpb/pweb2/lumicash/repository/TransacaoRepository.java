package br.edu.ifpb.pweb2.lumicash.repository;

import br.edu.ifpb.pweb2.lumicash.entity.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
}
