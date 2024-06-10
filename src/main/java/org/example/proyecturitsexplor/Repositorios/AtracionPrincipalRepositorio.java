package org.example.proyecturitsexplor.Repositorios;
import org.example.proyecturitsexplor.Entidades.AtracionPrincipal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface AtracionPrincipalRepositorio extends JpaRepository<AtracionPrincipal, Long> {

    boolean existsByNombre(String nombre);
}
