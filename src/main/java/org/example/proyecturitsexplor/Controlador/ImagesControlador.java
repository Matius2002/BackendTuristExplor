package org.example.proyecturitsexplor.Controlador;

import org.example.proyecturitsexplor.Entidades.Images;
import org.example.proyecturitsexplor.Repositorios.ImagesRepositorio;
import org.example.proyecturitsexplor.Servicios.ImagesServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/api")
@CrossOrigin //(origins = "http://localhost:8080")
public class ImagesControlador {
    @SuppressWarnings("unused")
    @Autowired
    private ImagesRepositorio imagesRepositorio;

    @Autowired
    private ImagesServicio imagesServicio;

    private final Path imageDirectory = Paths.get("src/main/resources/static/imagenes");

    // CRUD
    @PreAuthorize("permitAll()")
    @PostMapping("/images/cargar")
    public ResponseEntity<Images> cargarImagen(@RequestParam("archivo") MultipartFile archivo, @RequestParam("nombre") String nombre) {
        try {
            Images imagen = imagesServicio.guardarImagen(archivo, nombre, imageDirectory);
            return ResponseEntity.ok(imagen);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Recuperar todos los Images
    @GetMapping("/images/obtenerTodosLosImages")
    public ResponseEntity<List<Images>> obtenerTodosLosImages() {
        List<Images> images = imagesServicio.obtenerTodosLosImages();
        return ResponseEntity.ok(images);
    }

    // Recuperar Images por id
    @GetMapping("/images/recuperarPorId/{id}")
    public ResponseEntity<Images> obtenerImagesPorId(@PathVariable Long id) {
        Images images = imagesServicio.obtenerImagesPorId(id);
        return ResponseEntity.ok(images);
    }

    // Actualizar Images
    @PutMapping("/images/{id}")
    public ResponseEntity<?> actualizarImages(
            @PathVariable("id") Long id,
            @RequestParam(value = "archivo", required = false) MultipartFile archivo,
            @RequestParam(value = "nombre", required = false) String nombre) {
        try {
            Images imagesActual = imagesServicio.obtenerImagesPorId(id);
            if (imagesActual == null) {
                return new ResponseEntity<>("No se encontró ningún Images con el ID proporcionado.", HttpStatus.NOT_FOUND);
            }
            // Actualizar solo si se proporciona un archivo nuevo
            if (archivo != null && !archivo.isEmpty()) {
                Images imagenActualizada = imagesServicio.guardarImagen(archivo, nombre, imageDirectory);
                return ResponseEntity.ok(imagenActualizada);
            } else {
                // Si no se proporciona un archivo nuevo, actualizar solo los metadatos
                imagesActual.setNombre(nombre);
                imagesActual.setActiva(true);

                Images imagesActualizadaGuardada = imagesServicio.actulizarImages(imagesActual);
                return new ResponseEntity<>(imagesActualizadaGuardada, HttpStatus.OK);
            }
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Eliminar Images
    @DeleteMapping("/images/{id}")
    public ResponseEntity<String> eliminarImagesPorId(@PathVariable Long id) {
        imagesServicio.eliminarImages(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Images con ID " + id + " eliminada correctamente.");
    }

    // Verificar si un Images existe en la base de datos
    @GetMapping("/images/existe/{nombre}")
    public ResponseEntity<?> verificarImagesExistente(@PathVariable String nombre) {
        try {
            boolean existe = imagesServicio.verificarImageExistente(nombre);
            return ResponseEntity.ok(existe);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al verificar si el Images existe por Nombre: " + e.getMessage());
        }
    }

    // Servir imágenes desde el directorio
    @GetMapping("/imagenes/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            Path file = imageDirectory.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
