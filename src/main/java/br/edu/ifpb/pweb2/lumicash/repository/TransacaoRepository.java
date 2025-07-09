package br.edu.ifpb.pweb2.lumicash.repository;

import java.util.List;

import br.edu.ifpb.pweb2.lumicash.entity.Correntista;
import br.edu.ifpb.pweb2.lumicash.entity.Transacao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    List<Transacao> findByContaId(Long id);
    List<Transacao> findByContaIdAndCorrentista(Long id, Correntista correntista);
    List<Transacao> findByContaIdAndDataBetween(Long id, String dataInicio, String dataFim);
    List<Transacao> findByContaIdAndTipo(Long id, String tipo);
}
