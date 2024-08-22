package org.example.proyecturitsexplor.Servicios;
import org.example.proyecturitsexplor.Entidades.Images;
import org.example.proyecturitsexplor.Excepciones.ImagesNotFoundException;
import org.example.proyecturitsexplor.Repositorios.ImagesRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
@Service
public class ImagesServicio {
    @Autowired
    private ImagesRepositorio imagesRepositorio;

    public ImagesServicio(ImagesRepositorio imagesRepositorio) {
        this.imagesRepositorio = imagesRepositorio;
    }

    // CRUD
    public Images guardarImagen(MultipartFile archivo, String nombre, Path directorioPath) throws IOException {
        // Verificar si el directorio existe, si no, crearlo
        if (!Files.exists(directorioPath)) {
            Files.createDirectories(directorioPath);
        }

        // Verificar si el archivo está vacío
        if (archivo.isEmpty()) {
            throw new IOException("El archivo está vacío");
        }

        // Obtener la extensión del archivo original
        String extension = archivo.getOriginalFilename().substring(archivo.getOriginalFilename().lastIndexOf('.'));

        // Definir la ruta completa donde se guardará el archivo con el nuevo nombre
        Path rutaArchivo = directorioPath.resolve(nombre + extension);

        // Escribir los bytes del archivo en la ruta especificada
        try {
            Files.write(rutaArchivo, archivo.getBytes());
        } catch (IOException e) {
            throw new IOException("Error al guardar el archivo en la ruta especificada: " + rutaArchivo, e);
        }

        // Crear una nueva instancia de Images y establecer sus atributos
        Images imagen = new Images();
        imagen.setNombre(nombre + extension);
        imagen.setRuta("/imagenes/" + nombre + extension);  // Ajuste aquí
        imagen.setActiva(true);

        // Guardar la entidad Images en la base de datos
        return imagesRepositorio.save(imagen);
    }

    // Obtener todos los Images turisticos
    public List<Images> obtenerTodosLosImages() {
        return imagesRepositorio.findAll();
    }

    // Guardar Image turistico
    public Images guardarImages(Images Images) {
        return imagesRepositorio.save(Images);
    }

    // Obtener Images por id
    public Images obtenerImagesPorId(Long id) {
        return imagesRepositorio.findById(id).orElseThrow(() -> new ImagesNotFoundException(id));
    }

    // Actualizar Image turistico
    public Images actulizarImages(Images Images) {
        return imagesRepositorio.save(Images);
    }

    // Eliminar Image
    public void eliminarImages(Long id) {
        imagesRepositorio.deleteById(id);
    }

    // Verificar si existe en la base por nombre
    public boolean verificarImageExistente(String Nombre) {
        return imagesRepositorio.existsByNombre(Nombre);
    }
}
