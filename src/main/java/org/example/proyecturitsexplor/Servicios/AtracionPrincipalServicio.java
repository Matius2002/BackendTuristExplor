package org.example.proyecturitsexplor.Servicios;
import org.example.proyecturitsexplor.Entidades.AtracionPrincipal;
import org.example.proyecturitsexplor.Excepciones.AtracionPrincipalNotFoundException;
import org.example.proyecturitsexplor.Repositorios.AtracionPrincipalRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AtracionPrincipalServicio {

    @Autowired
    private AtracionPrincipalRepositorio atracionPrincipalRepositorio;

    public AtracionPrincipalServicio(AtracionPrincipalRepositorio atracionPrincipalRepositorio) {
        this.atracionPrincipalRepositorio = atracionPrincipalRepositorio;
    }
    //CRUD

    //Obtener todos las atraciones principales turisticos
    public List<AtracionPrincipal> obtenerTodosLosAtracionPrincipal () {
        return atracionPrincipalRepositorio.findAll();
    }
    //guardar AtracionPrincipal turistico
    public AtracionPrincipal guardarAtracionPrincipal(AtracionPrincipal atracionPrincipal) {
        return atracionPrincipalRepositorio.save(atracionPrincipal);
    }
    //Obtener la atracion principal por id
    public AtracionPrincipal obtenerAtracionPrincipalPorId(Long id) {
        return atracionPrincipalRepositorio.findById(id).orElseThrow(()-> new AtracionPrincipalNotFoundException(id));
    }
    //actulizar AtracionPrincipal turistico
    public AtracionPrincipal actulizarAtracionPrincipal(AtracionPrincipal atracionPrincipal) {
        return atracionPrincipalRepositorio.save(atracionPrincipal);
    }
    //Eliminar AtracionPrincipal
    public void eliminarAtracionPrincipal(Long id) {
        atracionPrincipalRepositorio.deleteById(id);
    }
    //Verificar si existe en la base. por nombre
    public boolean verificarAtracionPrincipalExistente(String nombre) {
        return atracionPrincipalRepositorio.existsByNombre(nombre);
    }


}
