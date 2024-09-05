package org.example.proyecturitsexplor.Controlador;

import org.example.proyecturitsexplor.Entidades.Experiencia;
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

    // CRUD

    @PostMapping("/experiencias/guardarExperiencia")
public ResponseEntity<?> guardarExperiencia(@RequestBody Experiencia experiencia) {
    // Logs detallados de los valores recibidos
    System.out.println("Datos completos de la experiencia: " + experiencia);
    System.out.println("Destino: " + (experiencia.getDestino() != null ? experiencia.getDestino().getId() : "null"));
    System.out.println("Usuario: " + (experiencia.getUsuario() != null ? experiencia.getUsuario().getId() : "null"));

    if (experiencia.getDestino() == null || experiencia.getDestino().getId() == null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El destino no puede estar vacío.");
    }
    if (experiencia.getUsuario() == null || experiencia.getUsuario().getId() == null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario no puede estar vacío.");
    }

    Experiencia experienciaGuardada = experienciaServicio.guardarExperiencia(experiencia);
    return ResponseEntity.status(HttpStatus.CREATED).body(experienciaGuardada);
}


    // Recuperar todos las experiencia
    @GetMapping("/experiencias/obtenerTodosLosExperiencia")
    public ResponseEntity<List<Experiencia>> obtenerTodosLosExperiencia() {
        List<Experiencia> experiencia = experienciaServicio.obtenerTodosLosExperiencia();
        return ResponseEntity.ok(experiencia);
    }

    // recuperar experiencia por id
    @GetMapping("/experiencias/recuperarPorId/{id}")
    public ResponseEntity<Experiencia> obtenerExperienciaPorId(@PathVariable Long id) {
        Experiencia experiencia = experienciaServicio.obtenerExperienciaPorId(id);
        return ResponseEntity.ok(experiencia);
    }

    // Actulizar experiencia
    @PutMapping("experiencias/{id}")
    public ResponseEntity<?> actualizarExperiencia(@PathVariable("id") Long id,
            @RequestBody Experiencia experienciaActualizada) {
        try {
            // Verificar si el ID proporcionado en la ruta coincide con el ID del
            // experiencia actualizada
            if (!id.equals(experienciaActualizada.getId())) {
                throw new IllegalArgumentException(
                        "El ID de la experiencia del cuerpo no coincide con el ID proporcionado en la ruta.");
            }
            Experiencia experienciaActual = experienciaServicio.obtenerExperienciaPorId(id);
            if (experienciaActual == null) {
                return new ResponseEntity<>("No se encontró ningun experiencia con el ID proporcionado.",
                        HttpStatus.NOT_FOUND);
            }
            Experiencia experienciaActualizadaGuardada = experienciaServicio
                    .actulizarExperiencia(experienciaActualizada);
            return new ResponseEntity<>(experienciaActualizadaGuardada, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Eliminar experiencia
    @DeleteMapping("/experiencias/{id}")
    public ResponseEntity<String> eliminarExperienciaPorId(@PathVariable Long id) {
        experienciaServicio.eliminarExperiencia(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body("Experiencia con ID " + id + " eliminada correctamente.");
    }
}
