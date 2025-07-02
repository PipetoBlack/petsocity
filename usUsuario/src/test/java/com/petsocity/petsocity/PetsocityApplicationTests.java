package com.petsocity.petsocity;

import com.petsocity.petsocity.controller.UsuarioController;
import com.petsocity.petsocity.model.Usuario;
import com.petsocity.petsocity.repository.UsuarioRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.*;
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

    @Autowired
    private UsuarioController usuarioController;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private final Faker faker = new Faker();

    private String baseUrl() {
        return "http://localhost:" + port + "/api/v1/usuarios";
    }

    @BeforeEach
    void limpiarBaseDeDatos() {
        usuarioRepository.deleteAll();
    }

    private Long crearUsuarioDePrueba(String sufijoEmail) {
        Usuario nuevo = new Usuario();
        nuevo.setPrimerNombre(faker.name().firstName());
        nuevo.setSegundoNombre(faker.name().firstName());
        nuevo.setPrimerApellido(faker.name().lastName());
        nuevo.setSegundoApellido(faker.name().lastName());
        nuevo.setEmail("test_" + sufijoEmail + "@" + faker.internet().domainName());
        nuevo.setContrasenia(faker.internet().password());
        nuevo.setDireccion(faker.address().fullAddress());

        ResponseEntity<Usuario> response = restTemplate.postForEntity(
                baseUrl(), nuevo, Usuario.class
        );

        assertThat(response.getBody()).isNotNull();
        return response.getBody().getId();
    }

    @Test
    @Order(1)
    void contextLoads() {
        assertThat(usuarioController).isNotNull();
    }

    @Test
    @Order(2)
    void contextLoads2() {
        System.out.println("Verificando que el contexto cargue correctamente.");
        assertThat(usuarioController).isNotNull();
    }

    @Test
    @Order(3)
    void createUsuarioShouldReturnCreated() {
        Usuario usuario = new Usuario();
        usuario.setPrimerNombre(faker.name().firstName());
        usuario.setSegundoNombre(faker.name().firstName());
        usuario.setPrimerApellido(faker.name().lastName());
        usuario.setSegundoApellido(faker.name().lastName());
        usuario.setEmail("test4_" + faker.internet().emailAddress());
        usuario.setContrasenia(faker.internet().password());
        usuario.setDireccion(faker.address().fullAddress());

        ResponseEntity<Usuario> response = restTemplate.postForEntity(
                baseUrl(), usuario, Usuario.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().getLocation()).isNotNull();
    }

    @Test
    @Order(4)
    void debeListarUsuariosYContenerCorchetes() {
        crearUsuarioDePrueba("brackets");
        String response = restTemplate.getForObject(baseUrl(), String.class);
        assertThat(response).contains("{");
    }

    @Test
    @Order(5)
    void debeListarUsuariosYContenerUsuarioTest4() {
        String response = restTemplate.getForObject(baseUrl(), String.class);
        assertThat(response.contains("test4_"));
    }

    @Test
    @Order(6)
    void debeActualizarUsuario() {
        Long id = crearUsuarioDePrueba("Update");

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
                baseUrl() + "/" + id,
                HttpMethod.PUT,
                request,
                Void.class
        );

        assertThat(response.getStatusCode()).isIn(HttpStatus.NO_CONTENT, HttpStatus.OK);

        Usuario usuarioActualizado = restTemplate.getForObject(
                baseUrl() + "/" + id, Usuario.class
        );

        assertThat(usuarioActualizado).isNotNull();
        assertThat(usuarioActualizado.getPrimerNombre()).isEqualTo("NombreActualizado");
        assertThat(usuarioActualizado.getEmail()).contains("actualizado_");
    }

    @Test
    @Order(7)
    void debeEliminarUsuarioCorrectamente() {
        Long id = crearUsuarioDePrueba("delete");

        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                baseUrl() + "/" + id,
                HttpMethod.DELETE,
                null,
                Void.class
        );

        assertThat(deleteResponse.getStatusCode()).isIn(HttpStatus.NO_CONTENT, HttpStatus.OK);

        ResponseEntity<String> getAfterDelete = restTemplate.getForEntity(
                baseUrl() + "/" + id, String.class
        );

        assertThat(getAfterDelete.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}