package com.petsocity.petsocity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.petsocity.petsocity.controller.UsuarioController;
import com.petsocity.petsocity.model.Usuario;
import com.petsocity.petsocity.repository.UsuarioRepository;

import net.datafaker.Faker;

// @SpringBootTest
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class PetsocityApplicationTests {
 @Autowired private UsuarioController usuarioController;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private TestRestTemplate restTemplate;
    @LocalServerPort private int port;

    private final Faker faker = new Faker();
    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/v1/usuarios";
    }

    // Reutilizable para generar usuarios de prueba
    private Long crearUsuarioDePrueba(String emailExtra) {
        Usuario nuevo = new Usuario();
        nuevo.setPrimerNombre(faker.name().firstName());
        nuevo.setSegundoNombre(faker.name().firstName());
        nuevo.setPrimerApellido(faker.name().lastName());
        nuevo.setSegundoApellido(faker.name().lastName());
        nuevo.setEmail(faker.internet().emailAddress() + emailExtra);
        nuevo.setContrasenia(faker.internet().password());
        nuevo.setDireccion(faker.address().fullAddress());

        ResponseEntity<Usuario> response = restTemplate.postForEntity(
            getBaseUrl(), nuevo, Usuario.class
        );

        assertThat(response.getBody()).isNotNull();
        return response.getBody().getId();
    }

    // Verifica que el contexto se cargue correctamente
    @Test
    void contextLoads() {
        assertThat(usuarioController).isNotNull();
    }

    // Limpia la base antes de cada test
    @BeforeEach
    void limpiarBaseDeDatos() {
        usuarioRepository.deleteAll();
    }

    // Verifica que el GET retorne un JSON válido
    @Test
    void getUsuariosContainsBrackets() {
        String response = restTemplate.getForObject(getBaseUrl(), String.class);
        assertThat(response).contains("[");
    }

    // Crea un usuario y verifica que se retorne correctamente
    @Test
    void createUsuarioShouldReturnCreated() {
        Usuario usuario = new Usuario();
        usuario.setPrimerNombre(faker.name().firstName());
        usuario.setSegundoNombre(faker.name().firstName());
        usuario.setPrimerApellido(faker.name().lastName());
        usuario.setSegundoApellido(faker.name().lastName());
        usuario.setEmail(faker.internet().emailAddress() + "Test4");
        usuario.setContrasenia(faker.internet().password());
        usuario.setDireccion(faker.address().fullAddress());

        ResponseEntity<Usuario> response = restTemplate.postForEntity(
            getBaseUrl(), usuario, Usuario.class
        );

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getEmail()).isEqualTo(usuario.getEmail());
    }

    // Verifica que el usuario creado esté presente en la respuesta
    @Test
    void getUsuarios() {
        crearUsuarioDePrueba("Test4");
        String listaUsuarios = restTemplate.getForObject(getBaseUrl(), String.class);
        assertThat(listaUsuarios).contains("Test4");
    }

    // Prueba de actualización de un usuario existente
    @Test
    void updateUsuarioShouldSucceed() {
        Long id = crearUsuarioDePrueba("update");

        Usuario actualizado = new Usuario();
        actualizado.setPrimerNombre("NombreActualizado");
        actualizado.setSegundoNombre("SegundoActualizado");
        actualizado.setPrimerApellido("ApellidoActualizado");
        actualizado.setSegundoApellido("SegundoApellidoActualizado");
        actualizado.setEmail("actualizado_" + id + "@email.com");
        actualizado.setContrasenia("nuevaContrasenia123");
        actualizado.setDireccion("Nueva Dirección 456");

        HttpEntity<Usuario> request = new HttpEntity<>(actualizado);

        ResponseEntity<Void> response = restTemplate.exchange(
            getBaseUrl() + "/" + id, HttpMethod.PUT, request, Void.class
        );

        assertThat(response.getStatusCode()).isIn(HttpStatus.NO_CONTENT, HttpStatus.OK);

        ResponseEntity<Usuario> getResponse = restTemplate.getForEntity(
            getBaseUrl() + "/" + id, Usuario.class
        );

        Usuario usuarioActualizado = getResponse.getBody();
        assertThat(usuarioActualizado).isNotNull();
        assertThat(usuarioActualizado.getPrimerNombre()).isEqualTo("NombreActualizado");
    }

    // Prueba de eliminación de un usuario
    @Test
    void deleteUsuarioShouldRemoveSuccessfully() {
        Long id = crearUsuarioDePrueba("delete");

        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
            getBaseUrl() + "/" + id, HttpMethod.DELETE, null, Void.class
        );

        assertThat(deleteResponse.getStatusCode()).isIn(HttpStatus.NO_CONTENT, HttpStatus.OK);

        ResponseEntity<String> getAfterDelete = restTemplate.getForEntity(
            getBaseUrl() + "/" + id, String.class
        );

        assertThat(getAfterDelete.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}