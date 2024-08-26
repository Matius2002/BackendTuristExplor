/*Objetivo: Proporciona una manera de convertir un objeto de tipo Usuarios (una entidad del dominio) en un UserResponseDTO 
(un objeto de transferencia de datos) para su uso en otras partes de la aplicación, especialmente en la capa de presentación 
o al enviar respuestas en una API REST. El mapeo convierte solo los datos necesarios (ID, nombre de usuario y email)*/
package org.example.proyecturitsexplor.DTO; /*Paquete*/

/*Importación*/
import org.example.proyecturitsexplor.Entidades.Usuarios;  /*La clase Usuarios es probablemente una entidad de dominio que representa a un usuario en la base de datos*/

/*Clase UserMapper*/
public class UserMapper { /*El nombre UserMapper indica que su propósito es mapear o convertir objetos de tipo Usuarios a otro tipo de objeto, en este caso, un UserResponseDTO*/

    /*Método mapToUserResponseDTO*/
    public static UserResponseDTO mapToUserResponseDTO(Usuarios usuario) { /*El método mapToUserResponseDTO toma un objeto Usuarios como parámetro y devuelve un objeto UserResponseDTO*/
        return new UserResponseDTO(usuario.getId(), usuario.getNombreUsuario(), usuario.getEmail()); /*El método extrae tres campos específicos del objeto Usuarios: id, nombreUsuario, y email*/
        /*Luego, crea y devuelve una nueva instancia de UserResponseDTO, utilizando los valores extraídos del objeto Usuarios*/
    }
}
