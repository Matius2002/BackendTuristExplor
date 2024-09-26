/*Objetivo: se encarga de la generación, firma y validación de tokens JWT (JSON Web Tokens).*/
package org.example.proyecturitsexplor.Servicios.auth; /*Paquetes*/

/*Importaciones*/
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.proyecturitsexplor.Entidades.Usuarios;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Map;

@Service
public class JWTService {

    /*Variables y Inyección de Dependencias*/
    @Value("${security.jwt.expiration-in-minutes}")
    private Long EXPIRATION_IN_MINUTES;
    private final String secretKey=new SecretKey().secretKey;

    /*Métodos*/
    public String generarToken(Usuarios usuario, Map<String, Object> claims){
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date((EXPIRATION_IN_MINUTES * 60 * 1000) + issuedAt.getTime()); // Calcula la fecha de expiración del nuevo token
        return Jwts.builder()
                .header().type("JWT")
                .and()
                .subject(usuario.getEmail()) // Define el sujeto del token (en este caso, el email del usuario)
                .issuedAt(issuedAt) // Define la fecha de emisión del token
                .expiration(expiration) // Define la fecha de expiración del token
                .claims(claims) // Añade los claims personalizados del usuario
                .signWith(generarKey(), Jwts.SIG.HS256) // Firma el token con la clave secreta
                .compact();
    }


    private javax.crypto.SecretKey generarKey(){
        byte[] secretAsBytes= Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(secretAsBytes);
    }


    public String extractUsername(String jwt){
        return extractAllClaims(jwt).getSubject();
    }

    private Claims extractAllClaims(String jwt){
        return Jwts.parser().verifyWith(generarKey()).build()
                .parseSignedClaims(jwt).getPayload();
    }

    public boolean validateToken(String jwt) {
        return true;
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

}