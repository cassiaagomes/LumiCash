package br.edu.ifpb.pweb2.lumicash.service;

import br.edu.ifpb.pweb2.lumicash.entity.Correntista;
import br.edu.ifpb.pweb2.lumicash.exception.EmailAlreadyExists;
import br.edu.ifpb.pweb2.lumicash.repository.CorrentistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private CorrentistaRepository correntistaRepository;

    private boolean EmailRegistrado(String email) {
        return this.correntistaRepository.findByEmail(email).isPresent();
    }

    public Correntista registrar(Correntista correntista) throws EmailAlreadyExists {
        if (EmailRegistrado(correntista.getEmail())) {
            throw new EmailAlreadyExists();
        }

        if (correntista.getEmail() == null || correntista.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser nulo ou vazio");
        }

        return this.correntistaRepository.save(correntista);
    }

    public Correntista autenticar(String email, String senha) {
        Optional<Correntista> correntistaOpt = correntistaRepository.findByEmailWithContas(email);

        if (correntistaOpt.isPresent()) {
            Correntista correntista = correntistaOpt.get();
            if (correntista.getSenha().equals(senha)) {
                return correntista;
            }
        }

        return null; // não autenticado
    }

}
