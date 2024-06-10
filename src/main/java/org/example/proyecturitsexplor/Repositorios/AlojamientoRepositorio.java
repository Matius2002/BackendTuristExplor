package org.example.proyecturitsexplor.Repositorios;
import org.example.proyecturitsexplor.Entidades.Alojamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface AlojamientoRepositorio  extends JpaRepository<Alojamiento, Long> {
    boolean existsByNombre(String Nombre);
}
