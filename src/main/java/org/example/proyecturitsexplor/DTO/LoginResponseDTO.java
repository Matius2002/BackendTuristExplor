/*Objetivo: es un objeto de transferencia de datos que encapsula un token de autenticación. Se utiliza para transportar 
el token desde el backend al frontend (o a cualquier cliente) después de que un usuario haya iniciado sesión exitosamente 
en la aplicación.*/
package org.example.proyecturitsexplor.DTO; /*Paquete*/

public class LoginResponseDTO { /*Clase*/

    /*Atributo (Campo)*/
    private String token;

    /*Constructores*/
    public LoginResponseDTO() {}
    public LoginResponseDTO(String token) {
        this.token = token;
    }

    /*Métodos Getter y Setter*/
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
