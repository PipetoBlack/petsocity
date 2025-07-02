package com.petsocity.petsocity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorModel {

    private String error;
    private String detalles;
    private int status;
    private String path;
    private LocalDateTime timestamp;
}