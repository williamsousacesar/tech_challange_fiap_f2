package br.com.fiap.horadoprato.horadoprato.exceptions;

public class TipoUsuarioJaExisteException extends RuntimeException {

    public TipoUsuarioJaExisteException(String message) {
        super(message);
    }
}
