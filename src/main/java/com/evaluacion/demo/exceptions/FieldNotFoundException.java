package com.evaluacion.demo.exceptions;

public class FieldNotFoundException extends RuntimeException {
    public FieldNotFoundException(String mensaje) {
        super(mensaje);
    }
}
