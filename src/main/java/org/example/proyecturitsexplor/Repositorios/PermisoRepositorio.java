package org.example.proyecturitsexplor.Repositorios;
import org.example.proyecturitsexplor.Entidades.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface PermisoRepositorio extends JpaRepository<Permiso, Long> {
}
