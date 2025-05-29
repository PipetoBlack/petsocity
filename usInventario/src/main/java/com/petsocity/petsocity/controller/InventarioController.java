package com.petsocity.petsocity.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@RequestMapping("/api/v1/inventarios")
public class InventarioController {

    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService){
        this.inventarioService = inventarioService;
    }

    // Leer todo
    @GetMapping("")
    public List<Inventario> obtenerTodos(){
        return inventarioService.obtenerTodos();
    }

    // leer por id obtenerPorId(id)
    @GetMapping("/{id}")
    public ResponseEntity<Inventario> obtenerPorId(@PathVariable("id") Long id){
        Optional<Inventario> inventario = inventarioService.obtenerPorId(id);
        return inventario.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

    // Buscar por categoria
    @GetMapping("/categoria/{id}")
    public List<Inventario> obtenerPorCategoria(@PathVariable("id") Long categoriaId) {
        return inventarioService.obtenerPorCategoriaId(categoriaId);
    }

    // Crear
    @PostMapping("")
    public ResponseEntity<Inventario> crearInventario(@RequestBody Inventario inventario){
        return ResponseEntity.ok(inventarioService.crearInventario(inventario));
    }
    
    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Inventario> actualizarInventario(@PathVariable("id") Long id, @RequestBody Inventario inventario){
        return ResponseEntity.ok(inventarioService.actualizarInventario(id, inventario));
    }


    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminarProductoInventario(@PathVariable("id") Long id){
        Map<String, String> response = new HashMap<>();
        inventarioService.eliminarInventario(id);
        response.put("mensaje", "Categoria eliminado correctamente");
        return ResponseEntity.ok(response);
    }
}
