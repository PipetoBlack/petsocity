package com.petsocity.petsocity;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class PetsocityApplicationTestMockito {

    // https://spring.io/guides/gs/testing-web
    // Another useful approach is to not start the server at all but to test only
    // the layer below that, where Spring handles the incoming HTTP request and
    // hands it off to your controller.

    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void shouldReturnDefaultMessage() throws Exception{
        this.mockMvc.perform(get("/api/v1/usuarios")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("[")));
    }

}
