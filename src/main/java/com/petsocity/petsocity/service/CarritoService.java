package com.petsocity.petsocity.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.petsocity.petsocity.model.Carrito;
import com.petsocity.petsocity.model.DetalleCarrito;
import com.petsocity.petsocity.model.EstadoCarrito;
import com.petsocity.petsocity.model.Usuario;
import com.petsocity.petsocity.repository.CarritoRepository;
import com.petsocity.petsocity.repository.DetalleCarritoRepository;
import com.petsocity.petsocity.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

//import org.springframework.beans.factory.annotation.Autowired;

@Service
@Transactional
public class CarritoService {
    private final CarritoRepository carritoRepository;
    private final DetalleCarritoRepository detalleCarritoRepository;
    private final UsuarioRepository usuarioRepository;

    //@Autowired
    public CarritoService(CarritoRepository carritoRepository,
                        DetalleCarritoRepository detalleCarritoRepository,
                        UsuarioRepository usuarioRepository){
            this.carritoRepository = carritoRepository;
            this.detalleCarritoRepository = detalleCarritoRepository;
            this.usuarioRepository = usuarioRepository;
    }

    // Metodos carrito
    public List<Carrito> obtenerTodosCarritos(){
        return carritoRepository.findAll();
    }

    public Optional<Carrito> obtenerPorIdCarrito(Long id){
        return carritoRepository.findById(id);
    }

    public Carrito crearCarrito(Carrito carrito){
        return carritoRepository.save(carrito);
    }

    public Carrito actualizarCarrito(Long id, Carrito nuevoCarrito){
        return carritoRepository.findById(id).map(carrito -> {
            carrito.setEstado(nuevoCarrito.getEstado());
            carrito.setUsuario(nuevoCarrito.getUsuario());
            carrito.setFechaCreacion(nuevoCarrito.getFechaCreacion());
            return carritoRepository.save(carrito);
        }).orElseThrow(()-> new RuntimeException("Carrito no encontrado"));
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

    // Metodos  DetalleCarrito
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

    // Metodos usuario
    public List<Usuario> obtenerTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerPorIdUsuario(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario crearUsuario(Usuario usuario) {
        if (usuario.getId() != null) {
            throw new RuntimeException("El ID debe ser nulo");
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario actualizarUsuario(Long id, Usuario datosActualizados) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setPrimerNombre(datosActualizados.getPrimerNombre());
            usuario.setSegundoNombre(datosActualizados.getSegundoNombre());
            usuario.setPrimerApellido(datosActualizados.getPrimerApellido());
            usuario.setSegundoApellido(datosActualizados.getSegundoApellido());
            usuario.setEmail(datosActualizados.getEmail());
            usuario.setDireccion(datosActualizados.getDireccion());
            usuario.setRol(datosActualizados.getRol());
            return usuarioRepository.save(usuario);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}