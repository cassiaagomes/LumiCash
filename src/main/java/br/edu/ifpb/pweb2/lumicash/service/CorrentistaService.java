package br.edu.ifpb.pweb2.lumicash.service;

import java.util.List;
import java.util.ArrayList;
import br.edu.ifpb.pweb2.lumicash.entity.Correntista; 

public class CorrentistaService {
    private List<Correntista> correntistas = new ArrayList<>();
    private int proximoId = 1;

    public void adicionarCorrentista(Correntista correntista) {
        if (correntista.getNome() == null || correntista.getEmail() == null|| correntista.getSenha() == null) {
            throw new IllegalArgumentException("Dados não podem ser vazios");
        } else if (correntista.getNome().length() < 3) {
            throw new IllegalArgumentException("Nome deve ter pelo menos 3 caracteres");
        } else if (correntista.getEmail().length() < 5) {
            throw new IllegalArgumentException("Email deve ter pelo menos 5 caracteres");
        } else if (correntista.getSenha().length() < 6) {
            throw new IllegalArgumentException("Senha deve ter pelo menos 6 caracteres");
        } else if (correntista.getIsAdmin() == null) {
            throw new IllegalArgumentException("Você deve informar se é admin ou não");
        }
        for (Correntista c : correntistas) {
            if (c.getEmail().equals(correntista.getEmail())) {
                throw new IllegalArgumentException("Email já cadastrado");
            }
        }
        correntista.setId((long) proximoId);
        correntistas.add(correntista);
        proximoId++;
    }

    public List<Correntista> listarCorrentistas() {
        return correntistas;
    }

    public Correntista buscarPorId (int id) {
        for (Correntista c: correntistas) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

}