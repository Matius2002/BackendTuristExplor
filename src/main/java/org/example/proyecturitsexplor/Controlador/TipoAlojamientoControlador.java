package org.example.proyecturitsexplor.Controlador;

import org.example.proyecturitsexplor.Entidades.TipoAlojamiento;
import org.example.proyecturitsexplor.Repositorios.TipoAlojamientoRepositorio;
import org.example.proyecturitsexplor.Servicios.TipoAlojamientoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api")
@CrossOrigin //(origins = "http://localhost:8080")
public class TipoAlojamientoControlador {

    @SuppressWarnings("unused")
    @Autowired
    private TipoAlojamientoRepositorio tipoAlojamientoRepositorio;
    @Autowired
    private TipoAlojamientoServicio tipoAlojamientoServicio;

    //CRUD
    @PostMapping("/tipoAlojamientos/guardarTipoAlojamientos")
    public ResponseEntity<TipoAlojamiento> guardarTipoAlojamiento(@RequestBody TipoAlojamiento tipoAlojamiento) {
        if (tipoAlojamiento.getNombre()==null || tipoAlojamiento.getDescripcion()==null || tipoAlojamiento.getServicios()==null || (tipoAlojamiento.getPrecioPromedio()==null)){
            return  ResponseEntity.badRequest().build();
        }
        TipoAlojamiento tipoAlojamientoGuardado = tipoAlojamientoServicio.guardarTipoAlojamiento(tipoAlojamiento);
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoAlojamientoGuardado);
    }
    //recuperar todos los tipos alojamientos
    @GetMapping("/tipoAlojamientos/obtenerTodosLosTiposAlojamientos")
    public ResponseEntity<List<TipoAlojamiento>>obtenerTodosLosTipoAlojamientos(){
        List<TipoAlojamiento> tipoAlojamientos = tipoAlojamientoServicio.obtenerTodosLosTipoAlojamientos();
        return ResponseEntity.ok(tipoAlojamientos);
    }
    //recuperar tipos alojamientos por id
    @GetMapping("/tipoAlojamientos/recuperarPorId/{id}")
    public ResponseEntity<TipoAlojamiento>obtenerTipoAlojamientoPorId(@PathVariable Long id){
        TipoAlojamiento tipoAlojamientos = tipoAlojamientoServicio.obtenerTipoAlojamientoPorId(id);
        return ResponseEntity.ok(tipoAlojamientos);
    }
    //actulizar tipo alojamiento
    @PutMapping("tipoAlojamientos/{id}")
    public ResponseEntity<?> actualizarTipoAlojamiento(@PathVariable("id") Long id, @RequestBody TipoAlojamiento tipoAlojamientoActualizada) {
        try {
            // Verificar si el ID proporcionado en la ruta coincide con el ID del tipo alojamiento actualizada
            if (!id.equals(tipoAlojamientoActualizada.getId())) {
                throw new IllegalArgumentException("El ID de la Tipo Alojamiento del cuerpo no coincide con el ID proporcionado en la ruta.");
            }
            TipoAlojamiento tipoAlojamientoActual = tipoAlojamientoServicio.obtenerTipoAlojamientoPorId(id);
            if (tipoAlojamientoActual == null) {
                return new ResponseEntity<>("No se encontró ningun Tipo Alojamiento con el ID proporcionado.", HttpStatus.NOT_FOUND);
            }
            TipoAlojamiento tipoAlojamientoActualizadaGuardada = tipoAlojamientoServicio.actulizarTipoAlojamiento(tipoAlojamientoActualizada);
            return new ResponseEntity<>(tipoAlojamientoActualizadaGuardada, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    //Eliminar tipo alojamiento
    @DeleteMapping("/tipoAlojamientos/{id}")
    public ResponseEntity<String> eliminarTipoAlojamientoPorId(@PathVariable Long id) {
        tipoAlojamientoServicio.eliminarTipoAlojamiento(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Tipo Alojamiento con ID " + id + " eliminada correctamente.");
    }
    // verificar si un tipo alojamiento existe en la base de datos
    @GetMapping("/tipoAlojamientos/existe/{nombre}")
    public ResponseEntity<?> verificarTipoAlojamientoExistente(@PathVariable String nombre) {
        try {
            boolean existe = tipoAlojamientoServicio.verificarTipoAlojamientoExistente(nombre);
            return ResponseEntity.ok(existe);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al verificar si el tipo alojamiento existe por Nombre: " + e.getMessage());
        }
    }
}
