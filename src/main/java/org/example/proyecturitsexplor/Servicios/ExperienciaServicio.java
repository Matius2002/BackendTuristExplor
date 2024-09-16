/*Obejtivo: proporciona operaciones CRUD para la entidad Experiencia. A través de este servicio, las experiencias turísticas pueden ser 
creadas, leídas, actualizadas y eliminadas en la base de datos.*/
package org.example.proyecturitsexplor.Servicios; /*Paquete*/

/*Importaciones*/
import org.example.proyecturitsexplor.Entidades.Experiencia;
import org.example.proyecturitsexplor.Excepciones.ExperienciaNotFoundException;
import org.example.proyecturitsexplor.Repositorios.ExperienciaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/*Servicio y clase*/
@Service
public class ExperienciaServicio {
    /*Dependencia y variable*/
    @Autowired
    private ExperienciaRepositorio experienciaRepositorio;

    /*Métodos*/
    public ExperienciaServicio(ExperienciaRepositorio experienciaRepositorio) {
        this.experienciaRepositorio = experienciaRepositorio;
    }

    //CRUD
    //Obtener todos los experiencia turisticos
    public List<Experiencia> obtenerTodosLosExperiencia () {
        return experienciaRepositorio.findAll();
    }
    //guardar experiencia turistico
    public Experiencia guardarExperiencia(Experiencia experiencia) {
        experiencia.setFecha(new Date());
        return experienciaRepositorio.save(experiencia);
    }
    //Obtener experiencia por id
    public Experiencia obtenerExperienciaPorId(Long id) {
        return experienciaRepositorio.findById(id).orElseThrow(()-> new ExperienciaNotFoundException(id));
    }
    //actulizar experiencia turistico
    public Experiencia actulizarExperiencia(Experiencia experiencia) {
        return experienciaRepositorio.save(experiencia);
    }
    //Eliminar experiencia
    public void eliminarExperiencia(Long id) {
        experienciaRepositorio.deleteById(id);
    }

}
