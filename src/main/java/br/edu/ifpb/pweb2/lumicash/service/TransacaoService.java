package br.edu.ifpb.pweb2.lumicash.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.pweb2.lumicash.entity.Comentario;
import br.edu.ifpb.pweb2.lumicash.entity.Conta;
import br.edu.ifpb.pweb2.lumicash.entity.Correntista;
import br.edu.ifpb.pweb2.lumicash.entity.Transacao;
import br.edu.ifpb.pweb2.lumicash.enums.TipoTransacao;
import br.edu.ifpb.pweb2.lumicash.repository.ContaRepository;
import br.edu.ifpb.pweb2.lumicash.repository.TransacaoRepository;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private ContaRepository contaRepository;

    public Transacao buscarPorId(Long id) {
        return transacaoRepository.findById(id).orElse(null);
    }

    public List<Transacao> findAllTransacao(Long contaId) {
        return transacaoRepository.findByContaId(contaId);
    }

    public List<Transacao> filtrarPorTipo(Long contaId, String tipo) {
        TipoTransacao tipoEnum = TipoTransacao.valueOf(tipo.toUpperCase()); 
        return transacaoRepository.findByContaIdAndTipo(contaId, tipoEnum);
}

    public List<Transacao> buscarPorData(Long contaId, LocalDate inicio, LocalDate fim) {
        return transacaoRepository.findByContaIdAndDataBetween(contaId, inicio, fim);
    }

    //public List<Transacao> buscarPorContaECorrentista(Long contaId, Correntista correntista) {
    //   return transacaoRepository.findByContaIdAndCorrentista(contaId, correntista);
    //}

    public Transacao salvar(Transacao transacao) {
        Conta conta = transacao.getConta();

        if (conta == null || conta.getId() == null || !contaRepository.existsById(conta.getId())) {
            throw new IllegalArgumentException("Conta associada à transação é inválida ou inexistente.");
        }

        if (transacao.getValor() == null || transacao.getValor() <= 0) {
            throw new IllegalArgumentException("Valor da transação deve ser maior que zero.");
        }

        if (transacao.getData() == null) {
            throw new IllegalArgumentException("Data da transação é obrigatória.");
        }

        if (transacao.getTipo() == null) {
            throw new IllegalArgumentException("Tipo de transação é obrigatório.");
        }

        if (transacao.getCategoria() == null) {
            throw new IllegalArgumentException("Categoria da transação é obrigatória.");
        }

        if (transacao.getComentario() != null) {
            transacao.getComentario().setTransacao(transacao);
        }

        return transacaoRepository.save(transacao);
    }



    public Transacao atualizar(Long id, Transacao novaTransacao) {
        Transacao existente = buscarPorId(id);
        if (existente == null) {
            throw new IllegalArgumentException("Transação não encontrada para atualização.");
        }

        novaTransacao.setId(id);
        if (novaTransacao.getComentario() != null) {
            novaTransacao.getComentario().setTransacao(novaTransacao); 
        }

        return salvar(novaTransacao);
    }

    public Transacao adicionarComentario(Long transacaoId, Comentario comentario) {
        Transacao transacao = buscarPorId(transacaoId);
        if (transacao == null) {
            throw new IllegalArgumentException("Transação não encontrada.");
        }

        comentario.setTransacao(transacao);
        transacao.setComentario(comentario);

        return transacaoRepository.save(transacao); 
    }


    public Transacao editarComentario(Long transacaoId, String novoTexto) {
        Transacao transacao = buscarPorId(transacaoId);
        if (transacao == null || transacao.getComentario() == null) {
            throw new IllegalArgumentException("Comentário não encontrado para esta transação.");
        }

        transacao.getComentario().setTexto(novoTexto);
        return transacaoRepository.save(transacao);
    }

 
    public Transacao removerComentario(Long transacaoId) {
        Transacao transacao = buscarPorId(transacaoId);
        if (transacao == null || transacao.getComentario() == null) {
            throw new IllegalArgumentException("Comentário não encontrado para esta transação.");
        }

        transacao.setComentario(null); 
        return transacaoRepository.save(transacao);
    }

    public void excluir(Long id) {
        Transacao transacao = buscarPorId(id);
        if (transacao == null) {
            throw new IllegalArgumentException("Transação não encontrada para exclusão.");
        }

        transacaoRepository.deleteById(id);
    }
}