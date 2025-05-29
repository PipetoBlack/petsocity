package com.petsocity.petsocity.integrations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.petsocity.petsocity.model.UsuarioDTO;

@Component
public class UsuarioIntegration {
    @Autowired
    private RestTemplate restTemplate;

    public UsuarioDTO obtenerUsuarioPorId(Long usuarioId) {
        String url = "http://localhost:8088/api/v1/usuarios/" + usuarioId; // Cambia el puerto y endpoint según tu microservicio Inventario
        return restTemplate.getForObject(url, UsuarioDTO.class);
    }
}
