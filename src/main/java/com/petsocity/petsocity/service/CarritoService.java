package com.petsocity.petsocity.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.petsocity.petsocity.model.Carrito;
import com.petsocity.petsocity.model.EstadoCarrito;
import com.petsocity.petsocity.repository.CarritoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CarritoService {

    private final CarritoRepository carritoRepository;

    public CarritoService(CarritoRepository carritoRepository) {
        this.carritoRepository = carritoRepository;
    }

    // Obtener todos los carritos
    public List<Carrito> obtenerTodos() {
        return carritoRepository.findAll();
    }

    // Obtener carrito por ID
    public Optional<Carrito> obtenerPorId(Long id) {
        return carritoRepository.findById(id);
    }

    // Crear nuevo carrito
    public Carrito crearCarrito(Carrito carrito) {
        return carritoRepository.save(carrito);
    }

    // Actualizar carrito existente
    public Carrito actualizarCarrito(Long id, Carrito nuevoCarrito) {
        return carritoRepository.findById(id).map(carrito -> {
            carrito.setEstado(nuevoCarrito.getEstado());
            carrito.setUsuario(nuevoCarrito.getUsuario());
            carrito.setFechaCreacion(nuevoCarrito.getFechaCreacion());
            return carritoRepository.save(carrito);
        }).orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
    }

    // Eliminar carrito por ID
    public void eliminarCarrito(Long id) {
        carritoRepository.deleteById(id);
    }

    // Obtener carritos por la ID de usuario
    public List<Carrito> obtenerPorUsuarioId(Long usuarioId) {
        return carritoRepository.buscarPorUsuarioId(usuarioId);
    }

    // Obtener carritos por estado
    public List<Carrito> obtenerPorEstado(EstadoCarrito estado) {
        return carritoRepository.buscarPorEstado(estado);
    }

    // Obtener carritos por usuario y estado
    public List<Carrito> obtenerPorUsuarioYEstado(Long usuarioId, EstadoCarrito estado) {
        return carritoRepository.buscarPorUsuarioYEstado(usuarioId, estado);
    }
}
