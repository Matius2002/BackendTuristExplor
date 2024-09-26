package org.example.proyecturitsexplor.Controlador;
import org.example.proyecturitsexplor.Entidades.EpocaVisitar;
import org.example.proyecturitsexplor.Repositorios.EpocaVisitarRepositorio;
import org.example.proyecturitsexplor.Servicios.EpocaVisitarServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/api")
@CrossOrigin //(origins = "http://localhost:8080")
public class EpocaVisitarControlador {
    @SuppressWarnings("unused")
    @Autowired
    private EpocaVisitarRepositorio epocaVisitarRepositorio;
    @Autowired
    private EpocaVisitarServicio epocaVisitarServicio;

    //CRUD
    @PostMapping("/epocaVisitars/guardarEpocaVisitar")
    public ResponseEntity<EpocaVisitar> guardarEpocaVisitar(@RequestBody EpocaVisitar epocaVisitar) {
        if (epocaVisitar.getNombre()==null || epocaVisitar.getDescripcion()==null || epocaVisitar.getClima()==null){
            return  ResponseEntity.badRequest().build();
        }
        EpocaVisitar epocaVisitarGuardado = epocaVisitarServicio.guardarEpocaVisitar(epocaVisitar);
        return ResponseEntity.status(HttpStatus.CREATED).body(epocaVisitarGuardado);
    }

    //Recuperar todos las epocas a visitar
    @GetMapping("/epocaVisitars/obtenerTodosLosEpocaVisitar")
    public ResponseEntity<List<EpocaVisitar>>obtenerTodosLosEpocaVisitar(){
        List<EpocaVisitar> epocaVisitar = epocaVisitarServicio.obtenerTodosLosEpocaVisitar();
        return ResponseEntity.ok(epocaVisitar);
    }
    //recuperar epocas visitar por id
    @GetMapping("/epocaVisitars/recuperarPorId/{id}")
    public ResponseEntity<EpocaVisitar>obtenerEpocaVisitarPorId(@PathVariable Long id){
        EpocaVisitar epocaVisitar = epocaVisitarServicio.obtenerEpocaVisitarPorId(id);
        return ResponseEntity.ok(epocaVisitar);
    }

    //Actulizar epoca de visitar
    @PutMapping("epocaVisitars/{id}")
    public ResponseEntity<?> actualizarEpocaVisitar(@PathVariable("id") Long id, @RequestBody EpocaVisitar epocaVisitarActualizada) {
        try {
            // Verificar si el ID proporcionado en la ruta coincide con el ID del EpocaVisitar actualizada
            if (!id.equals(epocaVisitarActualizada.getId())) {
                throw new IllegalArgumentException("El ID de la EpocaVisitar del cuerpo no coincide con el ID proporcionado en la ruta.");
            }
            EpocaVisitar epocaVisitarActual = epocaVisitarServicio.obtenerEpocaVisitarPorId(id);
            if (epocaVisitarActual == null) {
                return new ResponseEntity<>("No se encontró ningun EpocaVisitar con el ID proporcionado.", HttpStatus.NOT_FOUND);
            }
            EpocaVisitar epocaVisitarActualizadaGuardada = epocaVisitarServicio.actulizarEpocaVisitar(epocaVisitarActualizada);
            return new ResponseEntity<>(epocaVisitarActualizadaGuardada, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //Eliminar EpocaVisitar
    @DeleteMapping("/epocaVisitars/{id}")
    public ResponseEntity<String> eliminarEpocaVisitarPorId(@PathVariable Long id) {
        epocaVisitarServicio.eliminarEpocaVisitar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("EpocaVisitar con ID " + id + " eliminada correctamente.");
    }

    //Verificar si un EpocaVisitar existe en la base de datos
    @GetMapping("/epocaVisitars/existe/{nombre}")
    public ResponseEntity<?> verificarEpocaVisitarExistente(@PathVariable String nombre) {
        try {
            boolean existe = epocaVisitarServicio.verificarEpocaVisitarExistente(nombre);
            return ResponseEntity.ok(existe);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al verificar si la epoca visitar existe por Nombre: " + e.getMessage());
        }
    }
}
