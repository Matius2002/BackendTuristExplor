package org.example.proyecturitsexplor.Repositorios;
import org.example.proyecturitsexplor.Entidades.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface RolRepositorio extends JpaRepository<Rol, Long> {

    boolean existsByRolName(String rolName);

}
