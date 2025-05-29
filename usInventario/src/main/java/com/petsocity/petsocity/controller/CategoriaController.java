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

import com.petsocity.petsocity.model.Categoria;
import com.petsocity.petsocity.service.CategoriaService;



@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaController {
private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    // Leer todo
    @GetMapping("")
    public List<Categoria> listarCategorias() {
        return categoriaService.obtenerTodas();
    }

    // Leer por id
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtenerPorId(@PathVariable("id") Long id) {
        return categoriaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // crear
    @PostMapping("")
    public Categoria crearCategoria(@RequestBody Categoria categoria) {
        return categoriaService.crearCategoria(categoria);
    }

    // actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizarCategoria(@PathVariable("id") Long id, @RequestBody Categoria categoria) {
        Map<String, Object> response = new HashMap<>();

        if (categoria.getNombre() == null || categoria.getNombre().trim().isEmpty()) {
            response.put("error", "El nombre es obligatorio.");
            return ResponseEntity.badRequest().body(response);
        }
        Categoria categoriaActualizada = categoriaService.actualizarCategoria(id, categoria);

        response.put("mensaje", "Categoría actualizada correctamente.");
        response.put("categoria", categoriaActualizada);

        return ResponseEntity.ok(response);
    }

    // eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminarCategoria(@PathVariable("id") Long id) {
        Map<String, String> response = new HashMap<>();
        categoriaService.eliminarCategoria(id);
        response.put("mensaje", "Categoria eliminado correctamente");
        return ResponseEntity.ok(response);
    }

    /*¿Qué hace?
    Llama al servicio para eliminar la categoría con el id que llega en la URL.
    Luego crea un Map con un mensaje personalizado.
    Devuelve ese mensaje como respuesta (en formato JSON) al cliente.*/
}
