package com.petsocity.petsocity;

import com.petsocity.petsocity.model.Categoria;
import com.petsocity.petsocity.model.Inventario;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PetsocityApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private Faker faker;

    @BeforeEach
    void setup() {
        faker = new Faker();
    }

    private Long crearCategoriaDePrueba(String nombre) {
        Categoria categoria = new Categoria();
        categoria.setNombre(nombre);

        ResponseEntity<Categoria> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/categoria",
                categoria,
                Categoria.class);

        return response.getBody().getId();
    }

    private Long crearProductoDePrueba(String nombre) {
        Inventario producto = new Inventario();
        producto.setNombre(nombre);
        producto.setDescripcion("Producto de prueba");
        producto.setPrecio(9990);
        producto.setStock(5);

        ResponseEntity<Inventario> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/inventario",
                producto,
                Inventario.class);

        return response.getBody().getId();
    }

    @Test
    @Order(1)
    void getCategorias() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/v1/categoria",
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(2)
    void crearCategoriaTest() {
        Long id = crearCategoriaDePrueba("Alimento");
        assertThat(id).isNotNull();
    }

    @Test
    @Order(3)
    void getCategoriaById() {
        Long id = crearCategoriaDePrueba("Juguetes");
        ResponseEntity<Categoria> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/v1/categorias/" + id,
                Categoria.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getNombre()).isEqualTo("Juguetes");
    }

    @Test
    @Order(4)
    void updateCategoriaTest() {
        Long id = crearCategoriaDePrueba("Accesorios");
        Categoria actualizada = new Categoria();
        actualizada.setNombre("Snacks");

        HttpEntity<Categoria> request = new HttpEntity<>(actualizada);
        ResponseEntity<Categoria> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/categorias/" + id,
                HttpMethod.PUT,
                request,
                Categoria.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getNombre()).isEqualTo("Snacks");
    }

    @Test
    @Order(5)
    void deleteCategoriaTest() {
        Long id = crearCategoriaDePrueba("EliminarCat");
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/categorias/" + id,
                HttpMethod.DELETE,
                null,
                Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> getAfterDelete = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/v1/categorias/" + id,
                String.class);
        assertThat(getAfterDelete.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Order(6)
    void getInventarios() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/v1/inventarios",
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(7)
    void crearInventarioTest() {
        Long id = crearProductoDePrueba("Pelota");
        assertThat(id).isNotNull();
    }

    @Test
    @Order(8)
    void getInventarioById() {
        Long id = crearProductoDePrueba("Correa");
        ResponseEntity<Inventario> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/v1/inventarios/" + id,
                Inventario.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getNombre()).isEqualTo("Correa");
    }

    @Test
    @Order(9)
    void updateInventarioTest() {
        Long id = crearProductoDePrueba("Collar");
        Inventario actualizado = new Inventario();
        actualizado.setNombre("Collar Reflectante");
        actualizado.setDescripcion("Actualizado");
        actualizado.setPrecio(4990);
        actualizado.setStock(15);

        HttpEntity<Inventario> request = new HttpEntity<>(actualizado);
        ResponseEntity<Inventario> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/inventarios/" + id,
                HttpMethod.PUT,
                request,
                Inventario.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getNombre()).isEqualTo("Collar Reflectante");
        assertThat(response.getBody().getStock()).isEqualTo(15);
    }

    @Test
    @Order(10)
    void deleteInventarioTest() {
        Long id = crearProductoDePrueba("EliminarProd");
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/inventarios/" + id,
                HttpMethod.DELETE,
                null,
                Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> getAfterDelete = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/v1/inventarios/" + id,
                String.class);
        assertThat(getAfterDelete.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
