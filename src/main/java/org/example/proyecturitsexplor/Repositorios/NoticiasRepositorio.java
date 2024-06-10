package org.example.proyecturitsexplor.Repositorios;
import org.example.proyecturitsexplor.Entidades.Noticia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface NoticiasRepositorio  extends JpaRepository<Noticia, Long> {
    boolean existsByTitulo(String titulo);
}
