package org.example.proyecturitsexplor.Servicios;
import org.example.proyecturitsexplor.Entidades.Rol;
import org.example.proyecturitsexplor.Excepciones.RolNotFoundException;
import org.example.proyecturitsexplor.Repositorios.RolRepositorio;
import org.example.proyecturitsexplor.Repositorios.UserRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RolServicio {
    @Autowired
    private RolRepositorio rolRepositorio;
    @Autowired
    private UserRepositorio userRepositorio;

    //CRUD

    //Obtener todos los roles
    public List<Rol> obtenerTodosLosRoles () {
        return rolRepositorio.findAll();
    }
    //guardar Rol
    public Rol guardarRol(Rol rol) {
        return rolRepositorio.save(rol);
    }
    //Obtener la Rol  por id
    public Rol obtenerRolPorId(Long id) {
        return rolRepositorio.findById(id).orElseThrow(()-> new RolNotFoundException(id));
    }
    //actulizar Rol
    public Rol actualizarRol(Rol rol) {
        return rolRepositorio.save(rol);
    }
    //Eliminar Rol
    public void eliminarRol(Long id) {
        rolRepositorio.deleteById(id);
    }
    //Verificar si existe en la base. por nombre
    public boolean verificarRolExistente(String rolName) {
        return rolRepositorio.existsByRolName(rolName);
    }
}

