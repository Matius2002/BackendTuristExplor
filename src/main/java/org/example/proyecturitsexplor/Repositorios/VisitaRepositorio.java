package org.example.proyecturitsexplor.Repositorios;

import java.util.List;

import org.example.proyecturitsexplor.Entidades.Visita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VisitaRepositorio extends JpaRepository<Visita, Long> {
    @Query("SELECT v.rutaVisitada, COUNT(v) as totalVisitas FROM Visita v GROUP BY v.rutaVisitada ORDER BY totalVisitas DESC")
    List<Object[]> contarVisitasPorRuta();
}
