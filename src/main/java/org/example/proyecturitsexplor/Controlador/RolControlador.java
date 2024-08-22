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

    // Guardar rol
    @PostMapping("/roles/guardarRoles")
    public ResponseEntity<Rol> guardarRol(@RequestBody Rol rol) {
        if (rol.getRolName() == null || rol.getRolDescripc() == null ||
        rol.getRolFechaCreac()==null || rol.getRolFechaModic()==null) {
            return ResponseEntity.badRequest().build();
        }
        Rol rolGuardado = rolServicio.guardarRol(rol);
        return ResponseEntity.status(HttpStatus.CREATED).body(rolGuardado);
    }

    // Recuperar todos los roles
    @GetMapping("/roles/obtenerTodosLasRoles")
    public ResponseEntity<List<Rol>> obtenerTodosLosRoles() {
        List<Rol> roles = rolServicio.obtenerTodosLosRoles();
        return ResponseEntity.ok(roles);
    }

    // Recuperar rol por ID
    @GetMapping("/roles/recuperarPorId/{id}")
    public ResponseEntity<Rol> obtenerRolPorId(@PathVariable Long id) {
        Rol rol = rolServicio.obtenerRolPorId(id);
        if (rol == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rol);
    }

    // Actualizar rol
    @PutMapping("/roles/{id}")
    public ResponseEntity<?> actualizarRol(@PathVariable("id") Long id, @RequestBody Rol rolActualizado) {
        try {
            if (!id.equals(rolActualizado.getId())) {
                throw new IllegalArgumentException("El ID del rol del cuerpo no coincide con el ID proporcionado en la ruta.");
            }
            Rol rolActual = rolServicio.obtenerRolPorId(id);
            if (rolActual == null) {
                return new ResponseEntity<>("No se encontró ningún rol con el ID proporcionado.", HttpStatus.NOT_FOUND);
            }
            Rol rolActualizadoGuardado = rolServicio.actualizarRol(rolActualizado);
            return new ResponseEntity<>(rolActualizadoGuardado, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Eliminar rol
    @DeleteMapping("/roles/{id}")
    public ResponseEntity<String> eliminarRolPorId(@PathVariable Long id) {
        rolServicio.eliminarRol(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Rol con ID " + id + " eliminado correctamente.");
    }

    // Verificar si un rol existe por nombre
    @GetMapping("/roles/existe/{nombre}")
    public ResponseEntity<?> verificarRolExistente(@PathVariable String nombre) {
        try {
            boolean existe = rolServicio.verificarRolExistente(nombre);
            return ResponseEntity.ok(existe);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al verificar si el rol existe por nombre: " + e.getMessage());
        }
    }
}


