package com.petsocity.petsocity.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.petsocity.petsocity.model.Usuario;
import com.petsocity.petsocity.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuarios", description = "Operaciones CRUD de usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Leer todo
    @GetMapping("")
    @Operation(summary = "Obtener lista completa de usuarios", description = "Se obtiene la lista de todo los usuarios")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200", description = "Operación exitosa",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "400", description = "No se puede generar la lista de usuarios",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Usuario.class)))
    })
    public List<Usuario> obtenerTodosUsuarios() {
        return usuarioService.obtenerTodosUsuarios();
    }

    // Leer por id
    @GetMapping("/{id}")
    @Operation(summary = "Obtener un usuario por su ID", description = "Se obtiene solo un usuario por el ID")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200", description = "Operación exitosa",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "400", description = "ID no encontrado",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Usuario.class)))
    })
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable("id") Long id) {
        return usuarioService.obtenerPorIdUsuario(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // crear
    @PostMapping("")
    @Operation(summary = "Creacion de usuarios", description = "Se crea un usuario nuevo")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200", description = "Operación exitosa",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "400", description = "¡Correo duplicado!. Recuerda mantener los campos de ID y FechaCreacion vacias",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Usuario.class)))
    })
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario) {
        try{
            Usuario creado = usuarioService.crearUsuario(usuario);
            return ResponseEntity.ok(creado);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        //return ResponseEntity.ok(usuarioService.crearUsuario(usuario));
    }

    // actualizar
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un usuario por su ID", description = "Se puede actualizar uno o varios atributos del usuario por su ID")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200", description = "Operación exitosa",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "400", description = "ID no existe",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Usuario.class)))
    })
    public Usuario actualizarUsuario(@PathVariable("id") Long id, @RequestBody Usuario usuario) {
        return usuarioService.actualizarUsuario(id, usuario);
    }

    // eliminar
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario por su ID", description = "Se elimina usuario por ID")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200", description = "Operación exitosa",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "400", description = "ID no existe",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Usuario.class)))
    })
    public ResponseEntity<Map<String, String>> eliminarUsuario(@PathVariable("id") Long id) {
        usuarioService.eliminarUsuario(id);
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Usuario eliminado correctamente");
        return ResponseEntity.ok(response);
    }
}
