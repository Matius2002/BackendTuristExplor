package org.example.proyecturitsexplor.Seguridad.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.proyecturitsexplor.DTO.GenericBussinessSecurityExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException; /*La excepción que se lanza cuando falla una autenticación*/
import org.springframework.security.web.AuthenticationEntryPoint; /*Una interfaz de Spring Security que define cómo manejar errores de autenticación*/
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint { /*CustomAuthenticationEntryPoint implementa la interfaz AuthenticationEntryPoint, lo que significa que debe proporcionar una implementación para el método commence, que se invoca cuando una solicitud no autenticada intenta acceder a un recurso protegido*/
    private final ObjectMapper objectMapper = new ObjectMapper(); /*Es una instancia de ObjectMapper de la biblioteca Jackson, utilizada para convertir objetos Java en JSON. Aquí, se usa para serializar el objeto GenericBussinessSecurityExceptionDTO en un formato JSON antes de enviarlo en la respuesta*/
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException { /*commence es el método que se invoca cuando una solicitud no autenticada intenta acceder a un recurso protegido. Aquí es donde se maneja el error de autenticación y se envía una respuesta adecuada al cliente*/
        GenericBussinessSecurityExceptionDTO exceptionDTO = new GenericBussinessSecurityExceptionDTO("Error de inicio de sesión", "401", "Aún no ha iniciado sesión, por favor, inicie sesión para continuar", "AuthenticationException"); /*Se crea un objeto GenericBussinessSecurityExceptionDTO para encapsular la información sobre la excepción.*/
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); /*Configura el tipo de contenido de la respuesta como JSON*/
        response.setStatus(HttpStatus.UNAUTHORIZED.value()); /*Establece el código de estado HTTP a 401 Unauthorized, indicando que la solicitud no está autorizada debido a la falta de autenticación*/
        response.getOutputStream().write(objectMapper.writeValueAsBytes(exceptionDTO)); /*Convierte el objeto exceptionDTO a JSON utilizando ObjectMapper y lo escribe en la respuesta HTTP*/
    }
}
