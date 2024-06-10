package org.example.proyecturitsexplor.Servicios;
import org.example.proyecturitsexplor.Entidades.Permiso;
import org.example.proyecturitsexplor.Repositorios.PermisoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PermisoServicio {
    @Autowired
    private PermisoRepositorio permisoRepositorio;

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
