package org.example.proyecturitsexplor.Servicios.auth;
import jakarta.servlet.http.HttpServletRequest;
import org.example.proyecturitsexplor.DTO.LoginRequestDTO;
import org.example.proyecturitsexplor.DTO.LoginResponseDTO;
import org.example.proyecturitsexplor.DTO.UserResponseDTO;
import org.example.proyecturitsexplor.Entidades.Usuarios;
import org.example.proyecturitsexplor.Excepciones.UserNotFoundException;
import org.example.proyecturitsexplor.Repositorios.UserRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserRepositorio userRep;

    public Date getExpirationTime(String token) {
        return jwtService.extractExpiration(token);
    }

    public LoginResponseDTO login(LoginRequestDTO credenciales) {
        UsernamePasswordAuthenticationToken upat=new UsernamePasswordAuthenticationToken(
                credenciales.getEmail(), credenciales.getPassword()
        );
        try{
            authManager.authenticate(upat);
        }catch(Exception e){
            if (e instanceof BadCredentialsException) {
                throw new org.example.proyecturitsexplor.Excepciones.BadCredentialsException("Correo o contraseña incorrectos", "400", "Error de credenciales", HttpStatus.BAD_REQUEST);
            }
        }
        Usuarios usuario=this.userRep.findByEmail(credenciales.getEmail()).get();
        return new LoginResponseDTO(this.jwtService.generarToken(usuario, generarClaims(usuario)));
    }
    private Map<String, Object> generarClaims(Usuarios usuario) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", usuario.getId());
        claims.put("username", usuario.getNombreUsuario());
        List<String> permisos = new ArrayList<>();
        usuario.getAuthorities().forEach(a->permisos.add(a.getAuthority()));
        claims.put("permisos", permisos);
        claims.put("correo", usuario.getEmail());
        return claims;
    }
    public UserResponseDTO findLoggedUser(){
        String email=(String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Usuarios usuario=this.userRep.findByEmail(email).orElseThrow(()->new UserNotFoundException(email));
        return new UserResponseDTO(usuario.getId(), usuario.getNombreUsuario(), usuario.getEmail());
    }
    public boolean validateToken(String jwt) {
        try{
            jwtService.extractUsername(jwt);
            return true;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public String renewToken(String token) {
        // Lógica para renovar el token
        String renewedToken = null;
        try {
            if (jwtService.validateToken(token)) { // Valida si el token actual es válido
                String username = jwtService.extractUsername(token); // Extrae el nombre de usuario del token
                Usuarios usuario = userRep.findByEmail(username).orElseThrow(() -> new UserNotFoundException(username));
                renewedToken = jwtService.generarToken(usuario, generarClaims(usuario)); // Genera un nuevo token renovado
            }
        } catch (Exception e) {
            // Manejo de excepciones si la renovación del token falla
            throw new RuntimeException("Error al renovar el token", e);
        }
        return renewedToken;
    }
    public String resolveToken(HttpServletRequest request) {
        // Lógica para extraer el token del encabezado de la solicitud
        final String header = request.getHeader("Authorization");

        // Verifica si el encabezado es válido y devuelve el token
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7); // Quita el prefijo "Bearer " del token
        }

        return null; // Retorna null si no se encontró un token válido en el encabezado
    }
}
