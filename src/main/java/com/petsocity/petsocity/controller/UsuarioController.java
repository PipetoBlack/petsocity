package com.petsocity.petsocity.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.petsocity.petsocity.model.Usuario;
import com.petsocity.petsocity.service.UsuarioService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    // Leer todo
    @GetMapping
    public List<Usuario> obtenerTodos(){
        return usuarioService.obtenerTodos();
    }

    // leer por id obtenerPorId(id)
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable Long id){
        Optional<Usuario> usuario = usuarioService.obtenerPorId(id);
        return usuario.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

    // Crear
    @PostMapping("")
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario){
        return ResponseEntity.ok(usuarioService.crearUsuario(usuario));
    }
    
    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario){
        return ResponseEntity.ok(usuarioService.actualizarUsuario(id,usuario));
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id){
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

}
