package org.example.proyecturitsexplor.Servicios;
import org.example.proyecturitsexplor.Entidades.Usuarios;
import org.example.proyecturitsexplor.Excepciones.UserNotFoundException;
import org.example.proyecturitsexplor.Repositorios.UserRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServicio {
    @Autowired
    private UserRepositorio userRepositorio;

    public UserServicio(UserRepositorio userRepositorio) {
        this.userRepositorio = userRepositorio;
    }
    //CRUD
    //Obtener todos los Usuarios
    public List<Usuarios> obtenerTodosLosUsuarios () {
        return userRepositorio.findAll();
    }
    //guardar Usuarios
    public Usuarios guardarUsuarios(Usuarios usuarios) {
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

    //Login de inicio de session
    public ResponseEntity<?> loginUsuario(Usuarios usuarios) {
        if (verificarExistentePorEmail(usuarios.getEmail())) {
            Usuarios contra = userRepositorio.findByEmail(usuarios.getEmail());
            String contrasenaEncriptada = (usuarios.getPassword());
            if (contrasenaEncriptada.equals(contra.getPassword())) {
                System.out.println("TRUE");
                return new ResponseEntity<>(true, HttpStatus.OK);
            } else {
                System.out.println("NO COINCIDEN");
                return new ResponseEntity<>(false, HttpStatus.OK);
            }
        } else {
            System.out.println("NO SE ENCUENTRA");
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }

    public Usuarios obtenerUsuariosPorEmail(String email) {
        Usuarios usuario = userRepositorio.findByEmail(email);
        if (usuario == null) {
            usuario = new Usuarios();
        }
        return usuario;
    }
    public Usuarios obtenerPorEmail(String email) {
        return userRepositorio.findByEmail(email);
    }

}

//    public boolean authenticateUser(String nombreUsuario, String password) {
//        Usuarios usuarios = userRepositorio.findByNombreUsuario(nombreUsuario);
//        return usuarios != null && passwordEncoder.matches(password, usuarios.getPassword());
//    }