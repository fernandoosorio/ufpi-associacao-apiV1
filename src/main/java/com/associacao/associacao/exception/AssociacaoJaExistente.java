package com.associacao.associacao.exception;

public class AssociacaoJaExistente extends RuntimeException{
    public AssociacaoJaExistente() {
        super("Associacao já cadastrada!");
    }
}
