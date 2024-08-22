package org.example.proyecturitsexplor.Excepciones;

import org.springframework.http.HttpStatus;

public class BadCredentialsException extends BussinessSecurityException{

    public BadCredentialsException(String message, String code, String titulo, HttpStatus httpStatus) {
        super(code, titulo, message, httpStatus);
    }


}
