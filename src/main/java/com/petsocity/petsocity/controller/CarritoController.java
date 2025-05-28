package com.petsocity.petsocity.controller;

import java.util.List;
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
    @GetMapping("/listacarrito")
    public List<Carrito> obtenerTodosCarritos() {
        return carritoService.obtenerTodosCarritos();
    }

    // Leer por id
    @GetMapping("/{id}")
    public ResponseEntity<Carrito> obtenerCarritoPorId(@PathVariable Long id) {
        return carritoService.obtenerPorIdCarrito(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // crear
    @PostMapping("/carrito")
    public ResponseEntity<Carrito> crearCarrito(@RequestBody Carrito carrito) {
        return ResponseEntity.ok(carritoService.crearCarrito(carrito));
    }

    // actualizar
    @PutMapping("/{id}")
    public Carrito actualizarCarrito(@PathVariable Long id, @RequestBody Carrito carrito) {
        return carritoService.actualizarCarrito(id, carrito);
    }      

    // eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCarrito(@PathVariable Long id) {
        carritoService.eliminarCarrito(id);
        return ResponseEntity.noContent().build();
    }

    // leer carrito asociado al id
    @GetMapping("/usuario/{usuarioId}")
    public List<Carrito> obtenerCarritosPorUsuario(@PathVariable Long usuarioId) {
        return carritoService.obtenerPorUsuarioId(usuarioId);
    }

    // leer estado
    @GetMapping("/estado/{estado}")
    public List<Carrito> obtenerCarritosPorEstado(@PathVariable EstadoCarrito estado) {
        return carritoService.obtenerPorEstado(estado);
    }

    // leer usuario y estado
    @GetMapping("/usuario/{usuarioId}/estado/{estado}")
    public List<Carrito> obtenerCarritosPorUsuarioYEstado(@PathVariable Long usuarioId, @PathVariable EstadoCarrito estado) {
        return carritoService.obtenerPorUsuarioYEstado(usuarioId, estado);
    }
}