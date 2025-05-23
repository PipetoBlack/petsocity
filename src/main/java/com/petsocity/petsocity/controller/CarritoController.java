package com.petsocity.petsocity.controller;

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
import com.petsocity.petsocity.service.CarritoService;

@RestController
@RequestMapping("/petsocity/carrito")

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

}
