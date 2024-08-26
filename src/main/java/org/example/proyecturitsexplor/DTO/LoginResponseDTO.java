/*Objetivo: Es un objeto de transferencia de datos que encapsula un token de autenticación. Se utiliza para transportar 
el token desde el backend al frontend (o a cualquier cliente) después de que un usuario haya iniciado sesión exitosamente 
en la aplicación.*/
package org.example.proyecturitsexplor.DTO; /*Paquete*/

/*Clase*/
public class LoginResponseDTO { /*El nombre LoginResponseDTO indica que esta clase es un objeto de transferencia de datos (DTO) utilizado para encapsular y transportar un token de autenticación*/

    /*Atributo (Campo)*/
    private String token; /*Representa el token de autenticación que se generó después de que un usuario haya iniciado sesión exitosamente*/

    /*Constructores*/
    public LoginResponseDTO() {} /*permite crear un objeto sin inicializar el atributo token*/
    public LoginResponseDTO(String token) { /*permite crear una instancia de la clase inicializando el atributo token con un valor específico*/
        this.token = token;
    }

    /*Métodos Getter y Setter*/
    public String getToken() { /*Obtiene el valor del atributo token*/
        return token;
    }

    public void setToken(String token) { /*Establece el valor del atributo token*/
        this.token = token;
    }
}
