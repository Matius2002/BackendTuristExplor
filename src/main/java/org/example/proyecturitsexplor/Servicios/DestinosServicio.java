package org.example.proyecturitsexplor.Servicios;
import org.example.proyecturitsexplor.Entidades.*;
import org.example.proyecturitsexplor.Excepciones.DestinosNotFoundException;
import org.example.proyecturitsexplor.Repositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DestinosServicio {
    @Autowired
    private DestinosRepositorio destinosRepositorio;

    @Autowired
        private TipoTurismoRepositorio tipoTurismoRepositorio;

        @Autowired
        private AtracionPrincipalRepositorio atracionPrincipalRepositorio;

        @Autowired
        private EpocaVisitarRepositorio epocaVisitarRepositorio;

        @Autowired
        private ImagesRepositorio imagesRepositorio;

    public DestinosServicio(DestinosRepositorio destinosRepositorio) {
        this.destinosRepositorio = destinosRepositorio;
    }
    //CRUD

    //Obtener todos los destinos turisticos
    public List<Destinos> obtenerTodosLosDestinos () {
        return destinosRepositorio.findAll();
    }
    //guardar destino turistico
    public Destinos guardarDestino(Destinos destino) {
        // Asegúrate de que todas las entidades relacionadas existen
        Set<TipoTurismo> tiposTurismo = new HashSet<>();
        for (TipoTurismo tipo : destino.getTipoTurismo()) {
            tiposTurismo.add(tipoTurismoRepositorio.findById(tipo.getId()).orElseThrow(() -> new RuntimeException("TipoTurismo no encontrado")));
        }
        destino.setTipoTurismo(tiposTurismo);

        Set<AtracionPrincipal> atraciones = new HashSet<>();
        for (AtracionPrincipal atracion : destino.getAtracionesPrincipales()) {
            atraciones.add(atracionPrincipalRepositorio.findById(atracion.getId()).orElseThrow(() -> new RuntimeException("AtracionPrincipal no encontrado")));
        }
        destino.setAtracionesPrincipales(atraciones);

        Set<EpocaVisitar> epocas = new HashSet<>();
        for (EpocaVisitar epoca : destino.getEpocasVisitar()) {
            epocas.add(epocaVisitarRepositorio.findById(epoca.getId()).orElseThrow(() -> new RuntimeException("EpocaVisitar no encontrado")));
        }
        destino.setEpocasVisitar(epocas);

        Set<Images> imagenes = new HashSet<>();
        for (Images imagen : destino.getImagenes()) {
            imagenes.add(imagesRepositorio.findById(imagen.getId()).orElseThrow(() -> new RuntimeException("Imagen no encontrado")));
        }
        destino.setImagenes(imagenes);

        return destinosRepositorio.save(destino);
    }
    //Obtener destinos por id
    public Destinos obtenerDestinosPorId(Long id) {
        return destinosRepositorio.findById(id).orElseThrow(()-> new DestinosNotFoundException(id));
    }
    //actulizar destino turistico
    public Destinos actulizarDestinos(Destinos destinos) {
       return destinosRepositorio.save(destinos);
    }
    //Eliminar Destino
    public void eliminarDestino(Long id) {
        destinosRepositorio.deleteById(id);
    }
    //Verificar si existe en la base. por nombre
    public boolean verificarDestinoExistente(String destinoName) {
        return destinosRepositorio.existsByDestinoName(destinoName);
    }
}
