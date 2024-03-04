package com.example.demoexamen.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demoexamen.entities.Autor;
import com.example.demoexamen.entities.Libro;

import java.time.LocalDate;
import java.util.List;


public interface LibroDao extends JpaRepository<Libro, Integer> {

    Libro findById(int id);
    List<Libro> findLibrosByAutoresId(int id);

    
    List<Libro> findByAutoresAndDate(Autor autor, LocalDate date);

}
