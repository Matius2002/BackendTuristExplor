package org.example.proyecturitsexplor.Repositorios;
import org.example.proyecturitsexplor.Entidades.TipoAlojamiento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoAlojamientoRepositorio  extends JpaRepository<TipoAlojamiento, Long> {

    boolean existsByNombre(String nombre);
}