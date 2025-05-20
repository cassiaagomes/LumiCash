package br.edu.ifpb.pweb2.lumicash;

import br.edu.ifpb.pweb2.lumicash.entity.Correntista;
import br.edu.ifpb.pweb2.lumicash.service.CorrentistaService;

public class Main {
    public static void main(String[] args) {
        CorrentistaService service = new CorrentistaService();

        try {
            Correntista c1 = new Correntista();
            c1.setNome("Ana Silva");
            c1.setEmail("ana.silva@email.com");
            c1.setSenha("senha123");
            c1.setIsAdmin(false);
            service.adicionarCorrentista(c1);

            Correntista c2 = new Correntista();
            c2.setNome("Carlos Souza");
            c2.setEmail("carlos.souza@email.com");
            c2.setSenha("minhaSenha");
            c2.setIsAdmin(true);
            service.adicionarCorrentista(c2);

            System.out.println("Lista de correntistas cadastrados:");
            for (Correntista c : service.listarCorrentistas()) {
                System.out.println("ID: " + c.getId() + ", Nome: " + c.getNome() + ", Email: " + c.getEmail() + ", Admin: " + c.getIsAdmin());
            }

            long idParaBuscar = 1;
            Correntista encontrado = service.buscarPorId((int) idParaBuscar);
            if (encontrado != null) {
                System.out.println("\nCorrentista encontrado com ID " + idParaBuscar + ": " + encontrado.getNome());
            } else {
                System.out.println("\nNenhum correntista encontrado com ID " + idParaBuscar);
            }

        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao adicionar correntista: " + e.getMessage());
        }
    }
}
