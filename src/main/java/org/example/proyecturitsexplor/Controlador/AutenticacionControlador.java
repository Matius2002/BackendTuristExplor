package org.example.proyecturitsexplor.Controlador;
import org.example.proyecturitsexplor.Servicios.AutenticacionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080")
public class AutenticacionControlador {
    @Autowired
    private AutenticacionServicio autenticacionServicio;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        boolean autenticado = autenticacionServicio.autenticar(loginRequest.getEmail(), loginRequest.getPassword());
        if (autenticado) {
            return ResponseEntity.ok("Autenticación exitosa");
        } else {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }
    static class LoginRequest {
        private String email;
        private String password;

        // Getters y Setters
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
}
