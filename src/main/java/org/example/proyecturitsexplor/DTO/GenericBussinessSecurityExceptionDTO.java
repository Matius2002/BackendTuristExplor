/*Objetivo: proporcionar una forma estructurada de manejar y transmitir información sobre errores*/
package org.example.proyecturitsexplor.DTO; /*Paquete*/

/*Clase*/
public class GenericBussinessSecurityExceptionDTO { 

    /*Atributos*/
    private String titulo;
    private String code;
    private String detalle;
    private String tipo;

    /*Constructores*/
    public GenericBussinessSecurityExceptionDTO() {}
    public GenericBussinessSecurityExceptionDTO(String titulo, String code, String detalle, String tipo) {
        this.titulo = titulo;
        this.code = code;
        this.detalle = detalle;
        this.tipo = tipo;
    }

    /*Métodos Getter y Setter*/
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

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
