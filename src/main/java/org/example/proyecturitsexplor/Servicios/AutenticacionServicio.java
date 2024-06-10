package org.example.proyecturitsexplor.Servicios;
import org.example.proyecturitsexplor.Entidades.Usuarios;
import org.example.proyecturitsexplor.Repositorios.UserRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutenticacionServicio {
    @Autowired
    private UserRepositorio userRepositorio;

    public boolean autenticar(String email, String password) {
        Usuarios usuario = userRepositorio.findByEmail(email);
        return usuario != null && usuario.getPassword().equals(password);
    }
}
