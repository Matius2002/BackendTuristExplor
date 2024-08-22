package org.example.proyecturitsexplor.Seguridad;
import org.example.proyecturitsexplor.Excepciones.UserNotFoundException;
import org.example.proyecturitsexplor.Repositorios.UserRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityBeansInjector {
    @Autowired
    private AuthenticationConfiguration authConfig;
    @Autowired
    private UserRepositorio userRep;

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return this.authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return (String email) -> {
            return this.userRep.findByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException(email));
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider=new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(userDetailsService());
        return authProvider;
    }

}
