package com.petsocity.petsocity.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.petsocity.petsocity.model.DetalleCarrito;
import com.petsocity.petsocity.repository.DetalleCarritoRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class DetalleCarritoService {

    @Autowired
    private final DetalleCarritoRepository detalleCarritoRepository;

    public DetalleCarritoService(DetalleCarritoRepository detalleCarritoRepository) {
        this.detalleCarritoRepository = detalleCarritoRepository;
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

    public DetalleCarrito actualizarDetalleCarrito(long id, DetalleCarrito datosActualizados) {
        return detalleCarritoRepository.findById(id).map(detalleCarrito -> {
            detalleCarrito.setCantidad(datosActualizados.getCantidad());
            detalleCarrito.setCarrito(datosActualizados.getCarrito());
            detalleCarrito.setInventario(datosActualizados.getInventario());
            detalleCarrito.setPrecioUnitario(datosActualizados.getPrecioUnitario());
            return detalleCarritoRepository.save(detalleCarrito);
        }).orElseThrow(() -> new RuntimeException("Detalle no encontrado"));
    }

    public void eliminarDetalleCarrito(Long id) {
        detalleCarritoRepository.deleteById(id);
    }
}
