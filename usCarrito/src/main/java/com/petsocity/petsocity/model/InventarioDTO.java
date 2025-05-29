package com.petsocity.petsocity.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventarioDTO {

    private Long id;
    private String nombreProducto;
    private String descripcion;
    private BigDecimal precio;
    private Integer stockActual;
    private LocalDateTime fechaCreacion;
}
