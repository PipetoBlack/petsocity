package com.petsocity.petsocity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.petsocity.petsocity.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria,Long>{
   
   @Query("SELECT c FROM Categoria c WHERE c.nombre LIKE '%:nombre%'")
   List<Categoria> existsByName(String nombre);
}
