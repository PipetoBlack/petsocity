package com.petsocity.petsocity;

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

import net.datafaker.Faker;

// @SpringBootTest
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class PetsocityApplicationTests {
	@Autowired
	private UsuarioController usuarioController;

	@LocalServerPort
    private int port;

	@Autowired
    private TestRestTemplate restTemplate;

	@Test
    @Order(1)
	void contextLoads() {
		System.out.println("Testing the context loading...");
        // System.out.println("Server running on port: " + port);
	}

	@Test
    @Order(2)
    void contextLoads2() throws Exception {
        System.out.println("Testing the context loading. and the controller...");
        assertThat(usuarioController).isNotNull();
    }

	
   /* @Test
    @Order(3)
    void getUsuariosContainsBrackets() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port +
                "/api/v1/usuarios",
                String.class)).toString().contains("[");
    }
*/

    @Test
    @Order(3)
    void getUsuariosContainsBrackets() throws Exception {
            String response = this.restTemplate.getForObject("http://localhost:" + port + "/api/v1/usuarios", String.class);
            assertThat(response).contains("[");
    }

    @Test
    @Order(4)
    void createUsuarioShouldReturnCreated() {
        Usuario nuevoUsuario = new Usuario();
        Faker faker = new Faker();
        nuevoUsuario.setPrimerNombre(faker.name().firstName());
        nuevoUsuario.setSegundoNombre(faker.name().firstName());
        nuevoUsuario.setPrimerApellido(faker.name().lastName());
        nuevoUsuario.setSegundoApellido(faker.name().lastName());
        nuevoUsuario.setEmail(faker.internet().emailAddress());
        nuevoUsuario.setContrasenia(faker.internet().password());
        nuevoUsuario.setDireccion(faker.address().fullAddress());

        ResponseEntity<Usuario> response = restTemplate.postForEntity(
            "http://localhost:" + port + "/api/v1/usuarios",
            nuevoUsuario,
            Usuario.class);
        assertThat(response.getBody().getEmail()).isEqualTo(nuevoUsuario.getEmail());
    }

    @Test
    @Order(5)
    void updateUsuarioShouldSucceed() {
        Usuario actualizado = new Usuario("Felipe Actualizado", "nuevo@email.com");

        HttpEntity<Usuario> request = new HttpEntity<>(actualizado);
        ResponseEntity<Void> response = restTemplate.exchange(
            "http://localhost:" + port + "/api/v1/usuarios/1",
            HttpMethod.PUT,
            request,
            Void.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT); // o OK dependiendo de tu API
    }

    @Test
    @Order(6)
    void deleteUsuarioShouldRemoveSuccessfully() {
        ResponseEntity<Void> response = restTemplate.exchange(
            "http://localhost:" + port + "/api/v1/usuarios/1",
            HttpMethod.DELETE,
            null,
            Void.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT); // o OK
    }
}
