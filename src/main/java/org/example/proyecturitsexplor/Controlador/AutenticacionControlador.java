package org.example.proyecturitsexplor.Controlador;

import org.example.proyecturitsexplor.DTO.LoginRequestDTO;
import org.example.proyecturitsexplor.DTO.LoginResponseDTO;
import org.example.proyecturitsexplor.Servicios.auth.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller //Define esta clase como un controlador de Spring MVC, capaz de manejar solicitudes HTTP.
@RequestMapping("/api") //Especifica que todas las rutas dentro de esta clase estarán prefijadas con /api.
@CrossOrigin(origins = "http://localhost:8080") //Permite solicitudes CORS (Cross-Origin Resource Sharing) desde http://localhost:8080,

public class AutenticacionControlador {

    //Inyecta una instancia de AuthenticationService en este controlador para que se pueda utilizar en los métodos de la clase.
    @Autowired
    private AuthenticationService authService;

    @PreAuthorize("permitAll()") //Indica que este método es accesible para todos, sin necesidad de autenticación previa.
    @PostMapping("/login") //Define que este método responderá a solicitudes HTTP POST en la ruta /api/login
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO credenciales) { //El método recibe un objeto LoginRequestDTO en el cuerpo de la solicitud (@RequestBody)
        return ResponseEntity.ok(this.authService.login(credenciales)); //El método llama al servicio de autenticación (authService.login(credenciales)) para autenticar las credenciales.
        //ResponseEntity.ok(...) devuelve una respuesta HTTP 200 con un objeto LoginResponseDTO si la autenticación es exitosa.
    }

    @GetMapping("/validateToken") //Define que este método responderá a solicitudes HTTP GET en la ruta /api/validateToken.
    public ResponseEntity<Boolean> validateToken(@RequestParam String token) { //Recibe un parámetro token en la URL (@RequestParam).
        boolean isTokenValid = this.authService.validateToken(token); //Llama al servicio de autenticación (authService.validateToken(token)) para verificar si el token es válido.
        return ResponseEntity.ok(isTokenValid); //devuelve una respuesta HTTP 200 con un valor booleano (true o false) que indica si el token es válido o no.
    }

}
