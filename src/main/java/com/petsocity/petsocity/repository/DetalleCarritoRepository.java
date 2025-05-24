package com.petsocity.petsocity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.petsocity.petsocity.model.DetalleCarrito;

public interface DetalleCarritoRepository extends JpaRepository<DetalleCarrito,Long>{
        //Traer por el id del usuario
        List<DetalleCarrito>findByUsuarioId(long usuarioid);

            // Obtener todos los productos de un carrito
        List<DetalleCarrito> findByCarritoId(Long carritoId);

        // Buscar todos los carritos que contienen un producto específico
        List<DetalleCarrito> findByInventarioId(Long inventarioId);

        // Buscar si un carrito ya contiene cierto producto
        DetalleCarrito findByCarritoIdAndInventarioId(Long carritoId, Long inventarioId);
}
