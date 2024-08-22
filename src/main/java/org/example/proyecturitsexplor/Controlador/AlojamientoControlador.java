/*Objetivo:  es un controlador de Spring Boot que expone diversas API REST para gestionar alojamientos. Permite realizar operaciones 
CRUD, así como verificar la existencia de alojamientos. Utiliza anotaciones de Spring como @Controller, @RequestMapping, y @Autowired 
para manejar las solicitudes y las dependencias, asegurando así una estructura bien organizada y mantenible*/
package org.example.proyecturitsexplor.Controlador; /*Paquete*/

/*Importaciones*/
import org.example.proyecturitsexplor.Entidades.Alojamiento;
import org.example.proyecturitsexplor.Repositorios.AlojamientoRepositorio;
import org.example.proyecturitsexplor.Servicios.AlojamientoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/*Anotaciones del controlador*/
@Controller
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080")
public class AlojamientoControlador {
    /*Inyección de dependencias*/
    @Autowired
    private AlojamientoRepositorio alojamientoRepositorio;
    @Autowired
    private AlojamientoServicio alojamientoServicio;

    //CRUD
    /*Guardar un alojamiento*/
    @PreAuthorize("permitAll()")
    @PostMapping("/alojamientos/guardarAlojamientos")
    public ResponseEntity<Alojamiento> guardarAlojamiento(@RequestBody Alojamiento alojamiento) {
        System.out.println("Alojamiento recibido: " + alojamiento);
        System.out.println("Imágenes recibidas: " + alojamiento.getImagenes());

        if (alojamiento.getNombre() == null || alojamiento.getDescripcion() == null || alojamiento.getEmail() == null ||
                alojamiento.getTipoAlojamiento() == null || alojamiento.getCelular() == null || alojamiento.getDestinos() == null ||
                alojamiento.getDireccion() == null || alojamiento.getFechaCreacion() == null || alojamiento.getPrecioGeneral() == null ||
                alojamiento.getWebUrl() == null || alojamiento.getImagenes() == null) {
            return ResponseEntity.badRequest().build();
        }

        Alojamiento alojamientoGuardado = alojamientoServicio.guardarAlojamiento(alojamiento);
        return ResponseEntity.status(HttpStatus.CREATED).body(alojamientoGuardado);
    }

    //Obtener todos los alojamientos
    @GetMapping("/alojamientos/obtenerTodosLosAlojamientos")
    public ResponseEntity<List<Alojamiento>>obtenerTodosLosAlojamientos(){
        List<Alojamiento> alojamiento = alojamientoServicio.obtenerTodosLosAlojamientos();
        return ResponseEntity.ok(alojamiento);
    }

    //Obtener alojamientos por id
    @GetMapping("/alojamientos/recuperarPorId/{id}")
    public ResponseEntity<Alojamiento>obtenerAlojamientoPorId(@PathVariable Long id){
        Alojamiento alojamiento = alojamientoServicio.obtenerAlojamientoPorId(id);
        return ResponseEntity.ok(alojamiento);
    }
    //Actualizar un alojamiento
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

    //Eliminar un Alojamiento
    @DeleteMapping("/alojamientos/{id}")
    public ResponseEntity<String> eliminarDestinoPorId(@PathVariable Long id) {
        alojamientoServicio.eliminarAlojamiento(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Destino con ID " + id + " eliminada correctamente.");
    }

    // verificar si un alojamiento existe en la base de datos
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
