package com.petsocity.petsocity.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.petsocity.petsocity.model.Usuario;
import com.petsocity.petsocity.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

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
        if (usuarioRepository.existsByEmail(usuario.getEmail())){
            throw new IllegalArgumentException("El correo ingresado ya esta registrado");
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario actualizarUsuario(Long id, Usuario datosActualizados) {
        return usuarioRepository.findById(id).map(usuario -> {
            if (datosActualizados.getPrimerNombre() != null) {
                usuario.setPrimerNombre(datosActualizados.getPrimerNombre());
            }
            if (datosActualizados.getSegundoNombre() != null) {
                usuario.setSegundoNombre(datosActualizados.getSegundoNombre());
            }
            if (datosActualizados.getPrimerApellido() != null) {
                usuario.setPrimerApellido(datosActualizados.getPrimerApellido());
            }
            if (datosActualizados.getSegundoApellido() != null) {
                usuario.setSegundoApellido(datosActualizados.getSegundoApellido());
            }
            if (datosActualizados.getEmail() != null) {
                usuario.setEmail(datosActualizados.getEmail());
            }
            if (datosActualizados.getDireccion() != null) {
                usuario.setDireccion(datosActualizados.getDireccion());
            }
            if (datosActualizados.getContrasenia() != null){
                usuario.setContrasenia(datosActualizados.getContrasenia());
            }
            return usuarioRepository.save(usuario);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}
