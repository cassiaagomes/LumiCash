package br.edu.ifpb.pweb2.lumicash.service;

import org.springframework.stereotype.Service;

import br.edu.ifpb.pweb2.lumicash.entity.Conta;
import br.edu.ifpb.pweb2.lumicash.entity.Correntista;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class FacadeService {

    @Autowired
    private ContaService contaService;

    @Autowired
    private CorrentistaService correntistaService;

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

        return contaService.save(conta);
    }
}
 