package com.petsocity.petsocity;

import net.datafaker.Faker;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import com.petsocity.petsocity.model.Usuario;
import com.petsocity.petsocity.repository.UsuarioRepository;

@Profile("dev-eliminarEsteTexto")
@Component
public class DataLoader implements CommandLineRunner{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();

        // Generar estudiantes
        for (int i = 0; i < 50; i++) {
            Usuario usuario = new Usuario();
            //usuario.setId(i + 1);
            usuario.setPrimerNombre(faker.name().firstName());
            usuario.setSegundoNombre(faker.name().firstName());
            usuario.setPrimerApellido(faker.name().lastName());
            usuario.setSegundoApellido(faker.name().lastName());
            usuario.setEmail(faker.internet().emailAddress());
            usuario.setContrasenia(faker.internet().password());
            usuario.setDireccion(faker.address().fullAddress());
            LocalDateTime localDate = LocalDateTime.now();
            usuario.setFechaCreacion(localDate.plusDays(i));
            usuarioRepository.save(usuario);
        }
    }   
}
