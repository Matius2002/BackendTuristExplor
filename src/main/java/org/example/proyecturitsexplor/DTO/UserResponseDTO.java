/*Objetivo: La clase UserResponseDTO es un DTO que encapsula los datos de un usuario (id, nombreUsuario, y email). 
Tiene un constructor con parámetros que permite inicializar estos valores al crear el objeto, un constructor por defecto 
que permite crear un objeto vacío, y métodos getter y setter para acceder y modificar los valores de los campos de forma segura.*/
package org.example.proyecturitsexplor.DTO; /*Paquete*/

/*Clase */
public class UserResponseDTO { 

    /*Atributos (Campos)*/
    private Long id;
    private String nombreUsuario;
    private String email;

    /*Constructor con parámetros*/
    public UserResponseDTO(Long id, String nombreUsuario, String email) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
    }

    /*Constructor por defecto*/
    public UserResponseDTO() {}

    /*Métodos Getter y Setter*/
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
