package com.petsocity.petsocity.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petsocity.petsocity.integrations.InventarioIntegration;
import com.petsocity.petsocity.model.DetalleCarrito;
import com.petsocity.petsocity.model.InventarioDTO;
import com.petsocity.petsocity.repository.DetalleCarritoRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class DetalleCarritoService {

    @Autowired
    private final DetalleCarritoRepository detalleCarritoRepository;
    private final InventarioIntegration inventarioIntegration;

    public DetalleCarritoService(DetalleCarritoRepository detalleCarritoRepository, InventarioIntegration inventarioIntegration) {
        this.detalleCarritoRepository = detalleCarritoRepository;
        this.inventarioIntegration = inventarioIntegration;
    }

    public List<DetalleCarrito> obtenerTodosDetalles() {
        return detalleCarritoRepository.findAll();
    }

    public Optional<DetalleCarrito> obtenerPorIDDetalle(Long id) {
        return detalleCarritoRepository.findById(id);
    }

    public DetalleCarrito crearDetalleCarrito(DetalleCarrito detalleCarrito) {
        if (detalleCarritoRepository.existsById(detalleCarrito.getId())) {
            throw new RuntimeException("El detalle ya existe.");
        }
        return detalleCarritoRepository.save(detalleCarrito);
    }

    // PREGUNTAR AL PROFESOOOOOR 
    public DetalleCarrito actualizarDetalleCarrito(long id, DetalleCarrito datosActualizados) {
        return detalleCarritoRepository.findById(id).map(detalleCarrito -> {
            if (datosActualizados.getCantidad() != null) {
                detalleCarrito.setCantidad(datosActualizados.getCantidad());
            }
            if (datosActualizados.getCarrito() != null) {
                detalleCarrito.setCarrito(datosActualizados.getCarrito());
            }
            if (datosActualizados.getInventarioId() != null) {
                detalleCarrito.setInventarioId(datosActualizados.getInventarioId());
            
                // Aquí llamamos a InventarioIntegration para traer el DTO
                InventarioDTO producto = inventarioIntegration.obtenerInventarioPorId(datosActualizados.getInventarioId());
            
                if (producto != null) {
                    // Por ejemplo, actualizamos el precio unitario con el precio del producto
                    detalleCarrito.setPrecioUnitario(producto.getPrecio());
                }
            }
            if (datosActualizados.getPrecioUnitario() != null) {
                detalleCarrito.setPrecioUnitario(datosActualizados.getPrecioUnitario());
            }
            return detalleCarritoRepository.save(detalleCarrito);
        }).orElseThrow(() -> new RuntimeException("Detalle no encontrado"));
    }


    public void eliminarDetalleCarrito(Long id) {
        detalleCarritoRepository.deleteById(id);
    }
}
