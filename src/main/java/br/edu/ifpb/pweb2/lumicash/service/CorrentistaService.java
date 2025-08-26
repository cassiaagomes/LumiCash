package br.edu.ifpb.pweb2.lumicash.service;

import java.util.List;
import br.edu.ifpb.pweb2.lumicash.entity.Correntista;
import br.edu.ifpb.pweb2.lumicash.repository.CorrentistaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
public class CorrentistaService {

    @Autowired
    private CorrentistaRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Correntista salvar(Correntista correntista) {
        if (correntista.getId() == null) {
            System.out.println("[SERVICE] Salvando novo correntista...");
            correntista.setSenha(passwordEncoder.encode(correntista.getSenha()));

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
            
            // Verifica se a senha foi alterada antes de criptografar
            if (correntista.getSenha() != null && !correntista.getSenha().isEmpty() && !passwordEncoder.matches(correntista.getSenha(), existente.getSenha())) {
                existente.setSenha(passwordEncoder.encode(correntista.getSenha()));
            }

            existente.setIsAdmin(correntista.getIsAdmin());
            existente.setAtivo(correntista.getAtivo());

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

        c.getContas().size();

        return c;
    }

    @Transactional
    public void apagarPorId(Long id) throws Exception {
        System.out.println("[SERVICE] Iniciando exclusão do correntista ID: " + id);

        Correntista correntista = repository.findById(id)
                .orElseThrow(() -> new Exception("Correntista não encontrado com o ID: " + id));

        correntista.getContas().size();

        System.out.println("[SERVICE] Correntista encontrado com " + correntista.getContas().size() + " contas");

        repository.delete(correntista);

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

    public Page<Correntista> listarCorrentistasPaginados(int page, int size, String sortBy, String direction) {
    Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
    Pageable pageable = PageRequest.of(page, size, sort);
    return repository.findAll(pageable);
}
}