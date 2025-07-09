package br.edu.ifpb.pweb2.lumicash.service;

import java.util.List;
import java.util.ArrayList;
import br.edu.ifpb.pweb2.lumicash.entity.Correntista;
import br.edu.ifpb.pweb2.lumicash.repository.CorrentistaRepository;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CorrentistaService {

    @Autowired
    private CorrentistaRepository repository;

    public Correntista salvar(Correntista correntista) {
        return repository.save(correntista);
    }

    public Correntista encontrarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Correntista não encontrado"));
    }

    public Correntista encontrarporEmail(String email) throws Exception {
        return repository.findByEmail(email)
                .orElseThrow(() -> new Exception("Correntista não encontrado"));
    }

    // Em CorrentistaService.java

    @Transactional
    public void apagarPorId(Long id) throws Exception {
        // 1. Apenas encontre o correntista que deve ser apagado
        Correntista correntista = repository.findById(id)
                .orElseThrow(() -> new Exception("Correntista não encontrado com o ID: " + id));

        // 2. Peça ao repositório para apagar. O Hibernate fará o resto.
        // Ele verá a anotação @OneToMany com CascadeType.ALL e irá apagar
        // todas as contas associadas antes de apagar o correntista.
        repository.delete(correntista);
    }

    public void bloquear(Long id) {
        Correntista c = encontrarPorId(id);
        c.setAtivo(false);
        repository.save(c);
    }

    public void desbloquear(Long id) {
        Correntista c = encontrarPorId(id);
        c.setAtivo(true);
        repository.save(c);
    }

    public List<Correntista> listarCorrentistas() {
        return repository.findAll();
    }
}
