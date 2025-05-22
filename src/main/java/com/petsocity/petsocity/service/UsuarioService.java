package com.petsocity.petsocity.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.petsocity.petsocity.model.Usuario;
import com.petsocity.petsocity.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    // Get all
    public List<Usuario> obtenerTodos(){
        return usuarioRepository.findAll();
    }

    // Get por ID
    public Optional<Usuario> obtenerPorId(Long id){
        return usuarioRepository.findById(id);
    }

    // Crear
    public Usuario crearUsuario(Usuario usuario){
        if(usuario.getId() != null){
            throw new RuntimeException("El ID debe ser nulo")
        }
        return usuarioRepository.save(usuario);
    }

    // actualizar
    public Usuario actualizarUsuario(Long id Usuario datosActualizados){
        return usuarioRepository.findById(id).map(usuario ->{
            usuario.setNombre(datosActualizados.getNombre());
            usuario.setEmail(datosActualizados.getEmail());
        })
    }

    // eliminar
    public void eliminarUsuario(Long id){
        usuarioRepository.deleteById(id);
    }

    
    //borrar este comentari
}
