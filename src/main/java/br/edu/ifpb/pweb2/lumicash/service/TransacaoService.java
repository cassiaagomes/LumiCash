package br.edu.ifpb.pweb2.lumicash.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.pweb2.lumicash.dtos.OrcamentoCategoriaDTO;
import br.edu.ifpb.pweb2.lumicash.entity.Categoria;
import br.edu.ifpb.pweb2.lumicash.entity.Comentario;
import br.edu.ifpb.pweb2.lumicash.entity.Conta;
import br.edu.ifpb.pweb2.lumicash.entity.Correntista;
import br.edu.ifpb.pweb2.lumicash.entity.Transacao;
import br.edu.ifpb.pweb2.lumicash.repository.ComentarioRepository;
import br.edu.ifpb.pweb2.lumicash.repository.TransacaoRepository;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private ComentarioRepository comentarioRepository;
    
    
    public Transacao buscarPorId(Long id) {
        Optional<Transacao> opt = transacaoRepository.findById(id);
        return opt.orElse(null);
    }

    public void salvar(Transacao transacao) {
        transacaoRepository.save(transacao);
    }

    public List<Transacao> buscarTodas() {
        return transacaoRepository.findAll();
    }

    public List<Transacao> buscarPorConta(Long contaId) {
        return transacaoRepository.findByContaId(contaId);
    }

    public List<Transacao> buscarPorCorrentista(Correntista correntista) {
        return transacaoRepository.findByContaCorrentistaId(correntista.getId());
    }
    
    public List<Transacao> buscarPorContaOrderByData(Long contaId) {
        return transacaoRepository.findByContaIdOrderByDataDesc(contaId);
    }
    
    public void excluir(Long id) {
        transacaoRepository.deleteById(id);
    }

    public void apagarComentario(Transacao transacao) {
        Comentario comentario = transacao.getComentario();
        if (comentario != null) {
            transacao.setComentario(null);
            transacaoRepository.save(transacao);
            comentarioRepository.delete(comentario);
        }
    }

    public List<Transacao> filtrarTransacoes(Conta conta, LocalDate dataInicio, LocalDate dataFim) {
        return transacaoRepository.filtraTransacaoPorContaDataIncialDataFinal(conta, dataInicio, dataFim);
    }

    public List<Transacao> filtrarTransacoes(Long contaId, LocalDate dataInicio, LocalDate dataFim) {
        return transacaoRepository.filtraTransacaoPorContaIdDataInicialDataFinal(contaId, dataInicio, dataFim);
    }

    public List<OrcamentoCategoriaDTO> gerarOrcamentoPorCategoria(List<Categoria> categorias, int ano, Correntista correntista) {
        List<OrcamentoCategoriaDTO> lista = new ArrayList<>();

        for (Categoria cat : categorias) {
            OrcamentoCategoriaDTO dto = new OrcamentoCategoriaDTO();
            dto.setNome(cat.getNome());

            List<BigDecimal> valoresMensais = new ArrayList<>();
            BigDecimal total = BigDecimal.ZERO;

            for (int mes = 1; mes <= 12; mes++) {
                BigDecimal soma = transacaoRepository.somarPorCategoriaEMesECorrentista(cat.getId(), ano, mes, correntista.getId());
                if (soma == null)
                    soma = BigDecimal.ZERO;
                valoresMensais.add(soma);
                total = total.add(soma);
            }

            dto.setValoresMensais(valoresMensais);
            dto.setTotal(total);
            lista.add(dto);
        }
        return lista;
    }
    public List<Integer> obterAnosComTransacoes(Correntista correntista) {
        return transacaoRepository.findDistinctAnosByCorrentista(correntista.getId());
    }
}