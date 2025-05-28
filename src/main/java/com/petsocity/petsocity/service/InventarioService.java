package com.petsocity.petsocity.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.petsocity.petsocity.model.Inventario;
import com.petsocity.petsocity.repository.InventarioRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

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
    public Inventario crearInventario(Inventario inventario){
        if(inventarioRepository.existsById(inventario.getId()) == true){
            return inventario;
        }
        return inventarioRepository.save(inventario);
    }
    
    // actualiza todos los campos del inventario producto
    public Inventario actualizarInventario(Long id, Inventario datosActualizados) {
        return inventarioRepository.findById(id).map(inventario -> {
            if (datosActualizados.getNombreProducto() != null) {
            inventario.setNombreProducto(datosActualizados.getNombreProducto());
            }
            inventario.setDescripcion(datosActualizados.getDescripcion());
            if (datosActualizados.getDescripcion() != null) {
            }
            if (datosActualizados.getPrecio() != null) {
                inventario.setPrecio(datosActualizados.getPrecio());
            }
            if (datosActualizados.getStockActual() != null) {
                inventario.setStockActual(datosActualizados.getStockActual());
            }
            if (datosActualizados.getCategoria() != null) {
                inventario.setCategoria(datosActualizados.getCategoria());
            }
            return inventarioRepository.save(inventario);
        }).orElseThrow(() -> new RuntimeException("Inventario no encontrado"));
    }

    // eliminar con validacion por si no existe la id
    public void eliminarInventario(Long id) {
        inventarioRepository.deleteById(id);
    }

}
