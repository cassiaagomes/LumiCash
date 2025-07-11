package br.edu.ifpb.pweb2.lumicash.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.pweb2.lumicash.entity.Transacao;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    // Buscar transações por conta específica
    List<Transacao> findByContaId(Long contaId);
    
    // Buscar transações por conta específica ordenadas por data (mais recentes primeiro)
    List<Transacao> findByContaIdOrderByDataDesc(Long contaId);
    
    // Buscar transações por correntista (através do relacionamento com conta)
    @Query("SELECT t FROM Transacao t WHERE t.conta.correntista.id = :correntistaId")
    List<Transacao> findByContaCorrentistaId(@Param("correntistaId") Long correntistaId);
    
    // Buscar transações por correntista ordenadas por data
    @Query("SELECT t FROM Transacao t WHERE t.conta.correntista.id = :correntistaId ORDER BY t.data DESC")
    List<Transacao> findByContaCorrentistaIdOrderByDataDesc(@Param("correntistaId") Long correntistaId);
}