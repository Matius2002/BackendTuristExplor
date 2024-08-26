package org.example.proyecturitsexplor.Seguridad.handler;

import com.fasterxml.jackson.databind.ObjectMapper; /*Se utiliza para convertir objetos Java en su representación JSON*/
import jakarta.servlet.ServletException; 
import jakarta.servlet.http.HttpServletRequest; /*Representan la solicitud y respuesta HTTP, respectivamente*/
import jakarta.servlet.http.HttpServletResponse; 
import org.example.proyecturitsexplor.DTO.GenericBussinessSecurityExceptionDTO; /*Un DTO (Data Transfer Object) personalizado que encapsula los detalles de la excepción*/
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException; /*La excepción lanzada cuando se deniega el acceso a un recurso*/
import org.springframework.security.web.access.AccessDeniedHandler; /*La interfaz de Spring Security que define el contrato para manejar excepciones de acceso denegado*/
import org.springframework.stereotype.Component; /*Anotación que indica que esta clase es un componente gestionado por Spring, lo que permite que sea detectada automáticamente y utilizada en el contexto de la aplicación*/
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler { /*implementa la interfaz AccessDeniedHandler, lo que significa que debe proporcionar una implementación para el método handle, que se invoca cuando ocurre un AccessDeniedException*/

    /*Es una instancia de ObjectMapper de la biblioteca Jackson, que se utiliza para convertir objetos Java en su representación JSON y 
    viceversa. Aquí, se usa para convertir el objeto GenericBussinessSecurityExceptionDTO en un JSON antes de enviarlo en la respuesta*/
    private final ObjectMapper objectMapper = new ObjectMapper(); 

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException { /*es el método que se invoca cuando un usuario intenta acceder a un recurso sin los permisos necesarios. Este método personaliza la forma en que se maneja la excepción de acceso denegado*/
        GenericBussinessSecurityExceptionDTO exceptionDTO = new GenericBussinessSecurityExceptionDTO("Acceso denegado", "403", "No posee los permisos para realizar esta acción", "AccessDeniedException"); /*Aquí se crea un objeto GenericBussinessSecurityExceptionDTO que encapsula la información sobre la excepción.*/
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); /*Configura el tipo de contenido de la respuesta como JSON*/
        response.setStatus(HttpStatus.FORBIDDEN.value()); /*Establece el código de estado HTTP a 403 Forbidden, indicando que el acceso está prohibido*/
        response.getOutputStream().write(objectMapper.writeValueAsBytes(exceptionDTO)); /*Convierte el objeto exceptionDTO a JSON utilizando ObjectMapper y escribe ese JSON en la respuesta HTTP*/
    }
}
