package org.example.proyecturitsexplor.Servicios;
import org.example.proyecturitsexplor.Entidades.EpocaVisitar;
import org.example.proyecturitsexplor.Excepciones.EpocaVisitarNotFoundException;
import org.example.proyecturitsexplor.Repositorios.EpocaVisitarRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EpocaVisitarServicio {
    @Autowired
    private EpocaVisitarRepositorio epocaVisitarRepositorio;

    public EpocaVisitarServicio(EpocaVisitarRepositorio epocaVisitarRepositorio) {
        this.epocaVisitarRepositorio = epocaVisitarRepositorio;
    }
    //CRUD

    //Obtener todos los Epoca Visitar turisticos
    public List<EpocaVisitar> obtenerTodosLosEpocaVisitar () {
        return epocaVisitarRepositorio.findAll();
    }
    //guardar epocavisitar turistico
    public EpocaVisitar guardarEpocaVisitar(EpocaVisitar epocaVisitar) {
        return epocaVisitarRepositorio.save(epocaVisitar);
    }
    //Obtener EpocaVisitar por id
    public EpocaVisitar obtenerEpocaVisitarPorId(Long id) {
        return epocaVisitarRepositorio.findById(id).orElseThrow(()-> new EpocaVisitarNotFoundException(id));
    }
    //actulizar EpocaVisitar turistico
    public EpocaVisitar actulizarEpocaVisitar(EpocaVisitar epocaVisitar) {
        return epocaVisitarRepositorio.save(epocaVisitar);
    }
    //Eliminar EpocaVisitar
    public void eliminarEpocaVisitar(Long id) {
        epocaVisitarRepositorio.deleteById(id);
    }
    //Verificar si existe en la base. por nombre
    public boolean verificarEpocaVisitarExistente(String nombre) {
        return epocaVisitarRepositorio.existsByNombre(nombre);
    }
}
