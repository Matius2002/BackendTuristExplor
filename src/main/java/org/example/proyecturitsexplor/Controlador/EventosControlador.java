package org.example.proyecturitsexplor.Controlador;
import org.example.proyecturitsexplor.Entidades.Evento;
import org.example.proyecturitsexplor.Repositorios.EventosRepositorio;
import org.example.proyecturitsexplor.Servicios.EventosServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Controller
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080")
public class EventosControlador {
    @SuppressWarnings("unused")
    @Autowired
    private EventosRepositorio eventosRepositorio;
    @Autowired
    private EventosServicio eventosServicio;

    private static final Logger logger = LoggerFactory.getLogger(EventosControlador.class);

    //@PreAuthorize("hasRole('')")
    //CRUD
    @PostMapping("/eventos/guardarEventos")
    public ResponseEntity<Evento> guardarEventos(@RequestBody Evento evento) {
        logger.info("Datos recibidos para guardar evento: {}", evento.toString());

        // Verifica que la colección de destinos no esté vacía
        if (evento.getDestinos()==null || evento.getNombre() == null || evento.getDescripcion() == null ||
                evento.getFechaInicio() == null || evento.getFechaFin() == null || evento.getUbicacion() == null ||
                evento.getCostoEntrada() == null || evento.getImages() == null || evento.getTipoTurismo()==null) {

            logger.warn("Datos incompletos en la solicitud recibida");
            return ResponseEntity.badRequest().build();
        }

        Evento eventoGuardado = eventosServicio.guardarEvento(evento);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoGuardado);
    }


    //Recuperar todos los evento
    @GetMapping("/eventos/obtenerTodosLosEvento")
    public ResponseEntity<List<Evento>>obtenerTodosLosEventos(){
        List<Evento> eventos = eventosServicio.obtenerTodosLosEventos();
        return ResponseEntity.ok(eventos);
    }
    //recuperar eventos por id
    @GetMapping("/eventos/recuperarPorId/{id}")
    public ResponseEntity<Evento>obtenerEventosPorId(@PathVariable Long id){
        Evento eventos = eventosServicio.obtenerEventosPorId(id);
        return ResponseEntity.ok(eventos);
    }

    //Actulizar evento
    @PutMapping("eventos/{id}")
    public ResponseEntity<?> actualizarEventos(@PathVariable("id") Long id, @RequestBody Evento eventosActualizada) {
        try {
            // Verificar si el ID proporcionado en la ruta coincide con el ID del evento actualizada
            if (!id.equals(eventosActualizada.getId())) {
                throw new IllegalArgumentException("El ID de la evento del cuerpo no coincide con el ID proporcionado en la ruta.");
            }
            Evento eventosActual = eventosServicio.obtenerEventosPorId(id);
            if (eventosActual == null) {
                return new ResponseEntity<>("No se encontró ningun evento con el ID proporcionado.", HttpStatus.NOT_FOUND);
            }
            Evento eventosActualizadaGuardada = eventosServicio.actulizarEventos(eventosActualizada);
            return new ResponseEntity<>(eventosActualizadaGuardada, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //Eliminar evento
    @DeleteMapping("/eventos/{id}")
    public ResponseEntity<String> eliminarEventoPorId(@PathVariable Long id) {
        eventosServicio.eliminarEventos(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Evento con ID " + id + " eliminada correctamente.");
    }

    //Verificar si un evento existe en la base de datos
    @GetMapping("/eventos/existe/{destino}")
    public ResponseEntity<?> verificarEventosExistente(@PathVariable String destino) {
        try {
            boolean existe = eventosServicio.verificarEventosExistente(destino);
            return ResponseEntity.ok(existe);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al verificar si el evento existe por Nombre: " + e.getMessage());
        }
    }
}
