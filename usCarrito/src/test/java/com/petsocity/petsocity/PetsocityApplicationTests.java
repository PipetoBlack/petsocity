package com.petsocity.petsocity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PetsocityApplicationTests {

	@Autowired
	private CarritoController usuarioController;

    @Autowired
    private TestRestTemplate restTemplate;

	@Test
	void contextLoads() {
	}

}
