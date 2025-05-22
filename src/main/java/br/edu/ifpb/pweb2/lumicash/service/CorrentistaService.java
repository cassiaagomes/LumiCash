package br.edu.ifpb.pweb2.lumicash.service;

import java.util.List;
import java.util.ArrayList;
import br.edu.ifpb.pweb2.lumicash.entity.Correntista;
import br.edu.ifpb.pweb2.lumicash.repository.CorrentistaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CorrentistaService {

    @Autowired
    private  CorrentistaRepository repository;

    public Correntista salvar(Correntista correntista) {
        return repository.save(correntista);
    }

    public Correntista encontrarPorId(Long id){
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Correntista não encontrado"));
    }

    public Correntista encontrarporEmail(String email)throws Exception{
        return repository.retornarPorEmail(email).orElseThrow(() -> new Exception("Correntista não encontrado"));
    }

    public void apagarPorId(Long id)throws Exception{
        repository.deleteById(id);
    }

    public List<Correntista> listarCorrentistas() {
        return repository.findAll();
    }


}
