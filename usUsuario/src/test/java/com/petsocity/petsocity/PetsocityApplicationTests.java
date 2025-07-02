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
	@Autowired
	private UsuarioController usuarioController;
    @Autowired
    private UsuarioRepository usuarioRepository;

	@LocalServerPort
    private int port;

	@Autowired
    private TestRestTemplate restTemplate;

    private final Faker faker = new Faker();

    // Método privado de usuario reutilizable
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
            "http://localhost:" + port + "/api/v1/usuarios",
            nuevo,
            Usuario.class
        );

        assertThat(response.getBody()).isNotNull();
        return response.getBody().getId();
    }


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

    @BeforeEach
    void limpiarBaseDeDatos() {
        usuarioRepository.deleteAll();
    }

    @Test
    @Order(4)
    void getUsuariosContainsBrackets() throws Exception {
            String response = this.restTemplate.getForObject("http://localhost:" + port + "/api/v1/usuarios", String.class);
            assertThat(response).contains("[");
    }


    // GET FALTANTE

    @Test
    @Order(5)
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
            "http://localhost:" + port + "/api/v1/usuarios",
            usuario,
            Usuario.class);
        assertThat(response.getBody().getEmail()).isEqualTo(usuario.getEmail());
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    @Order(6)
    void getUsuarios() throws Exception {
        System.out.println("port: " + port);
        assertThat(this.restTemplate.getForObject("http://localhost:" + port +
                "/api/v1/usuarios",
                String.class)).toString().contains("Test4");
    }

    @Test
    @Order(7)
    void updateUsuarioShouldSucceed() {
        Long id = crearUsuarioDePrueba("Update");

        // Verifica que el ID generado en el test 4 esté presente
        assertThat(id)
            .withFailMessage("No se encontró el ID del usuario de prueba. Asegúrate de que el UsuarioTest se haya ejecutado.")
            .isNotNull();

        // Creamos un objeto Usuario con nuevos datos para actualizar
        Usuario actualizado = new Usuario();
        actualizado.setPrimerNombre("NombreActualizado");
        actualizado.setSegundoNombre("SegundoActualizado");
        actualizado.setPrimerApellido("ApellidoActualizado");
        actualizado.setSegundoApellido("SegundoApellidoActualizado");
        actualizado.setEmail("actualizado_" + id + "@email.com");
        actualizado.setContrasenia("nuevaContrasenia123");
        actualizado.setDireccion("Nueva Dirección 456");

        // Creamos la solicitud HTTP que lleva el objeto actualizado en el cuerpo
        HttpEntity<Usuario> request = new HttpEntity<>(actualizado);

        // Enviamos una solicitud PUT para actualizar el usuario por ID
        ResponseEntity<Void> response = restTemplate.exchange(
            "http://localhost:" + port + "/api/v1/usuarios/" + id,
            HttpMethod.PUT,
            request,
            Void.class
        );

        // Verificamos que el status devuelto sea 204 (sin contenido) o 200 (éxito con contenido)
        assertThat(response.getStatusCode())
            .withFailMessage("Se esperaba un código 204 NO_CONTENT o 200 OK, pero se recibió: %s", response.getStatusCode())
            .isIn(HttpStatus.NO_CONTENT, HttpStatus.OK);

        // Recuperamos el usuario actualizado desde el backend
        ResponseEntity<Usuario> getResponse = restTemplate.getForEntity(
            "http://localhost:" + port + "/api/v1/usuarios/" + id,
            Usuario.class
        );

        // Validamos que el usuario devuelto no sea null
        Usuario usuarioActualizado = getResponse.getBody();
        assertThat(usuarioActualizado).isNotNull();

        // Comprobamos que los cambios realmente se hayan aplicado
        assertThat(usuarioActualizado.getPrimerNombre()).isEqualTo("NombreActualizado");
        assertThat(usuarioActualizado.getEmail()).contains("actualizado_");
    }



    @Test
    @Order(8)
    void deleteUsuarioShouldRemoveSuccessfully() {
        Long id = crearUsuarioDePrueba("delete");

        // Enviar la solicitud DELETE para eliminar el usuario con ese ID
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
            "http://localhost:" + port + "/api/v1/usuarios/" + id,
            HttpMethod.DELETE,
            null,
            Void.class
        );

        // Verificar que la respuesta sea exitosa: 204 No Content o 200 OK
        assertThat(deleteResponse.getStatusCode())
            .withFailMessage("Se esperaba un código 204 NO_CONTENT o 200 OK, pero se recibió: %s", deleteResponse.getStatusCode())
            .isIn(HttpStatus.NO_CONTENT, HttpStatus.OK);

        // Intentar recuperar el usuario eliminado (debería devolver 404 Not Found)
        ResponseEntity<String> getAfterDelete = restTemplate.getForEntity(
            "http://localhost:" + port + "/api/v1/usuarios/" + id,
            String.class
        );

        // Verificar que ya no se puede encontrar el usuario
        assertThat(getAfterDelete.getStatusCode())
            .withFailMessage("Se esperaba que el usuario fuera eliminado, pero aún se encuentra.")
            .isEqualTo(HttpStatus.NOT_FOUND);
    }

}
