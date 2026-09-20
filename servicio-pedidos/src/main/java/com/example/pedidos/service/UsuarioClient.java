package com.example.pedidos.service;

import com.example.pedidos.dto.UsuarioResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component 
public class UsuarioClient {

    private final RestClient restClient;

    public UsuarioClient(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("http://localhost:8081")
                .build();
    }

    public UsuarioResponse obtenerUsuario(Long usuarioId) {
        return restClient.get()
                .uri("/usuarios/{id}", usuarioId)
                .retrieve()
                .body(UsuarioResponse.class);
    }
}
