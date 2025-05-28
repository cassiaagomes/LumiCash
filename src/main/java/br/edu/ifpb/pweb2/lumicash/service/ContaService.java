package br.edu.ifpb.pweb2.lumicash.service;

import br.edu.ifpb.pweb2.lumicash.entity.Conta;
import br.edu.ifpb.pweb2.lumicash.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ContaService {

    @Autowired
    private ContaRepository repository;

    public Conta save(Conta conta) {
        return repository.save(conta);
    }

    public List<Conta> findAll(){
        return repository.findAll();
    }

    public Conta findById(Long id){
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Conta de ID " + id + " não encontrada"));
    }

}
