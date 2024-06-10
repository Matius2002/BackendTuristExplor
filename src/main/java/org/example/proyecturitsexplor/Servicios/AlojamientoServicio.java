package org.example.proyecturitsexplor.Servicios;
import org.example.proyecturitsexplor.Entidades.Alojamiento;
import org.example.proyecturitsexplor.Excepciones.AlojamientoNotFoundException;
import org.example.proyecturitsexplor.Repositorios.AlojamientoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class AlojamientoServicio {
    @Autowired
    private AlojamientoRepositorio alojamientoRepositorio;

    public AlojamientoServicio(AlojamientoRepositorio alojamientoRepositorio) {
        this.alojamientoRepositorio = alojamientoRepositorio;
    }
    //CRUD

    //Obtener todos los alojamientos turisticos
    public List<Alojamiento> obtenerTodosLosAlojamientos () {
        return alojamientoRepositorio.findAll();
    }
    //guardar alojamiento turistico
    public Alojamiento guardarAlojamiento(Alojamiento alojamiento) {
        return alojamientoRepositorio.save(alojamiento);
    }
    //Obtener alojamientos por id
    public Alojamiento obtenerAlojamientoPorId(Long id) {
        return alojamientoRepositorio.findById(id).orElseThrow(()-> new AlojamientoNotFoundException(id));
    }
    //actulizar alojamiento turistico
    public Alojamiento actulizarAlojamientos(Alojamiento alojamiento) {
        return alojamientoRepositorio.save(alojamiento);
    }
    //Eliminar alojamiento
    public void eliminarAlojamiento(Long id) {
        alojamientoRepositorio.deleteById(id);
    }
    //Verificar si existe en la base. por nombre
    public boolean verificarAlojamientoExistente(String nombre) {
        return alojamientoRepositorio.existsByNombre(nombre);
    }
}
