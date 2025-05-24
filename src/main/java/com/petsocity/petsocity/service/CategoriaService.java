package com.petsocity.petsocity.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.petsocity.petsocity.model.Categoria;
import com.petsocity.petsocity.repository.CategoriaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> obtenerTodas() {
        return categoriaRepository.findAll();
    }

    public Optional<Categoria> obtenerPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    public Categoria crearCategoria(Categoria categoria) {
        
        List<Categoria> lcat = categoriaRepository.existsByName(categoria.getNombre());
        if (lcat.size() > 1 ) {
            return categoria;
        }
        return categoriaRepository.save(categoria);
    }

    public Categoria actualizarCategoria(Long id, Categoria nuevaCategoria) {
        return categoriaRepository.findById(id).map(categoria -> {
            categoria.setNombre(nuevaCategoria.getNombre());
            return categoriaRepository.save(categoria);
        }).orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }

    public void eliminarCategoria(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new RuntimeException("Categoría no encontrada");
        }
        categoriaRepository.deleteById(id);
    }
}
