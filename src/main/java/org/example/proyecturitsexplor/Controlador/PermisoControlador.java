package org.example.proyecturitsexplor.Controlador;
import org.example.proyecturitsexplor.Entidades.Permiso;
import org.example.proyecturitsexplor.Entidades.Rol;
import org.example.proyecturitsexplor.Entidades.Usuarios;
import org.example.proyecturitsexplor.Servicios.PermisoServicio;
import org.example.proyecturitsexplor.Servicios.UserServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080")
public class PermisoControlador {
    @Autowired
    private PermisoServicio permisoServicio;
    @Autowired
    private UserServicio userServicio;


    @PostMapping("/permisos/guardar")
    public ResponseEntity<Permiso> guardarPermiso(@RequestBody Permiso permiso) {
        Permiso permisoGuardado = permisoServicio.guardarPermiso(permiso);
        return ResponseEntity.status(HttpStatus.CREATED).body(permisoGuardado);
    }

    @GetMapping("/permisos/obtenerTodos")
    public ResponseEntity<List<Permiso>> obtenerTodosLosPermisos() {
        List<Permiso> permisos = permisoServicio.obtenerTodosLosPermisos();
        return ResponseEntity.ok(permisos);
    }

    @GetMapping("/permisos/obtenerPorId/{id}")
    public ResponseEntity<Permiso> obtenerPermisoPorId(@PathVariable Long id) {
        Permiso permiso = permisoServicio.obtenerPermisoPorId(id);
        return ResponseEntity.ok(permiso);
    }

    @PutMapping("/permisos/actualizar/{id}")
    public ResponseEntity<Permiso> actualizarPermiso(@PathVariable Long id, @RequestBody Permiso permisoActualizado) {
        if (!id.equals(permisoActualizado.getId())) {
            return ResponseEntity.badRequest().build();
        }
        Permiso permisoActual = permisoServicio.obtenerPermisoPorId(id);
        if (permisoActual == null) {
            return ResponseEntity.notFound().build();
        }
        Permiso permisoGuardado = permisoServicio.actualizarPermiso(permisoActualizado);
        return ResponseEntity.ok(permisoGuardado);
    }

    @DeleteMapping("/permisos/eliminar/{id}")
    public ResponseEntity<Void> eliminarPermiso(@PathVariable Long id, @RequestHeader("email") String email) {
        if (!tienePermiso(email)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        permisoServicio.eliminarPermiso(id);
        return ResponseEntity.noContent().build();
    }

    private boolean tienePermiso(String email) {
        Usuarios usuario = userServicio.obtenerPorEmail(email);

        // Verificar si el usuario existe
        if (usuario != null) {
            // Obtener los roles del usuario
            List<Rol> rolesUsuario = usuario.getRoles();

            // Verificar si el usuario tiene el rol de administrador
            for (Rol rol : rolesUsuario) {
                if (rol.getRolName().equals("ADMIN")) {
                    // Si el usuario tiene el rol de "ADMIN", se le permite eliminar el permiso
                    return true;
                }
            }
        }

        // Si el usuario no existe o no tiene el rol de "ADMIN", no se le permite eliminar el permiso
        return false;
    }

}
