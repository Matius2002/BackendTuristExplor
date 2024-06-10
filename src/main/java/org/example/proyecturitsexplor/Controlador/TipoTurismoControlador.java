package org.example.proyecturitsexplor.Controlador;
import org.example.proyecturitsexplor.Entidades.TipoTurismo;
import org.example.proyecturitsexplor.Repositorios.TipoTurismoRepositorio;
import org.example.proyecturitsexplor.Servicios.TipoTurismoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080")
public class TipoTurismoControlador {

    @Autowired
    private TipoTurismoRepositorio tipoTurismoRepositorio;
    @Autowired
    private TipoTurismoServicio tipoTurismoServicio;

    //CRUD
    @PostMapping("/tipoturismos/guardarTipoTurismos")
    public ResponseEntity<TipoTurismo> guardarTipoTurismo(@RequestBody TipoTurismo tipoTurismo) {
        if (tipoTurismo.getNombre() == null || tipoTurismo.getDescripcion() == null || tipoTurismo.getPopularidad() == null) {
            return ResponseEntity.badRequest().build();
        }
        TipoTurismo tipoTurismoGuardado = tipoTurismoServicio.guardarTipoTurismo(tipoTurismo);
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoTurismoGuardado);
    }

    //recuperar todos los tipo de turismo
    @GetMapping("/tipoturismos/obtenerTodosLosTiposTurismos")
    public ResponseEntity<List<TipoTurismo>> obtenerTodosLosTiposTurismo() {
        List<TipoTurismo> tipoTurismo = tipoTurismoServicio.obtenerTodosLosTiposTurismo();
        return ResponseEntity.ok(tipoTurismo);
    }

    //recuperar tipos de turismo por id
    @GetMapping("/tipoturismos/recuperarPorId/{id}")
    public ResponseEntity<TipoTurismo> obtenerTipoTurismoPorId(@PathVariable Long id) {
        TipoTurismo tipoTurismo = tipoTurismoServicio.obtenerTipoTurismoPorId(id);
        return ResponseEntity.ok(tipoTurismo);
    }

    //actulizar tipo turismo
    @PutMapping("tipoturismos/{id}")
    public ResponseEntity<?> actualizarTipoTurismo(@PathVariable("id") Long id, @RequestBody TipoTurismo tipoTurismoActualizada) {
        try {
            // Verificar si el ID proporcionado en la ruta coincide con el ID del tipo turismo actualizada
            if (!id.equals(tipoTurismoActualizada.getId())) {
                throw new IllegalArgumentException("El ID de la TipoTurismo del cuerpo no coincide con el ID proporcionado en la ruta.");
            }
            TipoTurismo tipoTurismoActual = tipoTurismoServicio.obtenerTipoTurismoPorId(id);
            if (tipoTurismoActual == null) {
                return new ResponseEntity<>("No se encontró ningun TipoTurismo con el ID proporcionado.", HttpStatus.NOT_FOUND);
            }
            TipoTurismo tipoTurismoActualizadaGuardada = tipoTurismoServicio.actualizarTipoTurismo(tipoTurismoActualizada);
            return new ResponseEntity<>(tipoTurismoActualizadaGuardada, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    //Eliminar tipo turismo
    @DeleteMapping("/tipoturismos/{id}")
    public ResponseEntity<String> eliminarTipoTurismoPorId(@PathVariable Long id) {
        tipoTurismoServicio.eliminarTipoTurismo(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("TipoTurismo con ID " + id + " eliminada correctamente.");
    }
    // verificar si un tipo turismo existe en la base de datos
    @GetMapping("/tipoturismos/existe/{nombre}")
    public ResponseEntity<?> verificarTipoTurismoExistente(@PathVariable String nombre) {
        try {
            boolean existe = tipoTurismoServicio.verificarTipoTurismoExistente(nombre);
            return ResponseEntity.ok(existe);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al verificar si el TipoTurismo existe por Nombre: " + e.getMessage());
        }
    }
}
