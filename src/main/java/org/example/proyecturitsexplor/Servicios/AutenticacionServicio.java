//package org.example.proyecturitsexplor.Servicios;
//import org.example.proyecturitsexplor.Entidades.Usuarios;
//import org.example.proyecturitsexplor.Repositorios.UserRepositorio;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class AutenticacionServicio {
//    @Autowired
//    private UserRepositorio userRepositorio;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    public boolean autenticar(String email, String password) {
//        Optional<Usuarios> usuarioOptional = userRepositorio.findByEmail(email);
//
//        if (usuarioOptional.isPresent()) {
//            Usuarios usuario = usuarioOptional.get();
//            return passwordEncoder.matches(password, usuario.getPassword());
//        } else {
//            return false;
//        }
//    }
//}
//
//
