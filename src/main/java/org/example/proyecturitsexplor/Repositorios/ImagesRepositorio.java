package org.example.proyecturitsexplor.Repositorios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.example.proyecturitsexplor.Entidades.Images;

public interface ImagesRepositorio extends JpaRepository<Images, Long> {

    boolean existsByNombre(String nombre);
}
