package org.example.proyecturitsexplor.Servicios;
import org.example.proyecturitsexplor.Entidades.TipoTurismo;
import org.example.proyecturitsexplor.Excepciones.TipoTurismoNotFoundException;
import org.example.proyecturitsexplor.Repositorios.TipoTurismoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TipoTurismoServicio {
    @Autowired
    private TipoTurismoRepositorio tipoTurismoRepositorio;

    public TipoTurismoServicio(TipoTurismoRepositorio tipoTurismoRepositorio) { this.tipoTurismoRepositorio = tipoTurismoRepositorio;}
    //CRUD

    //Obtener todos los Tipos de turismo
    public List<TipoTurismo> obtenerTodosLosTiposTurismo () {
        return tipoTurismoRepositorio.findAll();
    }
    //guardar tipos de turismo
    public TipoTurismo guardarTipoTurismo(TipoTurismo tipoTurismo) {
        return tipoTurismoRepositorio.save(tipoTurismo);
    }
    //Obtener tipos de turismo por id
    public TipoTurismo obtenerTipoTurismoPorId(Long id) {
        return tipoTurismoRepositorio.findById(id).orElseThrow(()-> new TipoTurismoNotFoundException(id));
    }
    //actulizar el tipo de turismo turistico
    public TipoTurismo actualizarTipoTurismo(TipoTurismo tipoTurismo) {
        return tipoTurismoRepositorio.save(tipoTurismo);
    }
    //Eliminar TipoTurismo
    public void eliminarTipoTurismo(Long id) {
        tipoTurismoRepositorio.deleteById(id);
    }
    //Verificar si existe en la base. por nombre
    public boolean verificarTipoTurismoExistente(String nombre) {
        return tipoTurismoRepositorio.existsByNombre(nombre);
    }
}
