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

    public InventarioService(InventarioRepository inventarioRepository, CategoriaRepository categoriaRepository){
        this.inventarioRepository = inventarioRepository;
        this.categoriaRepository = categoriaRepository;
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
        inventarioRepository.deleteById(id);
    }

}
