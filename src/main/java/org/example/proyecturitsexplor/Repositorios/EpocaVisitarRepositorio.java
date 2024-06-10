package org.example.proyecturitsexplor.Repositorios;
import org.example.proyecturitsexplor.Entidades.EpocaVisitar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface EpocaVisitarRepositorio  extends JpaRepository<EpocaVisitar, Long> {

    boolean existsByNombre(String nombre);
}
