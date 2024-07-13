package com.associacao.associacao.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MensagemException> exception(Exception ex) {
        return ResponseEntity.ok( new MensagemException(ex.getLocalizedMessage()));
    }


    // private ResponseEntity<MensagemException> handleException( MensagemException mensagemException, HttpStatus status) {
    //     if (status != null) {
    //         return new ResponseEntity<>( mensagemException, status );
    //     } else {
    //         throw new IllegalArgumentException("HttpStatus cannot be null");
    //     }
    // }
    
}
