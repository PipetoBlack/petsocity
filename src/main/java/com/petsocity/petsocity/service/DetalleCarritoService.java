package com.petsocity.petsocity.service;

import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;

import com.petsocity.petsocity.model.DetalleCarrito;
import com.petsocity.petsocity.repository.DetalleCarritoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DetalleCarritoService {
    private final DetalleCarritoRepository detalleCarritoRepository;

    public DetalleCarritoRepository getDetalleCarritoRepository() {
        return detalleCarritoRepository;
    }

    public DetalleCarritoService(DetalleCarritoRepository detalleCarritoRepository) {
        this.detalleCarritoRepository = detalleCarritoRepository;
    }

    // Obtener todos los datos
    public List<DetalleCarrito> obtenerTodos(){
        return detalleCarritoRepository.findAll();
    }

    // Si queremos solo la ID
    public Optional<DetalleCarrito> obtenerSoloID(Long id){
        return detalleCarritoRepository.findById(id);
    }
    //creacion de detalle del carro
    public DetalleCarrito crearDetalleCarrito(DetalleCarrito detalleCarrito){
        List<DetalleCarrito> lcat = detalleCarritoRepository.existsById(detalleCarrito.getId());
        if (lcat.size() > 1 ) {
            return detalleCarrito;
        }
        return detalleCarritoRepository.save(detalleCarrito);
    }
    //Actualizar
    public DetalleCarrito actualizardDetalleCarrito(long id, DetalleCarrito datosActualizados){
        return detalleCarritoRepository.findById(id).map(detalleCarrito->{
            detalleCarrito.setCantidad(datosActualizados.getCantidad());
            detalleCarrito.setCarrito(datosActualizados.getCarrito());
            detalleCarrito.setInventario(datosActualizados.getInventario());
            detalleCarrito.setPrecioUnitario(datosActualizados.getPrecioUnitario());
            return detalleCarritoRepository.save(detalleCarrito);

        }).orElseThrow(()->new RuntimeException("detalle no encontrado"));
    }
//------------------------------------------------------------------------
    // eliminar detalledecompra (No creo que sea necesario)
    public void eliminarDetalleCompra(Long id){
        detalleCarritoRepository.deleteById(id);
    }
//------------------------------------------------------------------------
}