package br.com.workshop.workshopmongo.controllers.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(String message) {
        super(message);
    }

    public EmailAlreadyExistsException() {
        super("O email já está cadastrado");
    }
}
