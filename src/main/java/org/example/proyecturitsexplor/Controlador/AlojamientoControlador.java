package org.example.proyecturitsexplor.Controlador;
import org.example.proyecturitsexplor.Entidades.Alojamiento;
import org.example.proyecturitsexplor.Repositorios.AlojamientoRepositorio;
import org.example.proyecturitsexplor.Servicios.AlojamientoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@Controller
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080")
public class AlojamientoControlador {
    @Autowired
    private AlojamientoRepositorio alojamientoRepositorio;
    @Autowired
    private AlojamientoServicio alojamientoServicio;

    //CRUD
    @PostMapping("/alojamientos/guardarAlojamientos")
    public ResponseEntity<Alojamiento> guardarAlojamiento(@RequestBody Alojamiento alojamiento) {
        if (alojamiento.getNombre() == null || alojamiento.getDescripcion()==null || alojamiento.getEmail() == null || alojamiento.getTipoAlojamiento() == null ||
                alojamiento.getCelular() == null || alojamiento.getDestinos() == null || alojamiento.getDireccion() == null ||
                alojamiento.getFechaActualizacion() == null || alojamiento.getFechaCreacion() == null || alojamiento.getPrecioGeneral() == null ||
                alojamiento.getWebUrl() == null) {
            return ResponseEntity.badRequest().build();
        }
        Alojamiento alojamientoGuardado = alojamientoServicio.guardarAlojamiento(alojamiento);
        return ResponseEntity.status(HttpStatus.CREATED).body(alojamientoGuardado);
    }

    //recuperar todos los alojamientos
    @GetMapping("/alojamientos/obtenerTodosLosAlojamientos")
    public ResponseEntity<List<Alojamiento>>obtenerTodosLosAlojamientos(){
        List<Alojamiento> alojamiento = alojamientoServicio.obtenerTodosLosAlojamientos();
        return ResponseEntity.ok(alojamiento);
    }
    //recuperar alojamientos por id
    @GetMapping("/alojamientos/recuperarPorId/{id}")
    public ResponseEntity<Alojamiento>obtenerAlojamientoPorId(@PathVariable Long id){
        Alojamiento alojamiento = alojamientoServicio.obtenerAlojamientoPorId(id);
        return ResponseEntity.ok(alojamiento);
    }
    //actulizar destino
    @PutMapping("alojamientos/{id}")
    public ResponseEntity<?> actualizarAlojamiento(@PathVariable("id") Long id, @RequestBody Alojamiento alojamientoActualizada) {
        try {
            // Verificar si el ID proporcionado en la ruta coincide con el ID del destino actualizada
            if (!id.equals(alojamientoActualizada.getId())) {
                throw new IllegalArgumentException("El ID de la Destino del cuerpo no coincide con el ID proporcionado en la ruta.");
            }
            Alojamiento alojamientoActual = alojamientoServicio.obtenerAlojamientoPorId(id);
            if (alojamientoActual == null) {
                return new ResponseEntity<>("No se encontró ningun Alojamiento con el ID proporcionado.", HttpStatus.NOT_FOUND);
            }
            Alojamiento alojamientoActualizadaGuardada = alojamientoServicio.actulizarAlojamientos(alojamientoActualizada);
            return new ResponseEntity<>(alojamientoActualizadaGuardada, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    //Eliminar destino
    @DeleteMapping("/alojamientos/{id}")
    public ResponseEntity<String> eliminarDestinoPorId(@PathVariable Long id) {
        alojamientoServicio.eliminarAlojamiento(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Destino con ID " + id + " eliminada correctamente.");
    }
    // verificar si un destino existe en la base de datos
    @GetMapping("/alojamientos/existe/{destinoName}")
    public ResponseEntity<?> verificarAlojamientoExistente(@PathVariable String destinoName) {
        try {
            boolean existe = alojamientoServicio.verificarAlojamientoExistente(destinoName);
            return ResponseEntity.ok(existe);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al verificar si el destino existe por Nombre: " + e.getMessage());
        }
    }
}
