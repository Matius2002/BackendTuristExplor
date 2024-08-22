package org.example.proyecturitsexplor.Controlador;
import org.example.proyecturitsexplor.Entidades.Destinos;
import org.example.proyecturitsexplor.Repositorios.DestinosRepositorio;
import org.example.proyecturitsexplor.Servicios.DestinosServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@Controller
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080")
public class DestinosControlador {

    @Autowired
    private DestinosRepositorio destinosRepositorio;
    @Autowired
    private DestinosServicio destinosServicio;

    //CRUD
    @PostMapping("/destinos/guardarDestinos")
    public ResponseEntity<Destinos> guardarDestinos(@RequestBody Destinos destinos) {
        if (destinos.getDestinoName() == null || destinos.getDescripcion() == null ||
                destinos.getUbicacion() == null || destinos.getFechaCreacion() == null ||
                destinos.getFechaActualizacion() == null ||
                destinos.getAtracionesPrincipales() == null ||
                destinos.getImagenes() == null || destinos.getTipoTurismo() == null ||
                destinos.getEpocasVisitar() == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            Destinos destinosGuardado = destinosServicio.guardarDestino(destinos);
            return ResponseEntity.status(HttpStatus.CREATED).body(destinosGuardado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //recuperar todos los destinos
    @GetMapping("/destinos/obtenerTodosLosDestinos")
    public ResponseEntity<List<Destinos>>obtenerTodosLosDestinos(){
        List<Destinos> destinos = destinosServicio.obtenerTodosLosDestinos();
            return ResponseEntity.ok(destinos);
    }
    //recuperar destinos por id
    @GetMapping("/destinos/recuperarPorId/{id}")
        public ResponseEntity<Destinos>obtenerDestinosPorId(@PathVariable Long id){
            Destinos destinos = destinosServicio.obtenerDestinosPorId(id);
                return ResponseEntity.ok(destinos);
    }
    //actulizar destino
    @PutMapping("/destinos/{id}")
    public ResponseEntity<?> actualizarDestinos(@PathVariable("id") Long id, @RequestBody Destinos destinosActualizada) {
        try {
            // Verificar si el ID proporcionado en la ruta coincide con el ID del destino actualizada
            if (!id.equals(destinosActualizada.getId())) {
                throw new IllegalArgumentException("El ID de la Destino del cuerpo no coincide con el ID proporcionado en la ruta.");
            }
            Destinos destinosActual = destinosServicio.obtenerDestinosPorId(id);
            if (destinosActual == null) {
                return new ResponseEntity<>("No se encontró ningun Destino con el ID proporcionado.", HttpStatus.NOT_FOUND);
            }
            Destinos destinosActualizadaGuardada = destinosServicio.actulizarDestinos(destinosActualizada);
            return new ResponseEntity<>(destinosActualizadaGuardada, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    //Eliminar destino
    @DeleteMapping("/destinos/{id}")
    public ResponseEntity<String> eliminarDestinoPorId(@PathVariable Long id) {
        destinosServicio.eliminarDestino(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Destino con ID " + id + " eliminada correctamente.");
    }
    // verificar si un destino existe en la base de datos
    @GetMapping("/destinos/existe/{destinoName}")
    public ResponseEntity<?> verificarDestinosExistente(@PathVariable String destinoName) {
        try {
            boolean existe = destinosServicio.verificarDestinoExistente(destinoName);
            return ResponseEntity.ok(existe);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al verificar si el destino existe por Nombre: " + e.getMessage());
        }
    }
}
