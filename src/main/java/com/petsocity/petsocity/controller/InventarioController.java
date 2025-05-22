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

import com.petsocity.petsocity.model.Inventario;
import com.petsocity.petsocity.service.InventarioService;

@RestController
@RequestMapping("/petsocity/inventario")
public class InventarioController {
    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService){
        this.inventarioService = inventarioService;
    }

    // Leer todo
    @GetMapping
    public List<Inventario> obtenerTodos(){
        return inventarioService.obtenerTodos();
    }

    // leer por id obtenerPorId(id)
    @GetMapping("/buscar/{id}")
    public ResponseEntity<Inventario> obtenerPorId(@PathVariable Long id){
        Optional<Inventario> inventario = inventarioService.obtenerPorId(id);
        return inventario.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

    // Buscar por nombre
    @GetMapping("/buscar/nombre/{nombre}")
    public ResponseEntity<List<Inventario>> buscarPorNombre(@PathVariable String nombre){
        List<Inventario> resultados = inventarioService.buscarPorNombre(nombre);
        if (resultados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(resultados);
    }

    // Buscar por categoria
    @GetMapping("/categoria/{categoriaId}")
    public List<Inventario> obtenerPorCategoria(@PathVariable Long categoriaId) {
        return inventarioService.obtenerPorCategoria(categoriaId);
    }


    // Crear
    @PostMapping("")
    public ResponseEntity<Inventario> crearInventario(@RequestBody Inventario inventario){
        return ResponseEntity.ok(inventarioService.crearInventario(inventario));
    }
    
    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Inventario> actualizarInventario(@PathVariable Long id, @RequestBody Inventario inventario){
        return ResponseEntity.ok(inventarioService.actualizarInventario(id, inventario));
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProductoInventario(@PathVariable Long id){
        inventarioService.eliminarInventario(id);
        return ResponseEntity.noContent().build();
    }

}
