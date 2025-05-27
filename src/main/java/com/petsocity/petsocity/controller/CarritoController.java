package com.petsocity.petsocity.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.petsocity.petsocity.model.Carrito;
import com.petsocity.petsocity.model.DetalleCarrito;
import com.petsocity.petsocity.model.EstadoCarrito;
import com.petsocity.petsocity.model.Usuario;
import com.petsocity.petsocity.service.CarritoService;

@RestController
@RequestMapping("/api/v1/carrito")
public class CarritoController {
    private final CarritoService carritoService;

    public CarritoController(CarritoService carritoService){
        this.carritoService = carritoService;
    }

    // Metodos de Carrito
    @GetMapping
    public List<Carrito> obtenerTodosCarritos() {
        return carritoService.obtenerTodosCarritos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carrito> obtenerCarritoPorId(@PathVariable Long id) {
        return carritoService.obtenerPorIdCarrito(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Carrito> crearCarrito(@RequestBody Carrito carrito) {
        return ResponseEntity.ok(carritoService.crearCarrito(carrito));
    }

    @PutMapping("/{id}")
    public Carrito actualizarCarrito(@PathVariable Long id, @RequestBody Carrito carrito) {
        return carritoService.actualizarCarrito(id, carrito);
    }      

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCarrito(@PathVariable Long id) {
        carritoService.eliminarCarrito(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Carrito> obtenerCarritosPorUsuario(@PathVariable Long usuarioId) {
        return carritoService.obtenerPorUsuarioId(usuarioId);
    }

    @GetMapping("/estado/{estado}")
    public List<Carrito> obtenerCarritosPorEstado(@PathVariable EstadoCarrito estado) {
        return carritoService.obtenerPorEstado(estado);
    }

    @GetMapping("/usuario/{usuarioId}/estado/{estado}")
    public List<Carrito> obtenerCarritosPorUsuarioYEstado(@PathVariable Long usuarioId, @PathVariable EstadoCarrito estado) {
        return carritoService.obtenerPorUsuarioYEstado(usuarioId, estado);
    }

    // Metodos de Usuario
    @GetMapping("/usuarios")
    public List<Usuario> obtenerTodosUsuarios() {
        return carritoService.obtenerTodosUsuarios();
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Long id) {
        return carritoService.obtenerPorIdUsuario(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/usuario")
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(carritoService.crearUsuario(usuario));
    }

    @PutMapping("/usuario/{id}")
    public Usuario actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        return carritoService.actualizarUsuario(id, usuario);
    }

    @DeleteMapping("/usuario/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        carritoService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    // Metodos de DetalleCarrito
    @GetMapping("/detalles")
    public List<DetalleCarrito> obtenerTodosDetalles() {
        return carritoService.obtenerTodosDetalles();
    }

    @GetMapping("/detalle/{id}")
    public ResponseEntity<DetalleCarrito> obtenerDetallePorId(@PathVariable Long id) {
        return carritoService.obtenerPorIDDetalle(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/detalle")
    public ResponseEntity<DetalleCarrito> crearDetalle(@RequestBody DetalleCarrito detalleCarrito) {
        return ResponseEntity.ok(carritoService.crearDetalleCarrito(detalleCarrito));
    }

    @PutMapping("/detalle/{id}")
    public DetalleCarrito actualizarDetalle(@PathVariable Long id, @RequestBody DetalleCarrito detalleCarrito) {
        return carritoService.actualizarDetalleCarrito(id, detalleCarrito);
    }
    
    @DeleteMapping("/detalle/{id}")
    public ResponseEntity<Void> eliminarDetalle(@PathVariable Long id) {
        carritoService.eliminarDetalleCarrito(id);
        return ResponseEntity.noContent().build();
    }
}













































/*
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petsocity.petsocity.model.Carrito;
import com.petsocity.petsocity.model.EstadoCarrito;
import com.petsocity.petsocity.service.CarritoService;

@RestController
@RequestMapping("/api/v1/carrito")

public class CarritoController {
    private final CarritoService carritoService;

    public CarritoController(CarritoService carritoService){
        this.carritoService = carritoService;
    }

    // Leer todos los carritos
    @GetMapping
    public List<Carrito> obtenerTodos() {
        return carritoService.obtenerTodos();
    }

    // Obtener un carrito por su ID
    @GetMapping("/buscar/{id}")
    public ResponseEntity<Carrito> obtenerPorId(@PathVariable Long id) {
        Optional<Carrito> carrito = carritoService.obtenerPorId(id);
        return carrito.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
}
    // Crear
    @PostMapping("")
    public ResponseEntity<Carrito> crearCarrito(@RequestBody Carrito carrito) {
        return ResponseEntity.ok(carritoService.crearCarrito(carrito));
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Carrito> actualizarCarrito(@PathVariable Long id, @RequestBody Carrito carrito) {
        return ResponseEntity.ok(carritoService.actualizarCarrito(id, carrito));
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCarrito(@PathVariable Long id) {
        carritoService.eliminarCarrito(id);
        return ResponseEntity.noContent().build();
    }

    // Obtener todos los carritos de un usuario
    @GetMapping("/usuario/{usuarioId}")
    public List<Carrito> obtenerPorUsuario(@PathVariable Long usuarioId) {
        return carritoService.obtenerPorUsuarioId(usuarioId);
    }

    // Obtener todos los carritos con un estado específico
    @GetMapping("/estado/{estado}")
    public List<Carrito> obtenerPorEstado(@PathVariable EstadoCarrito estado) {
        return carritoService.obtenerPorEstado(estado);
    }

    // Obtener carritos de un usuario con un estado específico
    @GetMapping("/usuario/{usuarioId}/estado/{estado}")
    public List<Carrito> obtenerPorUsuarioYEstado(@PathVariable Long usuarioId, @PathVariable EstadoCarrito estado) {
        return carritoService.obtenerPorUsuarioYEstado(usuarioId, estado);
    }

}
*/