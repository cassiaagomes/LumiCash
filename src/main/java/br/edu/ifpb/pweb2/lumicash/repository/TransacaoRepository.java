package br.edu.ifpb.pweb2.lumicash.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.pweb2.lumicash.entity.Correntista;
import br.edu.ifpb.pweb2.lumicash.entity.Transacao;
import br.edu.ifpb.pweb2.lumicash.enums.TipoTransacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    List<Transacao> findByContaId(Long id);

    //List<Transacao> findByContaIdAndCorrentista(Long id, Correntista correntista);

    List<Transacao> findByContaIdAndDataBetween(Long contaId, LocalDate dataInicio, LocalDate dataFim);

    List<Transacao> findByContaIdAndTipo(Long contaId, TipoTransacao tipo);
}
