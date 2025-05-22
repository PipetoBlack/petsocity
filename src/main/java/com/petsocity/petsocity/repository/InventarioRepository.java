package com.petsocity.petsocity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.petsocity.petsocity.model.Inventario;

public interface InventarioRepository extends JpaRepository<Inventario,Long>{
    // Traer por nombre
    List<Inventario> findByName(String name);

    // conexion de categoria con los productos del inventario
    List<Inventario> findByCategoriaId(Long categoriaId);

}
