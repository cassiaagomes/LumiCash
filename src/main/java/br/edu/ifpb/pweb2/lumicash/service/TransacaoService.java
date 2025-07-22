package br.edu.ifpb.pweb2.lumicash.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.pweb2.lumicash.entity.Comentario;
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

    // Buscar transações por conta específica
    public List<Transacao> buscarPorConta(Long contaId) {
        return transacaoRepository.findByContaId(contaId);
    }

    // Buscar transações por correntista (através das contas)
    public List<Transacao> buscarPorCorrentista(Correntista correntista) {
        return transacaoRepository.findByContaCorrentistaId(correntista.getId());
    }

    // Método adicional útil - buscar transações ordenadas por data
    public List<Transacao> buscarPorContaOrderByData(Long contaId) {
        return transacaoRepository.findByContaIdOrderByDataDesc(contaId);
    }

    // Método para excluir transação
    public void excluir(Long id) {
        transacaoRepository.deleteById(id);
    }

    public void apagarComentario(Transacao transacao) {
        Comentario comentario = transacao.getComentario();
        if (comentario != null) {
            transacao.setComentario(null);
            transacaoRepository.save(transacao); // desvincula o comentário
            comentarioRepository.delete(comentario); // remove do banco
        }
    }

}