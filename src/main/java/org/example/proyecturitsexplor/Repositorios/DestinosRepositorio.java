package org.example.proyecturitsexplor.Repositorios;
import org.example.proyecturitsexplor.Entidades.Destinos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface DestinosRepositorio extends JpaRepository<Destinos, Long> {

    boolean existsByDestinoName(String destinoName);
}
