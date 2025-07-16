package br.edu.ifpb.pweb2.lumicash.service;

import java.util.List;
import br.edu.ifpb.pweb2.lumicash.entity.Correntista;
import br.edu.ifpb.pweb2.lumicash.repository.CorrentistaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CorrentistaService {

    @Autowired
    private CorrentistaRepository repository;

    // ✅ SOLUÇÃO 1: Adicionar EntityManager para controle de sessão
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Correntista salvar(Correntista correntista) {
        if (correntista.getId() == null) {
            System.out.println("[SERVICE] Salvando novo correntista...");

            if (correntista.getIsAdmin() == null) {
                correntista.setIsAdmin(false);
            }
            if (correntista.getAtivo() == null) {
                correntista.setAtivo(true);
            }

            // Limpa as contas para evitar o erro
            if (correntista.getContas() != null) {
                correntista.getContas().clear();
            }

            return repository.save(correntista);

        } else {
            System.out.println("[SERVICE] Atualizando correntista existente...");

            Correntista existente = repository.findById(correntista.getId())
                    .orElseThrow(() -> new RuntimeException("Correntista não encontrado"));

            existente.setNome(correntista.getNome());
            existente.setEmail(correntista.getEmail());
            existente.setSenha(correntista.getSenha());
            existente.setIsAdmin(correntista.getIsAdmin());
            existente.setAtivo(correntista.getAtivo());

            // Não mexa na coleção de contas aqui, para evitar problemas

            return repository.save(existente);
        }
    }

    public Correntista encontrarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Correntista não encontrado"));
    }

    public Correntista encontrarporEmail(String email) throws Exception {
        return repository.findByEmail(email)
                .orElseThrow(() -> new Exception("Correntista não encontrado"));
    }

    @Transactional
    public Correntista encontrarPorIdComContas(Long id) {
        Correntista c = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Correntista não encontrado"));

        // Inicializar a coleção dentro da transação
        c.getContas().size();

        return c;
    }

    @Transactional
    public void apagarPorId(Long id) throws Exception {
        System.out.println("[SERVICE] Iniciando exclusão do correntista ID: " + id);

        // ✅ CORREÇÃO: Buscar com contas para garantir que tudo seja carregado
        Correntista correntista = repository.findById(id)
                .orElseThrow(() -> new Exception("Correntista não encontrado com o ID: " + id));

        // Forçar carregamento das contas
        correntista.getContas().size();

        System.out.println("[SERVICE] Correntista encontrado com " + correntista.getContas().size() + " contas");

        // Remover o correntista (cascade irá remover as contas)
        repository.delete(correntista);

        // ✅ CORREÇÃO: Limpar cache do EntityManager
        entityManager.flush();
        entityManager.clear();

        System.out.println("[SERVICE] Correntista excluído com sucesso");
    }

    @Transactional
    public void bloquear(Long id) {
        Correntista c = encontrarPorId(id);
        c.setAtivo(false);
        repository.save(c);
    }

    @Transactional
    public void desbloquear(Long id) {
        Correntista c = encontrarPorId(id);
        c.setAtivo(true);
        repository.save(c);
    }

    public List<Correntista> listarCorrentistas() {
        return repository.findAll();
    }
}