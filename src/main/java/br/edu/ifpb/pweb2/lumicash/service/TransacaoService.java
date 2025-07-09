package br.edu.ifpb.pweb2.lumicash.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.pweb2.lumicash.entity.Conta;
import br.edu.ifpb.pweb2.lumicash.entity.Correntista;
import br.edu.ifpb.pweb2.lumicash.entity.Transacao;
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
        return transacaoRepository.findByContaIdAndTipo(contaId, tipo);
    }

    public List<Transacao> buscarPorData(Long contaId, LocalDate inicio, LocalDate fim) {
        return transacaoRepository.findByContaIdAndDataBetween(
            contaId,
            inicio != null ? inicio.toString() : null,
            fim != null ? fim.toString() : null
        );
    }

    public List<Transacao> buscarPorContaECorrentista(Long contaId, Correntista correntista) {
        return transacaoRepository.findByContaIdAndCorrentista(contaId, correntista);
    }

    public Transacao salvar(Transacao transacao) {
        Conta conta = transacao.getConta();

        if (conta == null || !contaRepository.existsById(conta.getId())) {
            throw new IllegalArgumentException("Conta associada inválida.");
        }

        if (transacao.getTipo() == null || transacao.getValor() == null || transacao.getData() == null) {
            throw new IllegalArgumentException("Campos obrigatórios não preenchidos.");
        }

        if (transacao.getValor().doubleValue() <= 0) {
            throw new IllegalArgumentException("Valor da transação deve ser positivo.");
        }

        Double saldoAtual = conta.getSaldo();

        if (transacao.getTipo().equalsIgnoreCase("SAIDA")) {
            if (saldoAtual < transacao.getValor()) {
                throw new IllegalArgumentException("Saldo insuficiente para realizar a transação.");
            }
            conta.setSaldo(saldoAtual - transacao.getValor());
        } else if (transacao.getTipo().equalsIgnoreCase("ENTRADA")) {
            conta.setSaldo(saldoAtual + transacao.getValor());
        } else {
            throw new IllegalArgumentException("Tipo de transação inválido.");
        }

        contaRepository.save(conta);
        return transacaoRepository.save(transacao);
    }

    public Transacao atualizar(Long id, Transacao novaTransacao) {
        Transacao existente = buscarPorId(id);
        if (existente == null) {
            throw new IllegalArgumentException("Transação não encontrada.");
        }

        novaTransacao.setId(id);
        return salvar(novaTransacao);
    }

    public void excluir(Long id) {
        Transacao transacao = buscarPorId(id);
        if (transacao == null) {
            throw new IllegalArgumentException("Transação não encontrada.");
        }

        Conta conta = transacao.getConta();

        if (transacao.getTipo().equalsIgnoreCase("SAIDA")) {
            conta.setSaldo(conta.getSaldo() + transacao.getValor());
        } else if (transacao.getTipo().equalsIgnoreCase("ENTRADA")) {
            conta.setSaldo(conta.getSaldo() - transacao.getValor());
        }

        contaRepository.save(conta);
        transacaoRepository.deleteById(id);
    }
}

