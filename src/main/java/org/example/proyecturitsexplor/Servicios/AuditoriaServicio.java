/*Objetivo: proporciona una funcionalidad básica para registrar acciones de auditoría. Actualmente, la implementación se limita a imprimir 
la información en la consola, pero se deja espacio para expandir la funcionalidad, como guardar estos registros en una base de datos o en 
un archivo de registro.*/
package org.example.proyecturitsexplor.Servicios; /*Paquete*/

/*Importaciones*/
import org.springframework.stereotype.Service;

/*Servicio y clase*/
@Service
public class AuditoriaServicio {

    // Método para registrar acciones en la auditoría
    public void registrarAccion(String email, String accion, String descripcion) {
        // Implementar lógica para registrar la acción, por ejemplo, guardar en una base de datos o un archivo de registro
        System.out.println("Usuario: " + email + " - Acción: " + accion + " - Descripción: " + descripcion);
        // Aquí puedes agregar la lógica para guardar la auditoría en una base de datos si lo prefieres
    }

}
