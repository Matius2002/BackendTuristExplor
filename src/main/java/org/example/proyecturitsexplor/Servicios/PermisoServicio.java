/*Objetivo: proporciona operaciones CRUD básicas para la entidad Permiso. A través de este servicio, los permisos pueden ser creados, 
leídos, actualizados y eliminados en la base de datos.*/
package org.example.proyecturitsexplor.Servicios; /*Paquete*/

/*Importaciones*/
import org.example.proyecturitsexplor.Entidades.Permiso;
import org.example.proyecturitsexplor.Repositorios.PermisoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/*Servicio y clase*/
@Service
public class PermisoServicio {

    /*Dependencia y variable*/
    @Autowired
    private PermisoRepositorio permisoRepositorio;

    /*Métodos*/
    public Permiso guardarPermiso(Permiso permiso) {
        return permisoRepositorio.save(permiso);
    }

    public List<Permiso> obtenerTodosLosPermisos() {
        return permisoRepositorio.findAll();
    }

    public Permiso obtenerPermisoPorId(Long id) {
        Optional<Permiso> permiso = permisoRepositorio.findById(id);
        return permiso.orElse(null);
    }

    public Permiso actualizarPermiso(Permiso permiso) {
        return permisoRepositorio.save(permiso);
    }

    public void eliminarPermiso(Long id) {
        permisoRepositorio.deleteById(id);
    }
}
