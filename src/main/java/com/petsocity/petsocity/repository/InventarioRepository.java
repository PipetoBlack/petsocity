package com.petsocity.petsocity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.petsocity.petsocity.model.Inventario;

public interface InventarioRepository extends JpaRepository<Inventario,Long>{
    // nuevo
    List<Inventario> findByName(String nombreProducto);

}
