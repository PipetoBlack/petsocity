package com.petsocity.petsocity.repository;

import com.petsocity.petsocity.model.Carrito;
import com.petsocity.petsocity.model.EstadoCarrito;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {

    //  Buscar todos los carritos por ID de usuario
    @Query("SELECT c FROM Carrito c WHERE c.usuario.id = :usuarioId")
    List<Carrito> buscarPorUsuarioId(@org.springframework.data.repository.query.Param("usuarioId") Long usuarioId);

    // Buscar carritos por estado
    @Query("SELECT c FROM Carrito c WHERE c.estado = :estado")
    List<Carrito> buscarPorEstado(@org.springframework.data.repository.query.Param("estado") EstadoCarrito estado);

    // Buscar carritos por usuario y estado
    @Query("SELECT c FROM Carrito c WHERE c.usuario.id = :usuarioId AND c.estado = :estado")
    List<Carrito> buscarPorUsuarioYEstado(
        @org.springframework.data.repository.query.Param("usuarioId") Long usuarioId,
        @org.springframework.data.repository.query.Param("estado") EstadoCarrito estado
    );
}

