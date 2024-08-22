/*Objetivo: Es un objeto de transferencia de datos que encapsula la información básica necesaria 
para procesar una solicitud de inicio de sesión*/
package org.example.proyecturitsexplor.DTO; /*Paquete*/

/*Clase*/
public class LoginRequestDTO {

    /*Atributos (Campos)*/
    private String email;
    private String password;

    /*Constructores*/
    public LoginRequestDTO() {}
    public LoginRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /*Métodos Getter y Setter*/
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
