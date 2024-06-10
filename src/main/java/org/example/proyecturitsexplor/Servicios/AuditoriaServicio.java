package org.example.proyecturitsexplor.Servicios;
import org.springframework.stereotype.Service;

@Service
public class AuditoriaServicio {

    // Método para registrar acciones en la auditoría
    public void registrarAccion(String email, String accion, String descripcion) {
        // Implementar lógica para registrar la acción, por ejemplo, guardar en una base de datos o un archivo de registro
        System.out.println("Usuario: " + email + " - Acción: " + accion + " - Descripción: " + descripcion);
        // Aquí puedes agregar la lógica para guardar la auditoría en una base de datos si lo prefieres
    }

}
