package org.example.proyecturitsexplor.Repositorios;
import org.example.proyecturitsexplor.Entidades.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserRepositorio extends JpaRepository<Usuarios, Long> {
    boolean existsByNombreUsuario(String nombreUsuario);
    boolean existsByEmail(String email);

    Optional<Usuarios> findByEmail(String email);

    Usuarios findByNombreUsuario(String nombreUsuario);
}
