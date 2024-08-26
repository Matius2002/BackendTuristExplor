/*Objetivo: La clase UserResponseDTO es un DTO que encapsula los datos de un usuario (id, nombreUsuario, y email). 
Tiene un constructor con parámetros que permite inicializar estos valores al crear el objeto, un constructor por defecto 
que permite crear un objeto vacío, y métodos getter y setter para acceder y modificar los valores de los campos de forma segura.*/
package org.example.proyecturitsexplor.DTO; /*Paquete*/

/*Clase */
public class UserResponseDTO { 

    /*Atributos (Campos)*/
    private Long id; /*Un identificador único del usuario*/
    private String nombreUsuario; /*El nombre de usuario (username)*/
    private String email; /*La dirección de correo electrónico del usuario*/

    /*Constructor con parámetros*/
    /*Este constructor permite crear una instancia de UserResponseDTO inicializando los atributos id, nombreUsuario, y email al mismo tiempo. 
    Es útil cuando ya se dispone de la información del usuario y se desea crear un objeto UserResponseDTO completamente configurado*/
    public UserResponseDTO(Long id, String nombreUsuario, String email) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
    }

    /*Constructor por defecto*/
    public UserResponseDTO() {} /* Permite crear una instancia de UserResponseDTO sin inicializar los atributos*/

    /*Métodos Getter y Setter*/
    public Long getId() { /* Obtienen y establecen el valor del atributo id*/
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreUsuario() { /*Obtienen y establecen el valor del atributo nombreUsuario*/
        return nombreUsuario;
    }
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmail() { /*Obtienen y establecen el valor del atributo email*/
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
