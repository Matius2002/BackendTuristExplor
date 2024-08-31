package org.example.proyecturitsexplor.Seguridad.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.proyecturitsexplor.Entidades.Usuarios;
import org.example.proyecturitsexplor.Repositorios.UserRepositorio;
import org.example.proyecturitsexplor.Servicios.auth.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

/*La anotación @Component hace que esta clase sea un bean de Spring, permitiendo que Spring la detecte y gestione automáticamente*/
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter { /*es una clase que extiende OncePerRequestFilter, lo que significa que este filtro se ejecuta una vez por cada solicitud HTTP*/
    
  @Autowired /*para inyectar automáticamente las instancias de JWTService (servicio para manejar JWTs)*/
  private JWTService jwtService;

  @Autowired /*UserRepositorio (repositorio para acceder a la base de datos de usuarios)*/
  private UserRepositorio userRep;

    @Override
    protected void doFilterInternal(@SuppressWarnings("null") HttpServletRequest request, @SuppressWarnings("null") HttpServletResponse response, @SuppressWarnings("null") FilterChain filterChain) throws ServletException, IOException { /*es el método principal que se ejecuta cada vez que se intercepta una solicitud HTTP. Aquí es donde se realiza la lógica de autenticación*/
           String authorization = request.getHeader("Authorization"); /*El filtro primero intenta obtener el token JWT del encabezado Authorization de la solicitud. Si el encabezado no está presente o no comienza con "Bearer ", el filtro simplemente pasa la solicitud al siguiente filtro en la cadena*/
            if (!StringUtils.hasText(authorization) || !authorization.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
           }

            String token = authorization.split(" ")[1];
            String email=this.jwtService.extractUsername(token); /*Se extrae el token JWT del encabezado y se utiliza JWTService para extraer el nombre de usuario (email) del token*/
            Usuarios usuario=this.userRep.findByEmail(email).get(); /*Se busca el usuario en la base de datos utilizando el repositorio UserRepositorio basado en el email extraído del token*/
            UsernamePasswordAuthenticationToken upat=new UsernamePasswordAuthenticationToken(email, null, usuario.getAuthorities()); /*Se crea un UsernamePasswordAuthenticationToken con el email del usuario y sus autoridades (roles o permisos)*/
            upat.setDetails(new WebAuthenticationDetails(request)); /*Se añaden detalles de la solicitud HTTP utilizando WebAuthenticationDetails*/
            SecurityContextHolder.getContext().setAuthentication(upat); /*El token de autenticación se almacena en el SecurityContextHolder, lo que significa que el usuario está autenticado en el contexto de seguridad de Spring*/
            filterChain.doFilter(request, response); /*la solicitud continúa a través de la cadena de filtros. Si la autenticación fue exitosa, el usuario ahora está autenticado y las solicitudes posteriores tendrán acceso a la seguridad proporcionada por Spring*/
   }
}
