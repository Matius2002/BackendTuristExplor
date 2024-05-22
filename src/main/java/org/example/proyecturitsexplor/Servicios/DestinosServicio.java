package org.example.proyecturitsexplor.Servicios;
import org.example.proyecturitsexplor.Entidades.Destinos;
import org.example.proyecturitsexplor.Excepciones.DestinosNotFoundException;
import org.example.proyecturitsexplor.Repositorios.DestinosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DestinosServicio {
    @Autowired
    private DestinosRepositorio destinosRepositorio;

    public DestinosServicio(DestinosRepositorio destinosRepositorio) {
        this.destinosRepositorio = destinosRepositorio;
    }
    //CRUD

    //Obtener todos los destinos turisticos
    public List<Destinos> obtenerTodosLosDestinos () {
        return destinosRepositorio.findAll();
    }
    //guardar destino turistico
    public Destinos guardarDestino(Destinos destinos) {
        return destinosRepositorio.save(destinos);
    }
    //Obtener destinos por id
    public Destinos obtenerDestinosPorId(Long id) {
        return destinosRepositorio.findById(id).orElseThrow(()-> new DestinosNotFoundException(id));
    }
    //actulizar destino turistico
    public Destinos actulizarDestinos(Destinos destinos) {
       return destinosRepositorio.save(destinos);
    }
    //Eliminar Destino
    public void eliminarDestino(Long id) {
        destinosRepositorio.deleteById(id);
    }
    //Verificar si existe en la base. por nombre
    public boolean verificarDestinoExistente(String destinoName) {
        return destinosRepositorio.existsByDestinoName(destinoName);
    }
}
