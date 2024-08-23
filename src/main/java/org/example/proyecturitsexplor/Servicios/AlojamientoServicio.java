/*Objetivo: proporciona operaciones CRUD para la entidad Alojamiento. A través de este servicio, los alojamientos turísticos pueden 
ser creados, leídos, actualizados y eliminados en la base de datos. Además, incluye un método para verificar la existencia de un alojamiento 
por nombre, lo cual es útil para evitar duplicados.*/
package org.example.proyecturitsexplor.Servicios; /*Paquete*/

/*Importaciones*/
import org.example.proyecturitsexplor.Entidades.Alojamiento;
import org.example.proyecturitsexplor.Excepciones.AlojamientoNotFoundException;
import org.example.proyecturitsexplor.Repositorios.AlojamientoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/*Servicio y clase*/
@Service
public class AlojamientoServicio {
    /*Dependencia y variable*/
    @Autowired
    private AlojamientoRepositorio alojamientoRepositorio;

    /*Métodos*/
    public AlojamientoServicio(AlojamientoRepositorio alojamientoRepositorio) {
        this.alojamientoRepositorio = alojamientoRepositorio;
    }

    //CRUD
    //Obtener todos los alojamientos turisticos
    public List<Alojamiento> obtenerTodosLosAlojamientos () {
        return alojamientoRepositorio.findAll();
    }
    //guardar alojamiento turistico
    public Alojamiento guardarAlojamiento(Alojamiento alojamiento) {
        return alojamientoRepositorio.save(alojamiento);
    }
    //Obtener alojamientos por id
    public Alojamiento obtenerAlojamientoPorId(Long id) {
        return alojamientoRepositorio.findById(id).orElseThrow(()-> new AlojamientoNotFoundException(id));
    }
    //actulizar alojamiento turistico
    public Alojamiento actulizarAlojamientos(Alojamiento alojamiento) {
        return alojamientoRepositorio.save(alojamiento);
    }
    //Eliminar alojamiento
    public void eliminarAlojamiento(Long id) {
        alojamientoRepositorio.deleteById(id);
    }
    //Verificar si existe en la base. por nombre
    public boolean verificarAlojamientoExistente(String nombre) {
        return alojamientoRepositorio.existsByNombre(nombre);
    }
}
