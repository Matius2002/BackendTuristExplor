/*Objetivo: Este servicio permite realizar operaciones CRUD sobre la entidad TipoAlojamiento, asegurando la correcta gestión de estos datos 
dentro de la aplicación. Además, incluye un método para verificar la existencia de un tipo de alojamiento por nombre y maneja errores cuando 
no se encuentra un tipo de alojamiento específico.*/
package org.example.proyecturitsexplor.Servicios; /*Paquete*/

/*Importaciones*/
import org.example.proyecturitsexplor.Entidades.TipoAlojamiento;
import org.example.proyecturitsexplor.Excepciones.TipoAlojamientoNotFoundException;
import org.example.proyecturitsexplor.Repositorios.TipoAlojamientoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/*Servicio y clase*/
@Service
public class TipoAlojamientoServicio {

    /*Dependencia y variable*/
    @Autowired
    private TipoAlojamientoRepositorio tipoAlojamientoRepositorio;

    public TipoAlojamientoServicio(TipoAlojamientoRepositorio tipoAlojamientoRepositorio) {
        this.tipoAlojamientoRepositorio = tipoAlojamientoRepositorio;
    }
    
    //CRUD
    //Obtener todos los tipo alojamiento turisticos
    public List<TipoAlojamiento> obtenerTodosLosTipoAlojamientos () {
        return tipoAlojamientoRepositorio.findAll();
    }
    //guardar tipo alojamiento turistico
    public TipoAlojamiento guardarTipoAlojamiento(TipoAlojamiento tipoAlojamiento) {
        return tipoAlojamientoRepositorio.save(tipoAlojamiento);
    }
    //Obtener Tipo Alojamiento por id
    public TipoAlojamiento obtenerTipoAlojamientoPorId(Long id) {
        return tipoAlojamientoRepositorio.findById(id).orElseThrow(()-> new TipoAlojamientoNotFoundException(id));
    }
    //actulizar tipo alojamiento turistico
    public TipoAlojamiento actulizarTipoAlojamiento(TipoAlojamiento tipoAlojamiento) {
        return tipoAlojamientoRepositorio.save(tipoAlojamiento);
    }
    //Eliminar tipo alojamiento
    public void eliminarTipoAlojamiento(Long id) {
        tipoAlojamientoRepositorio.deleteById(id);
    }
    //Verificar si existe en la base. por nombre
    public boolean verificarTipoAlojamientoExistente(String nombre) {
        return tipoAlojamientoRepositorio.existsByNombre(nombre);
    }

}
