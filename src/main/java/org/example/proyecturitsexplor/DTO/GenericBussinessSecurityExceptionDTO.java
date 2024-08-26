/*Objetivo: proporcionar una forma estructurada de manejar y transmitir información sobre errores*/
package org.example.proyecturitsexplor.DTO; /*Paquete*/

/*Clase*/
public class GenericBussinessSecurityExceptionDTO { 

    /*Atributos*/
    private String titulo; /*Un título para la excepción*/
    private String code; /*Un código que representa el tipo de error o excepción*/
    private String detalle; /*Detalles adicionales sobre la excepción*/
    private String tipo; /*Tipo de excepción*/

    /*Constructores*/
    public GenericBussinessSecurityExceptionDTO() {} /*Permite crear un objeto sin inicializar sus atributos*/
    public GenericBussinessSecurityExceptionDTO(String titulo, String code, String detalle, String tipo) { /*Permite crear una instancia de la clase inicializando todos sus atributos al mismo tiempo*/
        this.titulo = titulo; 
        this.code = code; 
        this.detalle = detalle; 
        this.tipo = tipo; 
    }

    /*Métodos Getter y Setter*/
    public String getTitulo() { /*Obtienen y establecen el valor del atributo titulo*/
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCode() { /*Obtienen y establecen el valor del atributo code*/
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getDetalle() { /*Obtienen y establecen el valor del atributo detalle*/
        return detalle;
    }
    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getTipo() { /*Obtienen y establecen el valor del atributo tipo*/
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
