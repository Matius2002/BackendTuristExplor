package org.example.proyecturitsexplor.Controlador;
import org.example.proyecturitsexplor.DTO.LoginRequestDTO;
import org.example.proyecturitsexplor.DTO.LoginResponseDTO;
import org.example.proyecturitsexplor.Servicios.auth.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080")
public class AutenticacionControlador {
    @Autowired
    private AuthenticationService authService;

    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO credenciales) {
        return ResponseEntity.ok(this.authService.login(credenciales));
    }

    @GetMapping("/validateToken")
    public ResponseEntity<Boolean> validateToken(@RequestParam String token) {
        boolean isTokenValid = this.authService.validateToken(token);
        return ResponseEntity.ok(isTokenValid);
    }

}
