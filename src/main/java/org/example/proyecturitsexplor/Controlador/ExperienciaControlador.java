package org.example.proyecturitsexplor.Controlador;

import org.example.proyecturitsexplor.Entidades.Experiencia;
import org.example.proyecturitsexplor.Excepciones.ExperienciaNotFoundException;
import org.example.proyecturitsexplor.Repositorios.ExperienciaRepositorio;
import org.example.proyecturitsexplor.Servicios.ExperienciaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080")
public class ExperienciaControlador {

    @SuppressWarnings("unused")
    @Autowired
    private ExperienciaRepositorio experienciaRepositorio;
    @Autowired
    private ExperienciaServicio experienciaServicio;

    // Guardar experiencia
    @PostMapping("/experiencias/guardarExperiencia")
    public ResponseEntity<?> guardarExperiencia(@RequestBody Experiencia experiencia) {
        try {
            // Validaciones de destino y usuario
            if (experiencia.getDestino() == null || experiencia.getDestino().getId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El destino no puede estar vacío.");
            }
            if (experiencia.getUsuario() == null || experiencia.getUsuario().getId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario no puede estar vacío.");
            }

            Experiencia experienciaGuardada = experienciaServicio.guardarExperiencia(experiencia);
            return ResponseEntity.status(HttpStatus.CREATED).body(experienciaGuardada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar la experiencia.");
        }
    }

    // Obtener todas las experiencias
    @GetMapping("/experiencias/obtenerTodosLosExperiencia")
    public ResponseEntity<List<Experiencia>> obtenerTodosLosExperiencia() {
        List<Experiencia> experiencias = experienciaServicio.obtenerTodosLosExperiencia();
        return ResponseEntity.ok(experiencias);
    }

    // Obtener experiencia por ID
    @GetMapping("/experiencias/recuperarPorId/{id}")
    public ResponseEntity<?> obtenerExperienciaPorId(@PathVariable Long id) {
        try {
            Experiencia experiencia = experienciaServicio.obtenerExperienciaPorId(id);
            return ResponseEntity.ok(experiencia);
        } catch (ExperienciaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Actualizar experiencia
    @PutMapping("/experiencias/{id}")
    public ResponseEntity<?> actualizarExperiencia(@PathVariable("id") Long id, @RequestBody Experiencia experienciaActualizada) {
        try {
            if (!id.equals(experienciaActualizada.getId())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El ID de la experiencia no coincide.");
            }

            Experiencia experienciaActual = experienciaServicio.obtenerExperienciaPorId(id);
            Experiencia experienciaActualizadaGuardada = experienciaServicio.actulizarExperiencia(experienciaActualizada);
            return ResponseEntity.ok(experienciaActualizadaGuardada);
        } catch (ExperienciaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Eliminar experiencia
    @DeleteMapping("/experiencias/{id}")
    public ResponseEntity<?> eliminarExperienciaPorId(@PathVariable Long id) {
        try {
            experienciaServicio.eliminarExperiencia(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Experiencia eliminada correctamente.");
        } catch (ExperienciaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
