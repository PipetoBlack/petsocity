package com.petsocity.petsocity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.petsocity.petsocity.model.Inventario;

public interface InventarioRepository extends JpaRepository<Inventario,Long>{
    @Query("SELECT i FROM Inventario i WHERE i.nombreProducto LIKE %:nombre%")
    List<Inventario> findByNombreProducto(String nombre);

    @Query("SELECT i FROM Inventario i WHERE i.categoria.id = :categoriaId")
    List<Inventario> buscarPorCategoriaId(Long categoriaId);
}
