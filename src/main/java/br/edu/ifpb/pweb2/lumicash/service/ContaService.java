package br.edu.ifpb.pweb2.lumicash.service;

import br.edu.ifpb.pweb2.lumicash.entity.Conta;
import br.edu.ifpb.pweb2.lumicash.entity.Correntista;
import br.edu.ifpb.pweb2.lumicash.repository.ContaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
public class ContaService {

    @Autowired
    private ContaRepository repository;

    @Autowired
    private CorrentistaService correntistaService;
    
    // ✅ SOLUÇÃO: Adicionar EntityManager para controle de sessão
    @PersistenceContext
    private EntityManager entityManager;

    public Conta save(Conta conta) {
        return repository.save(conta);
    }

    public List<Conta> findAll() {
        return repository.findAll();
    }

    public Conta findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Conta de ID " + id + " não encontrada"));
    }

    @Transactional
    public Conta CriarConta(Conta conta, Correntista correntista) {
        // Validações
        if ("CARTAO".equalsIgnoreCase(conta.getTipo())) {
            if (conta.getDiaFechamento() == null) {
                throw new IllegalArgumentException("Dia de fechamento não pode ser nulo para contas do tipo CARTAO");
            }

            if (conta.getDiaFechamento() < 1 || conta.getDiaFechamento() > 31) {
                throw new IllegalArgumentException("Dia de fechamento deve ser entre 1 e 31");
            }
        }

        // ✅ CORREÇÃO: Garantir que o correntista está anexado à sessão atual
        if (correntista.getId() != null) {
            // Se tem ID, buscar da sessão atual para evitar detached entity
            correntista = correntistaService.encontrarPorIdComContas(correntista.getId());
        }

        // ✅ CORREÇÃO: Criar nova conta limpa
        Conta novaConta = new Conta();
        novaConta.setDescricao(conta.getDescricao());
        novaConta.setNumero(conta.getNumero());
        novaConta.setTipo(conta.getTipo());
        novaConta.setDiaFechamento(conta.getDiaFechamento());
        
        // Estabelecer relacionamento
        correntista.addConta(novaConta);

        // Salvar o correntista (cascade irá salvar a conta)
        Correntista salvo = correntistaService.salvar(correntista);
        
        // Encontrar a conta recém-criada
        return salvo.getContas()
                .stream()
                .filter(c -> c.getDescricao().equals(novaConta.getDescricao()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Conta não salva"));
    }

    @Transactional
    public void excluirConta(Conta conta) {
        // ✅ CORREÇÃO: Garantir que a conta está anexada à sessão
        if (conta.getId() != null) {
            conta = repository.findById(conta.getId())
                    .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        }
        
        repository.delete(conta);
        
        // Limpar cache
        entityManager.flush();
        entityManager.clear();
    }

    public List<Conta> findByCorrentista(Correntista correntista) {
        return repository.findByCorrentista(correntista);
    }

    public List<Conta> listarContasDoCorrentista(Correntista correntista) {
        return this.findByCorrentista(correntista);
    }

    public Page<Conta> listarContasDoCorrentistaPaginado(Correntista correntista, Pageable pageable) {
    return repository.findByCorrentista(correntista, pageable);
}
}