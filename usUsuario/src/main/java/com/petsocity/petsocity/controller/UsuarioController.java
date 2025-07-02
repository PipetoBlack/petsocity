package com.petsocity.petsocity.controller;

import com.petsocity.petsocity.assemblers.UsuarioModelAssembler;
import com.petsocity.petsocity.model.ApiErrorModel;
import com.petsocity.petsocity.model.Usuario;
import com.petsocity.petsocity.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.MediaType;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuarios", description = "Operaciones CRUD de usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioModelAssembler assembler;

    public UsuarioController(UsuarioService usuarioService, UsuarioModelAssembler assembler) {
        this.usuarioService = usuarioService;
        this.assembler = assembler;
    }

    // Leer todo
    @GetMapping(produces = {MediaTypes.HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Obtener lista completa de usuarios", description = "Retorna todos los usuarios registrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
            description = "Operacion exitosa",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "400", 
            description = "Error al obtener usuarios",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ApiErrorModel.class)))
    })
    public CollectionModel<EntityModel<Usuario>> obtenerTodosUsuarios() {
        List<Usuario> usuarios = usuarioService.obtenerTodosUsuarios();
        return assembler.toCollection(usuarios);
    }

    // Leer por ID
    @GetMapping(value = "/{id}", produces = {MediaTypes.HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Obtener un usuario por ID", description = "Busca un usuario especifico usando su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
            description = "Usuario encontrado",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "404", 
            description = "Usuario no encontrado",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = ApiErrorModel.class)))
    })
    public ResponseEntity<EntityModel<?>> obtenerUsuarioPorId(@PathVariable (name = "id") Long id) {
        Usuario usuario = usuarioService.obtenerPorIdUsuario(id);

        if (usuario == null) {
            ApiErrorModel error = new ApiErrorModel(
                "Usuario no encontrado",
                "No existe el usuario con ID " + id,404,
                "/api/v1/usuarios/" + id,
                LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaTypes.HAL_JSON)
                .body(assembler.wrapError(error));
        }
        return ResponseEntity.ok(assembler.toModel(usuario));
    }

    // Crear usuario
    @PostMapping(produces = {MediaTypes.HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Crear usuario", description = "Registra un nuevo usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", 
            description = "Usuario creado",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "400", 
            description = "Datos invalidos o duplicados",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = ApiErrorModel.class)))
    })
    public ResponseEntity<EntityModel<?>> crearUsuario(@RequestBody @Valid Usuario usuario) {
        try {
            Usuario creado = usuarioService.crearUsuario(usuario);
            URI location = linkTo(methodOn(UsuarioController.class).obtenerUsuarioPorId(creado.getId())).toUri();
            return ResponseEntity.created(location)
                .contentType(MediaTypes.HAL_JSON)
                .body(assembler.toModel(creado));
        } catch (IllegalArgumentException e) {
            ApiErrorModel error = new ApiErrorModel(
                "Error de validacion",
                e.getMessage(), 400,
                "/api/v1/usuarios",
                LocalDateTime.now()
            );
            return ResponseEntity.badRequest()
                .contentType(MediaTypes.HAL_JSON)
                .body(assembler.wrapError(error));
        }
    }

    // Actualizar usuario
    @PutMapping(value = "/{id}", produces = {MediaTypes.HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Actualizar usuario", description = "Modifica atributos del usuario por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
            description = "Usuario actualizado",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "400", 
            description = "ID invalido",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = ApiErrorModel.class)))
    })
    public ResponseEntity<EntityModel<?>> actualizarUsuario(@PathVariable(name = "id") Long id, @RequestBody Usuario usuario) {

        Usuario actualizado = usuarioService.actualizarUsuario(id, usuario);
        if (actualizado == null) {
            ApiErrorModel error = new ApiErrorModel(
                "Usuario no encontrado",
                "No se pudo actualizar el usuario con ID " + id,400,
                "/api/v1/usuarios/" + id,
                LocalDateTime.now()
            );
            return ResponseEntity.badRequest()
                .contentType(MediaTypes.HAL_JSON)
                .body(assembler.wrapError(error));
        }
        return ResponseEntity.ok()
            .contentType(MediaTypes.HAL_JSON)
            .body(assembler.toModel(actualizado));
    }

    // Borrar usuario por ID
    @DeleteMapping(value = "/{id}", produces = {MediaTypes.HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Eliminar usuario", description = "Borra el usuario indicado por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
            description = "Usuario eliminado",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "404", 
            description = "Usuario no encontrado",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = ApiErrorModel.class)))
    })
    public ResponseEntity<?> eliminarUsuario(@PathVariable(name = "id") Long id) {
        boolean eliminado = usuarioService.eliminarUsuario(id);

        if (!eliminado) {
            ApiErrorModel error = new ApiErrorModel(
                "Usuario no encontrado",
                "No se pudo eliminar el usuario con ID " + id,404,
                "/api/v1/usuarios/" + id,
                LocalDateTime.now()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaTypes.HAL_JSON)
                .body(assembler.wrapError(error));
        }
        Map<String, String> mensaje = Map.of("mensaje", "Usuario eliminado correctamente");
        EntityModel<Map<String, String>> respuesta = EntityModel.of(mensaje,
            linkTo(methodOn(UsuarioController.class).obtenerTodosUsuarios()).withRel("usuarios")
        );
        return ResponseEntity.ok()
            .contentType(MediaTypes.HAL_JSON)
            .body(respuesta);
    }
}