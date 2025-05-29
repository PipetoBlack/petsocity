package com.petsocity.petsocity.repository;

import java.util.List;
import java.util.Optional;

import com.petsocity.petsocity.model.DetalleCarrito;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleCarritoRepository extends JpaRepository<DetalleCarrito, Long> {

    // Obtener todos los productos de un carrito específico
    @Query("SELECT d FROM DetalleCarrito d WHERE d.carrito.id = :carritoId")
    List<DetalleCarrito> buscarPorCarritoId(@Param("carritoId") Long carritoId);

    // Obtener todos los registros que contienen un producto específico en cualquier carrito
    @Query("SELECT d FROM DetalleCarrito d WHERE d.inventarioId = :inventarioId")
    List<DetalleCarrito> buscarPorInventarioId(@Param("inventarioId") Long inventarioId);

    // Verificar si un producto ya está en un carrito específico
    @Query("SELECT d FROM DetalleCarrito d WHERE d.carrito.id = :carritoId AND d.inventarioId = :inventarioId")
    Optional<DetalleCarrito> buscarPorCarritoIdEInventarioId(
        @Param("carritoId") Long carritoId,
        @Param("inventarioId") Long inventarioId
    );
}
