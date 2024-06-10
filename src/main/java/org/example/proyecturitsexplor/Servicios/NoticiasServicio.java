package org.example.proyecturitsexplor.Servicios;
import org.example.proyecturitsexplor.Entidades.Noticia;
import org.example.proyecturitsexplor.Excepciones.NoticiasNotFoundException;
import org.example.proyecturitsexplor.Repositorios.NoticiasRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NoticiasServicio {
    @Autowired
    private NoticiasRepositorio noticiasRepositorio;

    public NoticiasServicio(NoticiasRepositorio noticiasRepositorio) {
        this.noticiasRepositorio = noticiasRepositorio;
    }
    //CRUD

    //Obtener todos los Noticias turisticos
    public List<Noticia> obtenerTodosLosNoticias () {
        return noticiasRepositorio.findAll();
    }
    //guardar Noticias turistico
    public Noticia guardarNoticias(Noticia noticia) {
        return noticiasRepositorio.save(noticia);
    }
    //Obtener Noticias por id
    public Noticia obtenerNoticiasPorId(Long id) {
        return noticiasRepositorio.findById(id).orElseThrow(()-> new NoticiasNotFoundException(id));
    }
    //actulizar Noticias turistico
    public Noticia actulizarNoticias(Noticia noticia) {
        return noticiasRepositorio.save(noticia);
    }
    //Eliminar Noticias
    public void eliminarNoticias(Long id) {
        noticiasRepositorio.deleteById(id);
    }
    //Verificar si existe en la base. por nombre
    public boolean verificarNoticiasExistente(String titulo) {
        return noticiasRepositorio.existsByTitulo(titulo);
    }
}
