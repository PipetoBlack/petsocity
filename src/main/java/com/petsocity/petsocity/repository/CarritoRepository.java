package com.petsocity.petsocity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.petsocity.petsocity.model.Carrito;
import com.petsocity.petsocity.model.EstadoCarrito;

public interface CarritoRepository extends JpaRepository<Carrito,Long>{
    // Trae los carritos asociados al usuario
    List<Carrito> findByUsuarioId(Long usuarioId);

    // Trae los carritos de acuerdo al estado
    List<Carrito> findByEstado(EstadoCarrito estado);

    // Trae los carritos de acuerdo al id del usuario, y al estado
    List<Carrito> findByUsuarioIdAndEstado(Long usuarioId, EstadoCarrito estado);
}
