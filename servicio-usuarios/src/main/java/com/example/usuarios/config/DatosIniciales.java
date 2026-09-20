package com.example.usuarios.config;

import com.example.usuarios.domain.Usuario;
import com.example.usuarios.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration 
public class DatosIniciales {

    @Bean
    CommandLineRunner cargarUsuarios(UsuarioRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Usuario("Ana Torres", "ana@correo.com"));
                repository.save(new Usuario("Luis Gómez", "luis@correo.com"));
            }
        };
    }
}
