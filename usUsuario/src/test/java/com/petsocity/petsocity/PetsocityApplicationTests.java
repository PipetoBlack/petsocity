package com.petsocity.petsocity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.petsocity.petsocity.controller.UsuarioController;

@SpringBootTest
class PetsocityApplicationTests {

	@Autowired
	private UsuarioController usuarioController;

    @Autowired
    private TestRestTemplate restTemplate;

	@Test
	void contextLoads() {
        System.out.println("Testing the context loading...");
        // System.out.println("Server running on port: " + port);
	}

    @Test
    void contextLoads2() throws Exception {
        System.out.println("Testing the context loading. and the controller...");
        assertThat(usuarioController).isNotNull();
    }

    @Test
    void getLibrosContainsBrackets() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port +
                "/api/v1/libros",
                String.class)).toString().contains("[");
    }

}
