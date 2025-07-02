package com.petsocity.petsocity.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petsocity.petsocity.assemblers.UsuarioModelAssembler;
import com.petsocity.petsocity.model.Usuario;
import com.petsocity.petsocity.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuarios", description = "Operaciones CRUD de usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    private UsuarioModelAssembler assembler;

    public UsuarioController(UsuarioService usuarioService, UsuarioModelAssembler assembler) {
        this.usuarioService = usuarioService;
        this.assembler = assembler;
    }

    // Leer todo
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener lista completa de usuarios", description = "Retorna todos los usuarios registrados")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200", description = "Operación exitosa",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "400", description = "No se puede generar la lista de usuarios",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Usuario.class)))
    })
    public CollectionModel<EntityModel<Usuario>> obtenerTodosUsuarios() {
        List<Usuario> usuarios = usuarioService.obtenerTodosUsuarios();
        return assembler.toCollection(usuarios);
    }

    // Leer por id
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener un usuario por ID", description = "Busca un usuario especifico usando su ID")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200", description = "Usuario encontrado",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Usuario.class)))
    })
    public ResponseEntity<EntityModel<?>> obtenerUsuarioPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.obtenerPorIdUsuario(id);
        if (usuario == null) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaTypes.HAL_JSON)
                .body(assembler.wrapError("error", "Usuario no encontrado"));
        }
        return ResponseEntity.ok(assembler.toModel(usuario));
    }

    // Crear
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear usuario", description = "Registra un nuevo usuario")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "201", description = "Usuario creado",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "400", description = "Datos invalidos o duplicados",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Usuario.class)))
    })
    public ResponseEntity<EntityModel<?>> crearUsuario(@RequestBody @Valid Usuario usuario) {
        try {
            Usuario creado = usuarioService.crearUsuario(usuario); // ID generado por la BD
            URI location = linkTo(methodOn(UsuarioController.class).obtenerUsuarioPorId(creado.getId())).toUri();

            return ResponseEntity
                    .created(location) // 201 Created con Location header
                    .contentType(MediaTypes.HAL_JSON) // explícito aunque Spring lo infiere
                    .body(assembler.toModel(creado)); // Respuesta HATEOAS
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .contentType(MediaTypes.HAL_JSON)
                    .body(assembler.wrapError("error", e.getMessage())); // Error estructurado con enlaces
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .contentType(MediaTypes.HAL_JSON)
                    .body(assembler.wrapError("error", "Detalles: " + e.getMessage()));
        }
    }

    // Actualizar
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar usuario", description = "Modifica atributos del usuario por su ID")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200", description = "Usuario actualizado",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "400", description = "ID invalido",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Usuario.class)))
    })
    public ResponseEntity<EntityModel<?>> actualizarUsuario(@PathVariable("id") Long id, @RequestBody Usuario usuario) {
        try {
            Usuario actualizado = usuarioService.actualizarUsuario(id, usuario);
            if (actualizado == null) {
                return ResponseEntity
                    .badRequest()
                    .contentType(MediaTypes.HAL_JSON)
                    .body(assembler.wrapError("error", "Usuario con ID " + id + " no existe"));
            }
            return ResponseEntity
                .ok()
                .contentType(MediaTypes.HAL_JSON)
                .body(assembler.toModel(actualizado));
        } catch (Exception e) {
            return ResponseEntity
                .internalServerError()
                .contentType(MediaTypes.HAL_JSON)
                .body(assembler.wrapError("error", "Detalles: " + e.getMessage()));
        }
    }

    // Eliminar
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar usuario", description = "Borra el usuario indicado por ID")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200", description = "Usuario eliminado",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Usuario.class)))
    })
    public ResponseEntity<EntityModel<Map<String, String>>> eliminarUsuario(@PathVariable("id") Long id) {
        try {
            boolean eliminado = usuarioService.eliminarUsuario(id); // tu servicio debería confirmar si existía

            if (!eliminado) {
                return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaTypes.HAL_JSON)
                    .body(assembler.wrapError("error", "Usuario con ID " + id + " no existe"));
            }

            Map<String, String> mensaje = Map.of("mensaje", "Usuario eliminado correctamente");
            EntityModel<Map<String, String>> respuesta = EntityModel.of(mensaje,
                linkTo(methodOn(UsuarioController.class).obtenerTodosUsuarios()).withRel("usuarios")
            );

            return ResponseEntity
                .ok()
                .contentType(MediaTypes.HAL_JSON)
                .body(respuesta);

        } catch (Exception e) {
            return ResponseEntity
                .internalServerError()
                .contentType(MediaTypes.HAL_JSON)
                .body(assembler.wrapError("error", "Detalles: " + e.getMessage()));
        }
    }
}
