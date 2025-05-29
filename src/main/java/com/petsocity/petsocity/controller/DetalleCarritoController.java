package com.petsocity.petsocity.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.petsocity.petsocity.model.DetalleCarrito;
import com.petsocity.petsocity.service.DetalleCarritoService;

@RestController
@RequestMapping("/api/v1/detallecarrito")
public class DetalleCarritoController {

    private final DetalleCarritoService detalleCarritoService;

    public DetalleCarritoController(DetalleCarritoService detalleCarritoService) {
        this.detalleCarritoService = detalleCarritoService;
    }

    // Leer todo
    @GetMapping("")
    public List<DetalleCarrito> obtenerTodosDetalles() {
        return detalleCarritoService.obtenerTodosDetalles();
    }

    // Leer por id
    @GetMapping("/{id}")
    public ResponseEntity<DetalleCarrito> obtenerDetallePorId(@PathVariable Long id) {
        return detalleCarritoService.obtenerPorIDDetalle(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // crear
    @PostMapping("")
    public ResponseEntity<DetalleCarrito> crearDetalle(@RequestBody DetalleCarrito detalleCarrito) {
        return ResponseEntity.ok(detalleCarritoService.crearDetalleCarrito(detalleCarrito));
    }

    // actualizar
    @PutMapping("/{id}")
    public DetalleCarrito actualizarDetalle(@PathVariable Long id, @RequestBody DetalleCarrito detalleCarrito) {
        return detalleCarritoService.actualizarDetalleCarrito(id, detalleCarrito);
    }
    
    // eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDetalle(@PathVariable Long id) {
        detalleCarritoService.eliminarDetalleCarrito(id);
        return ResponseEntity.noContent().build();
    }
}
