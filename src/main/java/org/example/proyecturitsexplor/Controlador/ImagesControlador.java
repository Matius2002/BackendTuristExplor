package org.example.proyecturitsexplor.Controlador;
import org.example.proyecturitsexplor.Entidades.Images;
import org.example.proyecturitsexplor.Repositorios.ImagesRepositorio;
import org.example.proyecturitsexplor.Servicios.ImagesServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
@Controller
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080")
public class ImagesControlador {
    @Autowired
    private ImagesRepositorio imagesRepositorio;
    @Autowired
    private ImagesServicio imagesServicio;

    //CRUD

    @PostMapping("/images/cargar")
    public ResponseEntity<Images> cargarImagen(@RequestParam("archivo") MultipartFile archivo, @RequestParam("nombre") String nombre) {
        try {
            Images imagen = imagesServicio.guardarImagen(archivo, nombre);
            return ResponseEntity.ok(imagen);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


//    @PostMapping("/images/guardarImages")
//    public ResponseEntity<Images> guardarImages(@RequestBody Images Images) {
//        if (Images.getNombre()==null || Images.getRuta()==null){
//            return  ResponseEntity.badRequest().build();
//        }
//        Images ImagesGuardado = imagesServicio.guardarImages(Images);
//        return ResponseEntity.status(HttpStatus.CREATED).body(ImagesGuardado);
//    }

    //Recuperar todos los Images
    @GetMapping("/images/obtenerTodosLosImages")
    public ResponseEntity<List<Images>>obtenerTodosLosImages(){
        List<Images> Images = imagesServicio.obtenerTodosLosImages();
        return ResponseEntity.ok(Images);
    }
    //recuperar Images por id
    @GetMapping("/images/recuperarPorId/{id}")
    public ResponseEntity<Images>obtenerImagesPorId(@PathVariable Long id){
        Images Images = imagesServicio.obtenerImagesPorId(id);
        return ResponseEntity.ok(Images);
    }

    //Actulizar Images
    @PutMapping("images/{id}")
    public ResponseEntity<?> actualizarImages(@PathVariable("id") Long id, @RequestBody Images ImagesActualizada) {
        try {
            // Verificar si el ID proporcionado en la ruta coincide con el ID del Images actualizada
            if (!id.equals(ImagesActualizada.getId())) {
                throw new IllegalArgumentException("El ID de la Images del cuerpo no coincide con el ID proporcionado en la ruta.");
            }
            Images ImagesActual = imagesServicio.obtenerImagesPorId(id);
            if (ImagesActual == null) {
                return new ResponseEntity<>("No se encontró ningun Images con el ID proporcionado.", HttpStatus.NOT_FOUND);
            }
            Images ImagesActualizadaGuardada = imagesServicio.actulizarImages(ImagesActualizada);
            return new ResponseEntity<>(ImagesActualizadaGuardada, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //Eliminar Images
    @DeleteMapping("/images/{id}")
    public ResponseEntity<String> eliminarImagesPorId(@PathVariable Long id) {
        imagesServicio.eliminarImages(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Images con ID " + id + " eliminada correctamente.");
    }

    //Verificar si un Images existe en la base de datos
    @GetMapping("/images/existe/{nombre}")
    public ResponseEntity<?> verificarImagesExistente(@PathVariable String nombre) {
        try {
            boolean existe = imagesServicio.verificarImageExistente(nombre);
            return ResponseEntity.ok(existe);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al verificar si el Images existe por Nombre: " + e.getMessage());
        }
    }
}
