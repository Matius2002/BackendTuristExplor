/*Objetivo: El servicio se encarga de realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre la entidad Usuarios 
en la base de datos. También incluye métodos para verificar la existencia de usuarios en la base de datos y para manejar contraseñas 
de manera segura utilizando la codificación.*/
package org.example.proyecturitsexplor.Servicios; /*Paquete*/

/*Importaciones*/
import org.example.proyecturitsexplor.Entidades.Rol;
import org.example.proyecturitsexplor.Entidades.Usuarios;
import org.example.proyecturitsexplor.Excepciones.UserNotFoundException;
import org.example.proyecturitsexplor.Repositorios.RolRepositorio;
import org.example.proyecturitsexplor.Repositorios.UserRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*Servicio y clase*/
@Service
public class UserServicio {

    /*Variables y inyección de dependencias*/
    @Autowired
    private UserRepositorio userRepositorio;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RolRepositorio rolRep;

    public UserServicio(UserRepositorio userRepositorio) {
        this.userRepositorio = userRepositorio;
    }

    // CRUD
    // Obtener todos los Usuarios
    public List<Usuarios> obtenerTodosLosUsuarios () {
        return userRepositorio.findAll();
    }
    //guardar Usuarios
    public Usuarios guardarUsuarios(Usuarios usuarios) {
        List<Rol> rolesUsuario=new ArrayList<>();
        usuarios.getRoles().forEach(r->{
            System.out.println(r.getId());
            Rol rolBD=this.rolRep.findById(r.getId()).get();
            rolesUsuario.add(rolBD);
        });
        usuarios.setPassword(this.passwordEncoder.encode(usuarios.getPassword()));
        //usuarios.setRoles(rolesUsuario);
        return userRepositorio.save(usuarios);
    }
    //Obtener la Usuarios por id
    public Usuarios obtenerUsuariosPorId(Long id) {
        return userRepositorio.findById(id).orElseThrow(()-> new UserNotFoundException(id));
    }
    //actulizar Usuarios
    public Usuarios actulizarUsuarios(Usuarios usuarios) {
        return userRepositorio.save(usuarios);
    }
    //Eliminar Usuarios
    public void eliminarUsuarios(Long id) {
        userRepositorio.deleteById(id);
    }
    //Verificar si existe en la base. por nombre
    public boolean verificarUsuariosExistente(String nombreUsuario) {
        return userRepositorio.existsByNombreUsuario(nombreUsuario);
    }
    // Verificar usuario existente por email
    public boolean verificarExistentePorEmail(String email){
        return userRepositorio.existsByEmail(email);
    }

    public Usuarios obtenerUsuariosPorEmail(String email) {
        Usuarios usuario=null;
        Optional<Usuarios> usuarioOptional = userRepositorio.findByEmail(email);
        if (usuarioOptional.isEmpty()) {
            usuario = new Usuarios();
        }
        usuario=usuarioOptional.get();
        return usuario;
    }
    public Usuarios obtenerPorEmail(String email) {

        Usuarios usuario=null;
        Optional<Usuarios> usuarioOptional = userRepositorio.findByEmail(email);
        if (usuarioOptional.isEmpty()) {
            usuario = new Usuarios();
        }
        usuario=usuarioOptional.get();
        return usuario;
    }
}

