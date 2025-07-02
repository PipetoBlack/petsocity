package com.petsocity.petsocity.assemblers;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.petsocity.petsocity.controller.UsuarioController;
import com.petsocity.petsocity.model.Usuario;
import com.petsocity.petsocity.model.ApiErrorModel;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

    @Override
    public EntityModel<Usuario> toModel(Usuario usuario) {
        EntityModel<Usuario> model = EntityModel.of(usuario);

        if (usuario.getId() != null) {
            model.add(linkTo(methodOn(UsuarioController.class).obtenerUsuarioPorId(usuario.getId())).withSelfRel());
            model.add(linkTo(methodOn(UsuarioController.class).obtenerTodosUsuarios()).withRel("usuarios"));
            model.add(linkTo(methodOn(UsuarioController.class).actualizarUsuario(usuario.getId(), null)).withRel("actualizar"));
            model.add(linkTo(methodOn(UsuarioController.class).eliminarUsuario(usuario.getId())).withRel("eliminar"));
        }

        return model;
    }

    /**
     * Método auxiliar para envolver mensajes de error dentro de una estructura HATEOAS.
     * Recibe una clave (por ejemplo, "error") y un mensaje explicativo, construyendo
     * un EntityModel que permite incluir enlaces útiles para guiar al cliente en su navegación.
     *
     * Esto facilita que los errores no sólo se presenten como texto plano, sino también
     * como recursos enriquecidos y navegables, manteniendo la coherencia con el diseño RESTful.
     *
     * @param key clave del error (e.g. "error")
     * @param message mensaje explicativo o detalle del fallo
     * @return EntityModel que contiene el cuerpo del error y enlaces relacionados
     */
    
    public EntityModel<ApiErrorModel> wrapError(ApiErrorModel error) {
        return EntityModel.of(error,
            linkTo(methodOn(UsuarioController.class).obtenerTodosUsuarios()).withRel("usuarios")
        );
    }

    /**
     * Envoltorio de lista de usuarios con enlaces para navegación.
     * Este método toma una lista de objetos Usuario y la transforma en una
     * CollectionModel, aplicando HATEOAS a cada elemento mediante el método toModel(),
     * y añadiendo un enlace al recurso raíz de usuarios.
     *
     * Permite que el cliente consuma una colección estructurada en formato HAL,
     * donde cada usuario tiene sus propios enlaces (self, actualizar, eliminar, etc.)
     * y la colección tiene su propio enlace de navegación (self) que apunta al endpoint completo.
     *
     * @param listaUsuarios lista completa de usuarios obtenida desde el servicio
     * @return CollectionModel que contiene los EntityModel individuales con enlaces HATEOAS
     */

    public CollectionModel<EntityModel<Usuario>> toCollection(List<Usuario> listaUsuarios) {
        List<EntityModel<Usuario>> modelos = listaUsuarios.stream()
            .map(this::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(modelos,
            linkTo(methodOn(UsuarioController.class).obtenerTodosUsuarios()).withSelfRel()
        );
    }
}