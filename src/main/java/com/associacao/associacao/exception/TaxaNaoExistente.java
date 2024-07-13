package com.associacao.associacao.exception;

public class TaxaNaoExistente extends RuntimeException{
    public TaxaNaoExistente() {
        super("Taxa Nao Existe");
    }
    
}
