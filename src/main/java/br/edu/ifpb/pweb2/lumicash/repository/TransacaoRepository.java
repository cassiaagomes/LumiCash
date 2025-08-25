package br.edu.ifpb.pweb2.lumicash.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.pweb2.lumicash.entity.Conta;
import br.edu.ifpb.pweb2.lumicash.entity.Transacao;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    List<Transacao> findByContaId(Long contaId);
    List<Transacao> findByContaIdOrderByDataDesc(Long contaId);

    @Query("SELECT t FROM Transacao t WHERE t.conta.correntista.id = :correntistaId")
    List<Transacao> findByContaCorrentistaId(@Param("correntistaId") Long correntistaId);

    @Query("SELECT t FROM Transacao t WHERE t.conta.correntista.id = :correntistaId ORDER BY t.data DESC")
    List<Transacao> findByContaCorrentistaIdOrderByDataDesc(@Param("correntistaId") Long correntistaId);
    @Query("SELECT t FROM Transacao t WHERE t.conta = :conta AND t.data BETWEEN :dataInicial AND :dataFinal")
    List<Transacao> filtraTransacaoPorContaDataIncialDataFinal(Conta conta,
                                                               LocalDate dataInicial,
                                                               LocalDate dataFinal);

    @Query("SELECT t FROM Transacao t WHERE t.conta.id = :contaId AND t.data BETWEEN :dataInicial AND :dataFinal")
    List<Transacao> filtraTransacaoPorContaIdDataInicialDataFinal(Long contaId,
                                                               LocalDate dataInicial,
                                                               LocalDate dataFinal);

    @Query(value = "SELECT DISTINCT EXTRACT(YEAR FROM t.data) FROM transacao t JOIN conta c ON t.conta_id = c.id WHERE c.correntista_id = :correntistaId ORDER BY EXTRACT(YEAR FROM t.data) DESC", nativeQuery = true)
    List<Integer> findDistinctAnosByCorrentista(@Param("correntistaId") Long correntistaId);

    @Query(value = "SELECT COALESCE(SUM(t.valor),0) FROM transacao t JOIN conta c ON t.conta_id = c.id WHERE t.categoria_id = :categoriaId AND EXTRACT(YEAR FROM t.data) = :ano AND EXTRACT(MONTH FROM t.data) = :mes AND c.correntista_id = :correntistaId", nativeQuery = true)
    BigDecimal somarPorCategoriaEMesECorrentista(@Param("categoriaId") Long categoriaId, @Param("ano") int ano, @Param("mes") int mes, @Param("correntistaId") Long correntistaId);

}