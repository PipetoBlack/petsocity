package com.petsocity.petsocity.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petsocity.petsocity.model.Carrito;
import com.petsocity.petsocity.model.EstadoCarrito;
import com.petsocity.petsocity.repository.CarritoRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CarritoService {
    @Autowired
    private final CarritoRepository carritoRepository;

    public CarritoService(CarritoRepository carritoRepository) {
        this.carritoRepository = carritoRepository;

    }
    public List<Carrito> obtenerTodosCarritos(){
        return carritoRepository.findAll();
    }

    public Optional<Carrito> obtenerPorIdCarrito(Long id){
        return carritoRepository.findById(id);
    }

    public Carrito crearCarrito(Carrito carrito){
        return carritoRepository.save(carrito);
    }

    public Carrito actualizarCarrito(Long id, Carrito nuevoCarrito) {
        return carritoRepository.findById(id).map(carrito -> {
            if (nuevoCarrito.getEstado() != null) {
                carrito.setEstado(nuevoCarrito.getEstado());
            }
            return carritoRepository.save(carrito);
        }).orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
    }

    public void eliminarCarrito(Long id){
        carritoRepository.deleteById(id);
    }

    public List<Carrito> obtenerPorUsuarioId(Long usuarioId) {
        return carritoRepository.buscarPorUsuarioId(usuarioId);
    }

    public List<Carrito> obtenerPorEstado(EstadoCarrito estado) {
        return carritoRepository.buscarPorEstado(estado);
    }

    public List<Carrito> obtenerPorUsuarioYEstado(Long usuarioId, EstadoCarrito estado) {
        return carritoRepository.buscarPorUsuarioYEstado(usuarioId, estado);
    }

}