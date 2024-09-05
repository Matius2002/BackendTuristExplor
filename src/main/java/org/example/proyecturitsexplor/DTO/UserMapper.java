package org.example.proyecturitsexplor.DTO; /*Paquete*/

import org.example.proyecturitsexplor.Entidades.Usuarios;  

public class UserMapper { 

    public static UserResponseDTO mapToUserResponseDTO(Usuarios usuario) { 
        return new UserResponseDTO(usuario.getId(), usuario.getNombreUsuario(), usuario.getEmail()); 
        
    }
}
