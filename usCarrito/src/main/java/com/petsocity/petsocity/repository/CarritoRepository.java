package com.petsocity.petsocity.repository;

import com.petsocity.petsocity.model.Carrito;
import com.petsocity.petsocity.model.EstadoCarrito;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {


    @Query("SELECT c FROM Carrito c WHERE c.usuarioId = :usuarioId")
    List<Carrito> buscarPorUsuarioId(@Param("usuarioId") Long usuarioId);

    @Query("SELECT c FROM Carrito c WHERE c.estado = :estado")
    List<Carrito> buscarPorEstado(@Param("estado") EstadoCarrito estado);

    @Query("SELECT c FROM Carrito c WHERE c.usuarioId = :usuarioId AND c.estado = :estado")
    List<Carrito> buscarPorUsuarioYEstado(
        @Param("usuarioId") Long usuarioId,
        @Param("estado") EstadoCarrito estado
    );
}
