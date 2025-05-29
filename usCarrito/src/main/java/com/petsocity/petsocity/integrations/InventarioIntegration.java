package com.petsocity.petsocity.integrations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.petsocity.petsocity.model.InventarioDTO;

@Component
public class InventarioIntegration {
    @Autowired
    private RestTemplate restTemplate;

    public InventarioDTO obtenerInventarioPorId(Long inventarioId) {
        String url = "http://localhost:8089/api/v1/inventarios/" + inventarioId; // Cambia el puerto y endpoint según tu microservicio Inventario
        return restTemplate.getForObject(url, InventarioDTO.class);
    }
}
