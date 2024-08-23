/*Objetivo: proporciona operaciones CRUD para la entidad EpocaVisitar. A través de este servicio, las épocas de visita pueden ser creadas, 
leídas, actualizadas y eliminadas en la base de datos. Además, incluye un método para verificar la existencia de una época de visita por 
nombre, lo cual es útil para evitar duplicados.*/
package org.example.proyecturitsexplor.Servicios; /*Paquete*/

/*Importaciones*/
import org.example.proyecturitsexplor.Entidades.EpocaVisitar;
import org.example.proyecturitsexplor.Excepciones.EpocaVisitarNotFoundException;
import org.example.proyecturitsexplor.Repositorios.EpocaVisitarRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/*Servicio y clase*/
@Service
public class EpocaVisitarServicio {

    /*Dependencia y variable*/
    @Autowired
    private EpocaVisitarRepositorio epocaVisitarRepositorio;

    /*Métodos*/
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
