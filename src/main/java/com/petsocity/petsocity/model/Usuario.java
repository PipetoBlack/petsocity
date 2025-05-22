package com.petsocity.petsocity.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pnombre" ,nullable = false)
    private String primerNombre;

    @Column(name = "snombre", nullable = false)
    private String segundoNombre;
    
    @Column(name = "papellido", nullable = false)
    private String primerApellido;

    @Column(name = "sapellido",nullable = false)
    private String segundoApellido;
    
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    
    @Column(name = "contrasenia", nullable = false)
    private String contrasenia;

    @Column(name = "direccion", nullable = false, columnDefinition = "TEXT")
    private String direccion;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private Rol rol;

    @Column(name = "fecha_creacion", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false, insertable = false)
    private LocalDateTime fechaCreacion;
}
