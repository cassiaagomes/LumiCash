package br.edu.ifpb.pweb2.lumicash.repository;

import br.edu.ifpb.pweb2.lumicash.entity.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
}
