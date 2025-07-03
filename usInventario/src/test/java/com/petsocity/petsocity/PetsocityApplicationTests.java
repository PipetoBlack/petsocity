package com.petsocity.petsocity;

import com.petsocity.petsocity.model.Categoria;
import com.petsocity.petsocity.model.Inventario;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class PetsocityInventarioApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private Faker faker;

    @BeforeEach
    void setup() {
        faker = new Faker();
    }

    @Test
    @Order(1)
    void getCategorias() {
        String url = "http://localhost:" + port + "/api/v1/categorias";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("[");
    }

    @Test
    @Order(2)
    void postCategoriaAlimento() {
        Categoria categoria = new Categoria();
        categoria.setNombre("Alimento");

        ResponseEntity<Categoria> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/categorias",
                categoria,
                Categoria.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getNombre()).isEqualTo("Alimento");
    }

    @Test
    @Order(3)
    void postCategoriaJuguetes() {
        Categoria categoria = new Categoria();
        categoria.setNombre("Juguetes");

        ResponseEntity<Categoria> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/categorias",
                categoria,
                Categoria.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getNombre()).isEqualTo("Juguetes");
    }

    @Test
    @Order(4)
    void getCategoriaById() {
        String url = "http://localhost:" + port + "/api/v1/categorias/1";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(5)
    void putCategoria() {
        Categoria categoria = new Categoria();
        categoria.setNombre("Snacks");

        HttpEntity<Categoria> request = new HttpEntity<>(categoria);
        ResponseEntity<Categoria> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/categorias/1",
                HttpMethod.PUT,
                request,
                Categoria.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getNombre()).isEqualTo("Snacks");
    }

    @Test
    @Order(6)
    void deleteCategoria() {
        ResponseEntity<Void> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/categorias/1",
                HttpMethod.DELETE,
                null,
                Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(7)
    void getInventarios() {
        String url = "http://localhost:" + port + "/api/v1/inventarios";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("[");
    }

    @Test
    @Order(8)
    void postInventario() {
        Inventario producto = new Inventario();
        producto.setNombre("Pelota");
        producto.setDescripcion("Pelota para perros");
        producto.setPrecio(1990);
        producto.setStock(20);

        ResponseEntity<Inventario> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/inventarios",
                producto,
                Inventario.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getNombre()).isEqualTo("Pelota");
        assertThat(response.getBody().getStock()).isEqualTo(20);
    }

    @Test
    @Order(9)
    void getInventarioById() {
        String url = "http://localhost:" + port + "/api/v1/inventarios/1";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(10)
    void putInventario() {
        Inventario producto = new Inventario();
        producto.setNombre("Correa");
        producto.setDescripcion("Correa de perro");
        producto.setPrecio(4990);
        producto.setStock(10);

        HttpEntity<Inventario> request = new HttpEntity<>(producto);
        ResponseEntity<Inventario> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/inventarios/1",
                HttpMethod.PUT,
                request,
                Inventario.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getNombre()).isEqualTo("Correa");
        assertThat(response.getBody().getStock()).isEqualTo(10);
    }

    @Test
    @Order(11)
    void deleteInventario() {
        ResponseEntity<Void> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/inventarios/1",
                HttpMethod.DELETE,
                null,
                Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
