package br.edu.ifpb.pweb2.lumicash.service;

import br.edu.ifpb.pweb2.lumicash.entity.Conta;
import br.edu.ifpb.pweb2.lumicash.entity.Correntista;
import br.edu.ifpb.pweb2.lumicash.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ContaService {

    @Autowired
    private ContaRepository repository;

    @Autowired
    private CorrentistaService correntistaService;

    public Conta save(Conta conta) {
        return repository.save(conta);
    }

    public List<Conta> findAll(){
        return repository.findAll();
    }

    public Conta findById(Long id){
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Conta de ID " + id + " não encontrada"));
    }

    public Conta CriarConta(Conta conta, Correntista correntista) {
        if ("CARTAO".equalsIgnoreCase(conta.getTipo())) {
            if(conta.getDiaFechamento() == null ) {
                throw new IllegalArgumentException("Dia de fechamento não pode ser nulo para contas do tipo CARTAO");
            }

            if (conta.getDiaFechamento() < 1 || conta.getDiaFechamento() > 31) {
                throw new IllegalArgumentException("Dia de fechamento deve ser entre 1 e 31");

            }
        }
        conta.setCorrentista(correntista);

        return this.save(conta);
    }

    public List<Conta> findByCorrentista(Correntista correntista) {
        return repository.findByCorrentista(correntista);
    }

    public List<Conta> listarContasDoCorrentista(Correntista correntista) {
        return this.findByCorrentista(correntista);
    }

}
