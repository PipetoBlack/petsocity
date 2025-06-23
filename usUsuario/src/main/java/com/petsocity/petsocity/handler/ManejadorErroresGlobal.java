/*package com.petsocity.petsocity.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ManejadorErroresGlobal {

@ExceptionHandler(Exception.class)
public ResponseEntity<Map<String, String>> fallback(Exception ex) {
    Map<String, String> respuesta = new HashMap<>();
    respuesta.put("error", "Ha ocurrido un error inesperado");
    return ResponseEntity.internalServerError().body(respuesta);
}
}
*/