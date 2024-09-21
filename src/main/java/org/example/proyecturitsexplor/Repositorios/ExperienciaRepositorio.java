package org.example.proyecturitsexplor.Repositorios;

import org.example.proyecturitsexplor.Entidades.Experiencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface ExperienciaRepositorio  extends JpaRepository<Experiencia, Long> {

}
