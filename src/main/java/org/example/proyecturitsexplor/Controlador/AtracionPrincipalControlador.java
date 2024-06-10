package org.example.proyecturitsexplor.Controlador;
import org.example.proyecturitsexplor.Entidades.AtracionPrincipal;
import org.example.proyecturitsexplor.Repositorios.AtracionPrincipalRepositorio;
import org.example.proyecturitsexplor.Servicios.AtracionPrincipalServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080")
public class AtracionPrincipalControlador {
    @Autowired
    private AtracionPrincipalRepositorio atracionPrincipalRepositorio;
    @Autowired
    private AtracionPrincipalServicio atracionPrincipalServicio;

    //CRUD
    @PostMapping("/atracionesPrincipales/guardarAtracionPrincipal")
    public ResponseEntity<AtracionPrincipal> guardarAtracionPrincipal(@RequestBody AtracionPrincipal atracionPrincipal) {
        if (atracionPrincipal.getNombre()==null || atracionPrincipal.getHorarioFuncionamiento()==null ||
                atracionPrincipal.getDescripcion()==null || atracionPrincipal.getHorarioFin()==null){
            return  ResponseEntity.badRequest().build();
        }
        AtracionPrincipal atracionPrincipalGuardado = atracionPrincipalServicio.guardarAtracionPrincipal(atracionPrincipal);
        return ResponseEntity.status(HttpStatus.CREATED).body(atracionPrincipalGuardado);
    }

    //Recuperar todos las atraciones principales
    @GetMapping("/atracionesPrincipales/obtenerTodosLasAtraciones")
    public ResponseEntity<List<AtracionPrincipal>>obtenerTodosLosAtracionPrincipal(){
        List<AtracionPrincipal> atracionPrincipal = atracionPrincipalServicio.obtenerTodosLosAtracionPrincipal();
        return ResponseEntity.ok(atracionPrincipal);
    }

    //Recuperar atracion principal por id
    @GetMapping("/atracionesPrincipales/recuperarPorId/{id}")
    public ResponseEntity<AtracionPrincipal>obtenerAtracionPrincipalPorId(@PathVariable Long id){
        AtracionPrincipal atracionPrincipal = atracionPrincipalServicio.obtenerAtracionPrincipalPorId(id);
        return ResponseEntity.ok(atracionPrincipal);
    }

    //Actulizar atracion principal
    @PutMapping("/atracionesPrincipales/{id}")
    public ResponseEntity<?> actualizarAtracionPrincipal(@PathVariable("id") Long id, @RequestBody AtracionPrincipal atracionPrincipalActualizada) {
        try {
            // Verificar si el ID proporcionado en la ruta coincide con el ID de la atracion principal actualizada
            if (!id.equals(atracionPrincipalActualizada.getId())) {
                throw new IllegalArgumentException("El ID de la atracion principal del cuerpo no coincide con el ID proporcionado en la ruta.");
            }
            AtracionPrincipal atracionPrincipalActual = atracionPrincipalServicio.obtenerAtracionPrincipalPorId(id);
            if (atracionPrincipalActual == null) {
                return new ResponseEntity<>("No se encontró ningun atracion principal con el ID proporcionado.", HttpStatus.NOT_FOUND);
            }
            AtracionPrincipal atracionActualizadaGuardada = atracionPrincipalServicio.actulizarAtracionPrincipal(atracionPrincipalActualizada);
            return new ResponseEntity<>(atracionActualizadaGuardada, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //Eliminar Atracion Principal
    @DeleteMapping("/atracionesPrincipales/{id}")
    public ResponseEntity<String> eliminarAtracionPrincipalPorId(@PathVariable Long id) {
        atracionPrincipalServicio.eliminarAtracionPrincipal(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Atracion Principal con ID " + id + " eliminada correctamente.");
    }

    //Verificar si una atracion principal existe en la base de datos
    @GetMapping("/atracionesPrincipales/existe/{nombre}")
    public ResponseEntity<?> verificarAtracionPrincipalExistente(@PathVariable String nombre) {
        try {
            boolean existe = atracionPrincipalServicio.verificarAtracionPrincipalExistente(nombre);
            return ResponseEntity.ok(existe);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al verificar si el atracion principal existe por Nombre: " + e.getMessage());
        }
    }
}
