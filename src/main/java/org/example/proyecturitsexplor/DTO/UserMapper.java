/*Objetivo: Proporciona una manera de convertir un objeto de tipo Usuarios (una entidad del dominio) en un UserResponseDTO 
(un objeto de transferencia de datos) para su uso en otras partes de la aplicación, especialmente en la capa de presentación 
o al enviar respuestas en una API REST. El mapeo convierte solo los datos necesarios (ID, nombre de usuario y email)*/
package org.example.proyecturitsexplor.DTO; /*Paquete*/

import org.example.proyecturitsexplor.Entidades.Usuarios; /*Importación*/

/*Clase UserMapper*/
public class UserMapper {

    /*Método mapToUserResponseDTO*/
    public static UserResponseDTO mapToUserResponseDTO(Usuarios usuario) {
        return new UserResponseDTO(usuario.getId(), usuario.getNombreUsuario(), usuario.getEmail());
    }
}
