package br.edu.ifpb.pweb2.lumicash.exception;

public class UsuarioBloqueadoException extends RuntimeException {
    public UsuarioBloqueadoException() {
        super("Usuário bloqueado. Contate o administrador.");
    }
}
