package org.example.proyecturitsexplor.Servicios;
import org.example.proyecturitsexplor.Entidades.Evento;
import org.example.proyecturitsexplor.Excepciones.EventoNotFoundException;
import org.example.proyecturitsexplor.Repositorios.EventosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EventosServicio {
    @Autowired
    private EventosRepositorio eventosRepositorio;

    public EventosServicio(EventosRepositorio eventosRepositorio) {
        this.eventosRepositorio = eventosRepositorio;
    }
    //CRUD

    //Obtener todos los eventos turisticos
    public List<Evento> obtenerTodosLosEventos () {
        return eventosRepositorio.findAll();
    }
    //guardar eventos turistico
    public Evento guardarEvento(Evento evento) {
        return eventosRepositorio.save(evento);
    }
    //Obtener eventos por id
    public Evento obtenerEventosPorId(Long id) {
        return eventosRepositorio.findById(id).orElseThrow(()-> new EventoNotFoundException(id));
    }
    //actulizar eventos turistico
    public Evento actulizarEventos(Evento evento) {
        return eventosRepositorio.save(evento);
    }
    //Eliminar eventos
    public void eliminarEventos(Long id) {
        eventosRepositorio.deleteById(id);
    }
    //Verificar si existe en la base. por nombre
    public boolean verificarEventosExistente(String nombre) {
        return eventosRepositorio.existsByNombre(nombre);
    }
}
