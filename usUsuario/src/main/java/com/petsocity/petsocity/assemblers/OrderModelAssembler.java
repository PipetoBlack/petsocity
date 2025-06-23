package com.petsocity.petsocity.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.petsocity.petsocity.controller.UsuarioController;
import com.petsocity.petsocity.model.Usuario;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>>{
    @Override
    public EntityModel<Usuario> toModel(Usuario usuario){
        return EntityModel.of(usuario,
                linkTo(methodOn(UsuarioController.class).get)

        )
    }
}
