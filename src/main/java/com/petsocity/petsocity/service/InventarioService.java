package com.petsocity.petsocity.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.petsocity.petsocity.model.Inventario;
import com.petsocity.petsocity.repository.InventarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class InventarioService {
    private final InventarioRepository inventarioRepository;

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
    return inventarioRepository.findByName(nombre);
    }

    // Obtener todos los productos de una categoría específica
    public List<Inventario> obtenerPorCategoria(Long categoriaId) {
        return inventarioRepository.findByCategoriaId(categoriaId);
    }



    // Crear
    public Inventario crearInventario(Inventario inventario){
        if(inventario.getId() != null){
            throw new RuntimeException("El ID debe ser nulo");
        }
        return inventarioRepository.save(inventario);
    }
    
    // actualiza todos los campos del inventario producto
    public Inventario actualizarInventario(Long id, Inventario datosActualizados){
        return inventarioRepository.findById(id).map(inventario ->{
            inventario.setNombreProducto(datosActualizados.getNombreProducto());
            inventario.setDescripcion(datosActualizados.getDescripcion());
            inventario.setPrecio(datosActualizados.getPrecio());
            inventario.setStockActual(datosActualizados.getStockActual());
            inventario.setCategoria(datosActualizados.getCategoria());
            inventario.setFechaCreacion(datosActualizados.getFechaCreacion());
            return inventarioRepository.save(inventario);
        }).orElseThrow(()-> new RuntimeException("Inventario no encontrado"));
    }

    // eliminar con validacion por si no existe la id
    public void eliminarInventario(Long id) {
        if (!inventarioRepository.existsById(id)) {
            throw new RuntimeException("El inventario con ID " + id + " no existe.");
        }
        inventarioRepository.deleteById(id);
    }

}
