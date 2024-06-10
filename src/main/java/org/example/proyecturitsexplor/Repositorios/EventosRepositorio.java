package org.example.proyecturitsexplor.Repositorios;
import org.example.proyecturitsexplor.Entidades.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface EventosRepositorio  extends JpaRepository<Evento, Long> {
    boolean existsByNombre(String nombre);
}
