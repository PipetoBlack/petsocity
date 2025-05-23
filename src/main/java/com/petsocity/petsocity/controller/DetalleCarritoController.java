package com.petsocity.petsocity.controller;

import java.util.List;
import java.util.Optional;

import com.petsocity.petsocity.model.DetalleCarrito;
import com.petsocity.petsocity.service.DetalleCarritoService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/petsocity/detalleCarrito")
public class DetalleCarritoController {
    private final DetalleCarritoService detalleCarritoService;

    public DetalleCarritoController(DetalleCarritoService detalleCarritoService) {
        this.detalleCarritoService = detalleCarritoService;
    }

    // leer todos los detalles de carrito
    @GetMapping
    public List<DetalleCarrito> obtenerTodos() {
        return detalleCarritoService.obtenerTodos();
    }

    // leer detalle por su id
    @GetMapping("/buscar/{id}")
    public ResponseEntity<DetalleCarrito> obtenerPorId(@PathVariable Long id) {
        Optional<DetalleCarrito> detalle = detalleCarritoService.obtenerPorId(id);
        return detalle.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear
    @PostMapping("")
    public ResponseEntity<DetalleCarrito> crear(@RequestBody DetalleCarrito detalle) {
        return ResponseEntity.ok(detalleCarritoService.crear(detalle));
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<DetalleCarrito> actualizar(@PathVariable Long id, @RequestBody DetalleCarrito detalle) {
        return ResponseEntity.ok(detalleCarritoService.actualizar(id, detalle));
    }

    // Eliminar 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        detalleCarritoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
