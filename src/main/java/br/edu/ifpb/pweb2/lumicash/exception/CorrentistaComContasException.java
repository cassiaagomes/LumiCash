package br.edu.ifpb.pweb2.lumicash.exception;

public class CorrentistaComContasException extends Exception {
    private final int numeroContas;
    
    public CorrentistaComContasException(String nome, int numeroContas) {
        super(String.format(
            "Não é possível excluir o correntista '%s' pois ele possui %d conta(s) ativa(s). " +
            "Por favor, encerre ou remova todas as contas primeiro.", 
            nome, numeroContas
        ));
        this.numeroContas = numeroContas;
    }
    
    public int getNumeroContas() {
        return numeroContas;
    }
}