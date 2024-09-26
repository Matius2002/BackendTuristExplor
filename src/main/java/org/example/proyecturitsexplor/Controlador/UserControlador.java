package org.example.proyecturitsexplor.Controlador;
import jakarta.servlet.http.HttpServletRequest;
import org.example.proyecturitsexplor.Entidades.Usuarios;
import org.example.proyecturitsexplor.Repositorios.UserRepositorio;
import org.example.proyecturitsexplor.Servicios.UserServicio;
import org.example.proyecturitsexplor.Servicios.auth.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api")
@CrossOrigin //(origins = "http://localhost:8080")
public class UserControlador {
    @SuppressWarnings("unused")
    @Autowired
    private UserRepositorio userRepositorio;
    @Autowired
    private UserServicio userServicio;
    @Autowired
    private AuthenticationService authenticationService;

    // CRUD para Usuarios

    //CONTROLADOR LIBRE
    @PreAuthorize("permitAll()")
    @PostMapping("/usuarios/guardarUsuario")
    public ResponseEntity<Usuarios> guardarUsuario(@RequestBody Usuarios usuarios) {
        if (usuarios.getNombreUsuario()==null || usuarios.getEmail()==null ||
                usuarios.getRoles()==null || usuarios.getPassword()==null){
            return  ResponseEntity.badRequest().build();
        }
        Usuarios usuarioGuardado = userServicio.guardarUsuarios(usuarios);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioGuardado);
    }

    //Recuperar todos los Usuarios
    @GetMapping("/usuarios/obtenerTodosLosUsuario")
    public ResponseEntity<List<Usuarios>> obtenerTodosLosUsuarios() {
        List<Usuarios> usuarios = userServicio.obtenerTodosLosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    //Recuperar Usuarios por id
    @GetMapping("/usuarios/recuperarPorId/{id}")
    public ResponseEntity<Usuarios>obtenerUsuariosPorId(@PathVariable Long id){
        Usuarios usuarios = userServicio.obtenerUsuariosPorId(id);
        return ResponseEntity.ok(usuarios);
    }

    //Actulizar Usuarios
    @PutMapping("/usuarios/{id}")
    public ResponseEntity<?> actualizarUsuarios(@PathVariable("id") Long id, @RequestBody Usuarios usuarioActualizada) {
        try {

            if (!id.equals(usuarioActualizada.getId())) {
                throw new IllegalArgumentException("El ID del Usuario principal del cuerpo no coincide con el ID proporcionado en la ruta.");
            }
            Usuarios usuarioActual = userServicio.obtenerUsuariosPorId(id);
            if (usuarioActual == null) {
                return new ResponseEntity<>("No se encontró ningun Usuario con el ID proporcionado.", HttpStatus.NOT_FOUND);
            }
            Usuarios usuarioGuardada = userServicio.actulizarUsuarios(usuarioActualizada);
            return new ResponseEntity<>(usuarioGuardada, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //Eliminar Usuario
    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<String> eliminarUsuarioPorId(@PathVariable Long id) {
        userServicio.eliminarUsuarios(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuario con ID " + id + " eliminada correctamente.");
    }

    //Verificar si un Usuario  existe en la base de datos
    @GetMapping("/usuarios/existe/{nombreUsuario}")
    public ResponseEntity<?> verificarUsuarioExistente(@PathVariable String nombreUsuario) {
        try {
            boolean existe = userServicio.verificarUsuariosExistente(nombreUsuario);
            return ResponseEntity.ok(existe);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al verificar si el Usuario existe por Nombre: " + e.getMessage());
        }
    }
    @GetMapping("/usuarios/userExiste/{email}")
    public ResponseEntity<?> verificarExistentePorEmail(@PathVariable String email) {
        try {
            boolean existe = userServicio.verificarExistentePorEmail(email);
            return ResponseEntity.ok(existe);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al verificar si el usuario existe por correo electrónico: " + e.getMessage());
        }
    }

    @GetMapping("/usuarios/profile")
    public ResponseEntity<?> verPerfil(){
        return ResponseEntity.ok(this.authenticationService.findLoggedUser());
    }

    @PostMapping("/renew-token")
    public ResponseEntity<Map<String, Object>> renewToken(HttpServletRequest request) {
        String token = authenticationService.resolveToken(request);
        String renewedToken = authenticationService.renewToken(token);
        Map<String, Object> response = new HashMap<>();
        response.put("token", renewedToken);
        response.put("expirationTime", authenticationService.getExpirationTime(renewedToken));

        return ResponseEntity.ok(response);
    }

}

