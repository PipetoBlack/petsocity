package com.petsocity.petsocity.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petsocity.petsocity.model.Categoria;
import com.petsocity.petsocity.model.Inventario;
import com.petsocity.petsocity.repository.CategoriaRepository;
import com.petsocity.petsocity.repository.InventarioRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;


    public InventarioService(InventarioRepository inventarioRepository){
        this.inventarioRepository = inventarioRepository;
    }

    //obtener todos
    public List<Inventario> obtenerTodos(){
        return inventarioRepository.findAll();
    }

    // Obtener por ID
    public Optional<Inventario> obtenerPorId(Long id){
        return inventarioRepository.findById(id);
    }

    // Obtener por nombre
    public List<Inventario> buscarPorNombre(String nombre) {
    return inventarioRepository.findByNombreProducto(nombre);
    }

    public List<Inventario> obtenerPorCategoriaId(Long id) {
        return inventarioRepository.buscarPorCategoriaId(id);
    }

    // Crear
    public Inventario crearInventario(Inventario inventario) {
        if (inventario.getCategoria() == null || inventario.getCategoria().getId() == null) {
            throw new RuntimeException("Debe especificarse una categoría válida.");
        }

        var categoria = categoriaRepository.findById(inventario.getCategoria().getId())
                .orElseThrow(() -> new RuntimeException("La categoría no existe."));

        inventario.setCategoria(categoria); // Asociar entidad gestionada
        return inventarioRepository.save(inventario);
    }

    
   public Inventario actualizarInventario(Long id, Inventario datosActualizados) {
    return inventarioRepository.findById(id).map(inventario -> {

        if (datosActualizados.getNombreProducto() != null) {
            inventario.setNombreProducto(datosActualizados.getNombreProducto());
        }

        if (datosActualizados.getDescripcion() != null) {
            inventario.setDescripcion(datosActualizados.getDescripcion());
        }

        if (datosActualizados.getPrecio() != null) {
            inventario.setPrecio(datosActualizados.getPrecio());
        }

        if (datosActualizados.getStockActual() != null) {
            inventario.setStockActual(datosActualizados.getStockActual());
        }

        if (datosActualizados.getCategoria() != null && datosActualizados.getCategoria().getId() != null) {
            Categoria categoria = categoriaRepository.findById(datosActualizados.getCategoria().getId())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + datosActualizados.getCategoria().getId()));
            inventario.setCategoria(categoria);
        }

        return inventarioRepository.save(inventario);
    }).orElseThrow(() -> new RuntimeException("Inventario no encontrado"));
}


    // eliminar con validacion por si no existe la id
    public void eliminarInventario(Long id) {
        inventarioRepository.deleteById(id);
    }

}
