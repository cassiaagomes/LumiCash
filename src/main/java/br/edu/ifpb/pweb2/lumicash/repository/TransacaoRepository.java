package br.edu.ifpb.pweb2.lumicash.repository;

import java.time.LocalDate;
import java.util.List;

import br.edu.ifpb.pweb2.lumicash.entity.Correntista;
import br.edu.ifpb.pweb2.lumicash.entity.Transacao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    List<Transacao> findByContaId(Long id);
    List<Transacao> findByContaIdAndContaCorrentistaId(Long id, Long correntistaId);
    List<Transacao> findByContaIdAndDataBetween(Long id, LocalDate dataInicio, LocalDate dataFim);
    List<Transacao> findByContaIdAndTipo(Long id, String tipo);
}
