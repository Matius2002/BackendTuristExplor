package org.example.proyecturitsexplor.DTO; /*Paquete*/

/*Clase*/
/*El nombre LoginRequestDTO indica que esta clase es un objeto de transferencia de datos (DTO) utilizado para encapsular la 
información necesaria para procesar una solicitud de inicio de sesión*/
public class LoginRequestDTO { 

    /*Atributos (Campos)*/
    private String email; /*La dirección de correo electrónico del usuario que intenta iniciar sesión*/
    private String password; /*La contraseña del usuario*/

    /*Constructores*/
    public LoginRequestDTO() {} /*permite crear un objeto sin inicializar sus atributos*/
    public LoginRequestDTO(String email, String password) { /*permite crear una instancia de la clase inicializando los atributos email y password al mismo tiempo*/
        this.email = email;
        this.password = password;
    }

    /*Métodos Getter y Setter*/
    public String getEmail() { /*Obtienen y establecen el valor del atributo email*/
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() { /*Obtienen y establecen el valor del atributo password*/
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
