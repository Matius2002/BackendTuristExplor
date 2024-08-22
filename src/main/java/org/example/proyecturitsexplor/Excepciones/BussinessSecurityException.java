package org.example.proyecturitsexplor.Excepciones;

import org.springframework.http.HttpStatus;

public class BussinessSecurityException extends RuntimeException{
    private long id;
    private String titulo;
    private String code;
    private HttpStatus httpStatus;

    public BussinessSecurityException(String code, String titulo, String message, HttpStatus httpStatus){
        super(message);
        this.code=code;
        this.titulo=titulo;
        this.httpStatus=httpStatus;

    }

    public BussinessSecurityException(){}

    public BussinessSecurityException(String message, Throwable cause){
        super(message, cause);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
