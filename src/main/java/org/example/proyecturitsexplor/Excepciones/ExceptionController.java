package org.example.proyecturitsexplor.Excepciones;
import org.example.proyecturitsexplor.DTO.GenericBussinessSecurityExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> returnBadCredentialsException(BadCredentialsException e){
        return ResponseEntity.badRequest().body(this.createBussinessSecurityExceptionResponse(e, "BadCredentialsException"));
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<GenericBussinessSecurityExceptionDTO> returnAccessDeniedException(AccessDeniedException e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new GenericBussinessSecurityExceptionDTO("Acceso denegado", "403", "No posee permisos para realizar esta acción", "AccessDeniedException"));
    }
    private GenericBussinessSecurityExceptionDTO createBussinessSecurityExceptionResponse(BussinessSecurityException e, String tipo){
        return new GenericBussinessSecurityExceptionDTO(e.getTitulo(), e.getCode(), e.getMessage(), tipo);
    }

}
