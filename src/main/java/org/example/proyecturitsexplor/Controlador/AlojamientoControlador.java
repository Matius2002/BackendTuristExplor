package org.example.proyecturitsexplor.Controlador;

// Importación de clases y paquetes necesarios
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

@Controller
@RequestMapping("/api") // Define la ruta base para todos los endpoints de este controlador
@CrossOrigin(origins = "http://localhost:8080") // Permite solicitudes desde el origen especificado (CORS)
public class AlojamientoControlador {

    // Inyección de dependencias mediante @Autowired para acceder al repositorio y servicio de alojamientos
    @SuppressWarnings("unused")
    @Autowired
    private AlojamientoRepositorio alojamientoRepositorio;

    @Autowired
    private AlojamientoServicio alojamientoServicio;

    // Permite que cualquier usuario acceda a este endpoint, sin restricciones de autorización
    @PreAuthorize("permitAll()")
    @PostMapping("/alojamientos/guardarAlojamientos") // Define el endpoint para guardar un alojamiento
    public ResponseEntity<Alojamiento> guardarAlojamiento(@RequestBody Alojamiento alojamiento) {
        // Imprime los detalles del alojamiento recibido y sus imágenes en la consola
        System.out.println("Alojamiento recibido: " + alojamiento);
        System.out.println("Imágenes recibidas: " + alojamiento.getImagenes());

        // Validación de campos obligatorios; si alguno está nulo, devuelve un error 400 (Bad Request)
        if (alojamiento.getNombre() == null || alojamiento.getDescripcion() == null || alojamiento.getEmail() == null ||
                alojamiento.getTipoAlojamiento() == null || alojamiento.getCelular() == null || alojamiento.getDestinos() == null ||
                alojamiento.getDireccion() == null || alojamiento.getFechaCreacion() == null || alojamiento.getPrecioGeneral() == null ||
                alojamiento.getWebUrl() == null || alojamiento.getImagenes() == null) {
            return ResponseEntity.badRequest().build();
        }

        // Guarda el alojamiento utilizando el servicio y devuelve una respuesta con el alojamiento guardado y el estado 201 (Created)
        Alojamiento alojamientoGuardado = alojamientoServicio.guardarAlojamiento(alojamiento);
        return ResponseEntity.status(HttpStatus.CREATED).body(alojamientoGuardado);
    }

    // Endpoint para obtener todos los alojamientos
    @GetMapping("/alojamientos/obtenerTodosLosAlojamientos")
    public ResponseEntity<List<Alojamiento>> obtenerTodosLosAlojamientos() {
        // Llama al servicio para obtener todos los alojamientos y los devuelve con el estado 200 (OK)
        List<Alojamiento> alojamiento = alojamientoServicio.obtenerTodosLosAlojamientos();
        return ResponseEntity.ok(alojamiento);
    }

    // Endpoint para obtener un alojamiento por su ID
    @GetMapping("/alojamientos/recuperarPorId/{id}")
    public ResponseEntity<Alojamiento> obtenerAlojamientoPorId(@PathVariable Long id) {
        // Llama al servicio para obtener un alojamiento por su ID y lo devuelve con el estado 200 (OK)
        Alojamiento alojamiento = alojamientoServicio.obtenerAlojamientoPorId(id);
        return ResponseEntity.ok(alojamiento);
    }

    // Endpoint para actualizar un alojamiento por su ID
    @PutMapping("alojamientos/{id}")
    public ResponseEntity<?> actualizarAlojamiento(@PathVariable("id") Long id, @RequestBody Alojamiento alojamientoActualizada) {
        try {
            // Verifica si el ID de la ruta coincide con el ID del cuerpo de la solicitud; si no, lanza una excepción
            if (!id.equals(alojamientoActualizada.getId())) {
                throw new IllegalArgumentException("El ID de la Destino del cuerpo no coincide con el ID proporcionado en la ruta.");
            }

            // Busca el alojamiento actual por su ID; si no se encuentra, devuelve un error 404 (Not Found)
            Alojamiento alojamientoActual = alojamientoServicio.obtenerAlojamientoPorId(id);
            if (alojamientoActual == null) {
                return new ResponseEntity<>("No se encontró ningun Alojamiento con el ID proporcionado.", HttpStatus.NOT_FOUND);
            }

            // Actualiza el alojamiento y devuelve el alojamiento actualizado con el estado 200 (OK)
            Alojamiento alojamientoActualizadaGuardada = alojamientoServicio.actulizarAlojamientos(alojamientoActualizada);
            return new ResponseEntity<>(alojamientoActualizadaGuardada, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            // Devuelve un error 400 (Bad Request) si ocurre una excepción con un mensaje de error
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint para eliminar un alojamiento por su ID
    @DeleteMapping("/alojamientos/{id}")
    public ResponseEntity<String> eliminarDestinoPorId(@PathVariable Long id) {
        // Llama al servicio para eliminar el alojamiento y devuelve un mensaje de éxito con el estado 204 (No Content)
        alojamientoServicio.eliminarAlojamiento(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Destino con ID " + id + " eliminada correctamente.");
    }

    // Endpoint para verificar si un alojamiento existe por su nombre
    @GetMapping("/alojamientos/existe/{destinoName}")
    public ResponseEntity<?> verificarAlojamientoExistente(@PathVariable String destinoName) {
        try {
            // Verifica si el alojamiento existe utilizando el servicio y devuelve el resultado
            boolean existe = alojamientoServicio.verificarAlojamientoExistente(destinoName);
            return ResponseEntity.ok(existe);
        } catch (Exception e) {
            // Devuelve un error 500 (Internal Server Error) si ocurre una excepción durante la verificación
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al verificar si el destino existe por Nombre: " + e.getMessage());
        }
    }
}
