package com.petsocity.petsocity.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "Campo obligatorio")
    @Column(name = "primer_nombre" ,nullable = false)
    private String primerNombre;

    @NotBlank(message = "Campo obligatorio")
    @Column(name = "segundo_nombre", nullable = false)
    private String segundoNombre;

    @NotBlank(message = "Campo obligatorio")
    @Column(name = "primer_apellido", nullable = false)
    private String primerApellido;

    @NotBlank(message = "Campo obligatorio")
    @Column(name = "segundo_apellido",nullable = false)
    private String segundoApellido;
    
    @Email(message = "El correo no tiene un formato valido")
    @Pattern(
        regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
        message = "Debe tener el formato nombre@dominio.com")
    @NotBlank(message = "Campo obligatorio")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Campo obligatorio")
    @Size(min = 6, max = 255, message = "La contraseña debe tener al menos 6 caracteres")
    @Column(name = "contrasenia", nullable = false)
    private String contrasenia;

    @NotBlank(message = "Campo obligatorio")
    @Column(name = "direccion", nullable = false, columnDefinition = "TEXT")
    private String direccion;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;
}
