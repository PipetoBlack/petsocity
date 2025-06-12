package com.petsocity.petsocity;

import biblioteca.salas.duoc.biblioteca.salas.duoc.model.*;
import biblioteca.salas.duoc.biblioteca.salas.duoc.repository.*;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.petsocity.petsocity.model.Usuario;
import com.petsocity.petsocity.repository.UsuarioRepository;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Profile("dev")
@Component

public class dataLoader implements UsuarioRepository {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

    // Generar estudiantes
    for (int i = 0; i < 50; i++) {
        Usuario usuario = new Usuario();
        usuario.setPrimerNombre(faker.name().firstName());
        usuario.setSegundoNombre(faker.name().firstName());
        usuario.setPrimerApellido(faker.name().lastName());
        usuario.setSegundoApellido(faker.name().lastName());
        usuario.setEmail(faker.internet().emailAddress());
        usuario.setContrasenia(faker.internet().password());
        usuario.setDireccion(faker.address().full);
        usuarioRepository.save(usuario);



}
