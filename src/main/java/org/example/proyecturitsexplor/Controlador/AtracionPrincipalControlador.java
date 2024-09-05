package org.example.proyecturitsexplor.Controlador; // Paquete donde se ubica el controlador

/* Importaciones necesarias para el funcionamiento del controlador */
import org.example.proyecturitsexplor.Entidades.AtracionPrincipal;
import org.example.proyecturitsexplor.Repositorios.AtracionPrincipalRepositorio;
import org.example.proyecturitsexplor.Servicios.AtracionPrincipalServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/* Anotaciones del controlador */
@Controller
@RequestMapping("/api") // Define la ruta base para todos los endpoints de este controlador
@CrossOrigin(origins = "http://localhost:8080") // Permite solicitudes desde el origen especificado (CORS)
public class AtracionPrincipalControlador {
    
    /* Inyección de dependencias */
    @SuppressWarnings("unused")
    @Autowired
    private AtracionPrincipalRepositorio atracionPrincipalRepositorio;

    @Autowired
    private AtracionPrincipalServicio atracionPrincipalServicio;

    // CRUD: Endpoint para guardar una atracción principal
    @PostMapping("/atracionesPrincipales/guardarAtracionPrincipal")
    public ResponseEntity<AtracionPrincipal> guardarAtracionPrincipal(@RequestBody AtracionPrincipal atracionPrincipal) {
        // Validación de campos obligatorios; si alguno está nulo, devuelve un error 400 (Bad Request)
        if (atracionPrincipal.getNombre() == null || atracionPrincipal.getHorarioFuncionamiento() == null ||
                atracionPrincipal.getDescripcion() == null || atracionPrincipal.getHorarioFin() == null) {
            return ResponseEntity.badRequest().build();
        }
        // Guarda la atracción principal utilizando el servicio y devuelve una respuesta con la atracción guardada y el estado 201 (Created)
        AtracionPrincipal atracionPrincipalGuardado = atracionPrincipalServicio.guardarAtracionPrincipal(atracionPrincipal);
        return ResponseEntity.status(HttpStatus.CREATED).body(atracionPrincipalGuardado);
    }

    // Endpoint para recuperar todas las atracciones principales
    @GetMapping("/atracionesPrincipales/obtenerTodosLasAtraciones")
    public ResponseEntity<List<AtracionPrincipal>> obtenerTodosLosAtracionPrincipal() {
        // Llama al servicio para obtener todas las atracciones principales y las devuelve con el estado 200 (OK)
        List<AtracionPrincipal> atracionPrincipal = atracionPrincipalServicio.obtenerTodosLosAtracionPrincipal();
        return ResponseEntity.ok(atracionPrincipal);
    }

    // Endpoint para recuperar una atracción principal por su ID
    @GetMapping("/atracionesPrincipales/recuperarPorId/{id}")
    public ResponseEntity<AtracionPrincipal> obtenerAtracionPrincipalPorId(@PathVariable Long id) {
        // Llama al servicio para obtener una atracción principal por su ID y la devuelve con el estado 200 (OK)
        AtracionPrincipal atracionPrincipal = atracionPrincipalServicio.obtenerAtracionPrincipalPorId(id);
        return ResponseEntity.ok(atracionPrincipal);
    }

    // Endpoint para actualizar una atracción principal por su ID
    @PutMapping("/atracionesPrincipales/{id}")
    public ResponseEntity<?> actualizarAtracionPrincipal(@PathVariable("id") Long id, @RequestBody AtracionPrincipal atracionPrincipalActualizada) {
        try {
            // Verificar si el ID proporcionado en la ruta coincide con el ID de la atracción principal actualizada
            if (!id.equals(atracionPrincipalActualizada.getId())) {
                throw new IllegalArgumentException("El ID de la atracion principal del cuerpo no coincide con el ID proporcionado en la ruta.");
            }

            // Busca la atracción principal actual por su ID; si no se encuentra, devuelve un error 404 (Not Found)
            AtracionPrincipal atracionPrincipalActual = atracionPrincipalServicio.obtenerAtracionPrincipalPorId(id);
            if (atracionPrincipalActual == null) {
                return new ResponseEntity<>("No se encontró ningun atracion principal con el ID proporcionado.", HttpStatus.NOT_FOUND);
            }

            // Actualiza la atracción principal y devuelve la atracción actualizada con el estado 200 (OK)
            AtracionPrincipal atracionActualizadaGuardada = atracionPrincipalServicio.actulizarAtracionPrincipal(atracionPrincipalActualizada);
            return new ResponseEntity<>(atracionActualizadaGuardada, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            // Devuelve un error 400 (Bad Request) si ocurre una excepción con un mensaje de error
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint para eliminar una atracción principal por su ID
    @DeleteMapping("/atracionesPrincipales/{id}")
    public ResponseEntity<String> eliminarAtracionPrincipalPorId(@PathVariable Long id) {
        // Llama al servicio para eliminar la atracción principal y devuelve un mensaje de éxito con el estado 204 (No Content)
        atracionPrincipalServicio.eliminarAtracionPrincipal(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Atracion Principal con ID " + id + " eliminada correctamente.");
    }

    // Endpoint para verificar si una atracción principal existe en la base de datos por su nombre
    @GetMapping("/atracionesPrincipales/existe/{nombre}")
    public ResponseEntity<?> verificarAtracionPrincipalExistente(@PathVariable String nombre) {
        try {
            // Verifica si la atracción principal existe utilizando el servicio y devuelve el resultado
            boolean existe = atracionPrincipalServicio.verificarAtracionPrincipalExistente(nombre);
            return ResponseEntity.ok(existe);
        } catch (Exception e) {
            // Devuelve un error 500 (Internal Server Error) si ocurre una excepción durante la verificación
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al verificar si el atracion principal existe por Nombre: " + e.getMessage());
        }
    }
}
