package org.example.proyecturitsexplor.Controlador;
import org.example.proyecturitsexplor.Entidades.Rol;
import org.example.proyecturitsexplor.Repositorios.RolRepositorio;
import org.example.proyecturitsexplor.Servicios.AuditoriaServicio;
import org.example.proyecturitsexplor.Servicios.RolServicio;
import org.example.proyecturitsexplor.Servicios.UserServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080")
public class RolControlador {
    @Autowired
    private RolRepositorio rolRepositorio;
    @Autowired
    private RolServicio rolServicio;
    @Autowired
    private UserServicio userServicio;
    @Autowired
    private AuditoriaServicio auditoriaService;

    // Constructor
    public RolControlador(RolRepositorio rolRepositorio, RolServicio rolServicio, UserServicio userServicio, AuditoriaServicio auditoriaServicio) {
        this.rolRepositorio = rolRepositorio;
        this.rolServicio = rolServicio;
        this.userServicio = userServicio;
        this.auditoriaService = auditoriaServicio;
    }

    // Verificar si el usuario tiene el rol requerido
    private boolean tieneRol(String email, String rolName) {
        return userServicio.obtenerUsuariosPorEmail(email).getRoles()
                .stream().anyMatch(rol -> rol.getRolName().equals(rolName));
    }
    // endpoint verifica si un usuario tiene el rol de 'administrador'
    @GetMapping("/check-admin-role/{email}")
    public String checkAdminRole(@PathVariable String email) {
        if (tieneRol(email, "admin")) {
            return "El usuario tiene el rol de 'administrador'.";
        } else {
            return "El usuario no tiene el rol de 'administrador'.";
        }
    }
    //GUARDAR ROL
    @PostMapping("/roles/guardarRoles")
    public ResponseEntity<Rol> guardarRol(@RequestHeader("email") String email, @RequestBody Rol rol) {
        if (!tieneRol(email, "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if (rol.getRolName() == null || rol.getRolDescripc() == null ||
                rol.getRolFechaCreac() == null || rol.getRolFechaModic() == null) {
            return ResponseEntity.badRequest().build();
        }

        Rol rolGuardado = rolServicio.guardarRol(rol);
        auditoriaService.registrarAccion(email, "Guardar Rol", "Se ha guardado el rol: " + rol.getRolName());
        return ResponseEntity.status(HttpStatus.CREATED).body(rolGuardado);
    }

    @GetMapping("/roles/obtenerTodosLasRoles")
    public ResponseEntity<List<Rol>> obtenerTodosLasRoles(@RequestHeader("email") String email) {
        if (!tieneRol(email, "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<Rol> roles = rolServicio.obtenerTodosLosRoles();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/roles/recuperarPorId/{id}")
    public ResponseEntity<Rol> rolPorId(@RequestHeader("email") String email, @PathVariable Long id) {
        if (!tieneRol(email, "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Rol rol = rolServicio.obtenerRolPorId(id);
        return ResponseEntity.ok(rol);
    }

    @PutMapping("/roles/{id}")
    public ResponseEntity<?> actualizarRol(@RequestHeader("email") String email, @PathVariable("id") Long id, @RequestBody Rol rolActualizada) {
        if (!tieneRol(email, "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        try {
            if (!id.equals(rolActualizada.getId())) {
                throw new IllegalArgumentException("El ID del rol principal del cuerpo no coincide con el ID proporcionado en la ruta.");
            }

            Rol rolActual = rolServicio.obtenerRolPorId(id);
            if (rolActual == null) {
                return new ResponseEntity<>("No se encontró ningun Rol principal con el ID proporcionado.", HttpStatus.NOT_FOUND);
            }

            Rol rolGuardada = rolServicio.actulizarRol(rolActualizada);
            return new ResponseEntity<>(rolGuardada, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<String> eliminarRolPorId(@RequestHeader("email") String email, @PathVariable Long id) {
        if (!tieneRol(email, "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        rolServicio.eliminarRol(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Rol Principal con ID " + id + " eliminada correctamente.");
    }

    @GetMapping("/roles/existe/{nombre}")
    public ResponseEntity<?> verificarAtracionPrincipalExistente(@RequestHeader("email") String email, @PathVariable String nombre) {
        if (!tieneRol(email, "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        try {
            boolean existe = rolServicio.verificarRolExistente(nombre);
            return ResponseEntity.ok(existe);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al verificar si el Rol  existe por Nombre: " + e.getMessage());
        }
    }

}


