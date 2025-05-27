package com.petsocity.petsocity.service;

import com.petsocity.petsocity.model.Carrito;
import com.petsocity.petsocity.model.EstadoCarrito;
import com.petsocity.petsocity.repository.CarritoRepository;
import com.petsocity.petsocity.repository.DetalleCarritoRepository;
import com.petsocity.petsocity.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;
    @Autowired
    private DetalleCarritoRepository detalleCarritoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    // Obtener todos los carritos registrados
    public List<Carrito> obtenerTodos() {
        return carritoRepository.findAll();
    }

    // Obtener un carrito por su ID
    public Optional<Carrito> obtenerPorId(Long id) {
        return carritoRepository.findById(id);
    }

    // Crear un nuevo carrito
    public Carrito crearCarrito(Carrito carrito) {
        return carritoRepository.save(carrito);
    }

    // Actualizar un carrito existente
    public Carrito actualizarCarrito(Long id, Carrito carritoNuevo) {
        Optional<Carrito> carritoOptional = carritoRepository.findById(id);

        if (carritoOptional.isPresent()) {
            Carrito carritoExistente = carritoOptional.get();

            carritoExistente.setEstado(carritoNuevo.getEstado());
            carritoExistente.setUsuario(carritoNuevo.getUsuario());
            carritoExistente.setFechaCreacion(carritoNuevo.getFechaCreacion());

            return carritoRepository.save(carritoExistente);
        }

        return carritoNuevo;
    }

    // Eliminar un carrito por su ID
    public void eliminarCarrito(Long id) {
        carritoRepository.deleteById(id);
    }

    // Obtener carritos asociados a un usuario
    public List<Carrito> obtenerPorUsuarioId(Long usuarioId) {
        return carritoRepository.findByUsuarioId(usuarioId);
    }

    // Obtener carritos por estado
    public List<Carrito> obtenerPorEstado(EstadoCarrito estado) {
        return carritoRepository.findByEstado(estado);
    }

    // Preguntar al alan que pasa con esto
    // Obtener carritos de un usuario con un estado específico
    public List<Carrito> obtenerPorUsuarioYEstado(Long usuarioId, EstadoCarrito estado) {
        return carritoRepository.findByUsuarioIdAndEstado(usuarioId, estado);
    }
}
