package com.evaluacion.demo.exceptions;

public class ItemNotFoundException extends RuntimeException{
    public ItemNotFoundException(String mensaje){
        super(mensaje);
    }
}
