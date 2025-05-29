package com.petsocity.petsocity.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.petsocity.petsocity.model.Carrito;
import com.petsocity.petsocity.model.EstadoCarrito;
import com.petsocity.petsocity.service.CarritoService;

@RestController
@RequestMapping("/api/v1/carritos")
public class CarritoController {

    private final CarritoService carritoService;

    public CarritoController(CarritoService carritoService){
        this.carritoService = carritoService;
    }

    // Leer todo
    @GetMapping("")
    public List<Carrito> obtenerTodosCarritos() {
        return carritoService.obtenerTodosCarritos();
    }

    // Leer por id
    @GetMapping("/{id}")
    public ResponseEntity<Carrito> obtenerCarritoPorId(@PathVariable("id") Long id) {
        return carritoService.obtenerPorIdCarrito(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // crear
    @PostMapping("")
    public ResponseEntity<Carrito> crearCarrito(@RequestBody Carrito carrito) {
        return ResponseEntity.ok(carritoService.crearCarrito(carrito));
    }

    // actualizar
    @PutMapping("/{id}")
    public Carrito actualizarCarrito(@PathVariable("id") Long id, @RequestBody Carrito carrito) {
        return carritoService.actualizarCarrito(id, carrito);
    }      

    // eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminarCarrito(@PathVariable("id") Long id) {
        Map<String, String> response = new HashMap<>();
        carritoService.eliminarCarrito(id);
        response.put("mensaje", "Carrito eliminado correctamente");
        return ResponseEntity.ok(response);
    }

    // leer carrito asociado al id
    @GetMapping("/usuario/{usuarioId}")
    public List<Carrito> obtenerCarritosPorUsuario(@PathVariable("usuarioId") Long usuarioId) {
        return carritoService.obtenerPorUsuarioId(usuarioId);
    }

    // leer estado
    @GetMapping("/estado/{estado}")
    public List<Carrito> obtenerCarritosPorEstado(@PathVariable("estado") EstadoCarrito estado) {
        return carritoService.obtenerPorEstado(estado);
    }

    // leer usuario y estado
    @GetMapping("/usuario/{usuarioId}/estado/{estado}")
    public List<Carrito> obtenerCarritosPorUsuarioYEstado(@PathVariable("usuarioId") Long usuarioId, @PathVariable("estado") EstadoCarrito estado) {
        return carritoService.obtenerPorUsuarioYEstado(usuarioId, estado);
    }
}