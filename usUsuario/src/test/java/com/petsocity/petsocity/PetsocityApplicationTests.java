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

    @Test
    @Order(3)
    void deleteAllUsuariosShouldRemoveSuccessfully(){
        // Crear al menos un usuario para asegurarnos que hay algo que borrar    
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setPrimerNombre("Test");
        nuevoUsuario.setSegundoNombre("Test2");
        nuevoUsuario.setPrimerApellido("Prueba");
        nuevoUsuario.setSegundoApellido("Prueba2");
        nuevoUsuario.setEmail("eliminartodo@email.com");
        nuevoUsuario.setContrasenia("abc123");
        nuevoUsuario.setDireccion("Dirección Test");

        // Se crea el usuario en el endpoint
        restTemplate.postForEntity(
            "http://localhost:" + port + "/api/v1/usuarios",
            nuevoUsuario,
            Usuario.class
        );

        // Ejecutar DELETE a todos los usuarios 
        ResponseEntity<Void> response = restTemplate.exchange(
            "http://localhost:" + port + "/api/v1/usuarios",
            HttpMethod.DELETE,
            null,
            Void.class
        );

        //  Verificar que la respuesta sea 204 No Content o 200 OK
        // assertThat(response.getStatusCode())
        //     System.out.println("Código de estado al eliminar todos: " + response.getStatusCode());
        //     .withFailMessage("Se esperaba 204 NO_CONTENT al eliminar todos los usuarios.")

        //     .isIn(HttpStatus.NO_CONTENT, HttpStatus.OK);
            System.out.println("Código de estado al eliminar todos: " + response.getStatusCode());


            // Verificar que no queda ningún usuario
        String getAllResponse = restTemplate.getForObject(
            "http://localhost:" + port + "/api/v1/usuarios",
            String.class
        );

        // Asegurarse de no se encuentre ningun ID en la respuesta
        assertThat(getAllResponse)
            .withFailMessage("Se esperaban 0 usuarios tras la eliminación, pero se encontró: %s", getAllResponse)
            .doesNotContain("Prueba")
            .doesNotContain("Eliminar");
    }

    @Test
    @Order(4)
    void getUsuariosContainsBrackets() throws Exception {
            String response = this.restTemplate.getForObject("http://localhost:" + port + "/api/v1/usuarios", String.class);
            assertThat(response).contains("[");
    }

    private static Long idUsuarioTest; //--> Se crea una varibale para guardar ID

    @Test
    @Order(5)
    void createUsuarioShouldReturnCreated() {
        Usuario nuevoUsuario = new Usuario();
        Faker faker = new Faker();
        nuevoUsuario.setPrimerNombre(faker.name().firstName());
        nuevoUsuario.setSegundoNombre(faker.name().firstName());
        nuevoUsuario.setPrimerApellido(faker.name().lastName());
        nuevoUsuario.setSegundoApellido(faker.name().lastName());
        nuevoUsuario.setEmail(faker.internet().emailAddress() + "Test4");
        nuevoUsuario.setContrasenia(faker.internet().password());
        nuevoUsuario.setDireccion(faker.address().fullAddress());

        ResponseEntity<Usuario> response = restTemplate.postForEntity(
            "http://localhost:" + port + "/api/v1/usuarios",
            nuevoUsuario,
            Usuario.class);
        assertThat(response.getBody().getEmail()).isEqualTo(nuevoUsuario.getEmail());
        assertThat(response.getBody()).isNotNull();
        idUsuarioTest = response.getBody().getId(); // <--- ¡guardar el ID!
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
        // Verifica que el ID generado en el test 4 esté presente
        assertThat(idUsuarioTest)
            .withFailMessage("No se encontró el ID del usuario de prueba. Asegúrate de que el UsuarioTest se haya ejecutado.")
            .isNotNull();

        // Creamos un objeto Usuario con nuevos datos para actualizar
        Usuario actualizado = new Usuario();
        actualizado.setId(idUsuarioTest); // Este ID es fundamental para el PUT
        actualizado.setPrimerNombre("NombreActualizado");
        actualizado.setSegundoNombre("SegundoActualizado");
        actualizado.setPrimerApellido("ApellidoActualizado");
        actualizado.setSegundoApellido("SegundoApellidoActualizado");
        actualizado.setEmail("actualizado_" + idUsuarioTest + "@email.com");
        actualizado.setContrasenia("nuevaContrasenia123");
        actualizado.setDireccion("Nueva Dirección 456");

        // Creamos la solicitud HTTP que lleva el objeto actualizado en el cuerpo
        HttpEntity<Usuario> request = new HttpEntity<>(actualizado);

        // Enviamos una solicitud PUT para actualizar el usuario por ID
        ResponseEntity<Void> response = restTemplate.exchange(
            "http://localhost:" + port + "/api/v1/usuarios/" + idUsuarioTest,
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
            "http://localhost:" + port + "/api/v1/usuarios/" + idUsuarioTest,
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
        // Asegurarse de que tenemos un ID válido de usuario creado previamente
        assertThat(idUsuarioTest)
            .withFailMessage("No se encontró el ID del usuario de prueba. Asegúrate de que el UsuarioTest se haya ejecutado.")
            .isNotNull();

        // Enviar la solicitud DELETE para eliminar el usuario con ese ID
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
            "http://localhost:" + port + "/api/v1/usuarios/" + idUsuarioTest,
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
            "http://localhost:" + port + "/api/v1/usuarios/" + idUsuarioTest,
            String.class
        );

        // Verificar que ya no se puede encontrar el usuario
        assertThat(getAfterDelete.getStatusCode())
            .withFailMessage("Se esperaba que el usuario fuera eliminado, pero aún se encuentra.")
            .isEqualTo(HttpStatus.NOT_FOUND);
    }

}
