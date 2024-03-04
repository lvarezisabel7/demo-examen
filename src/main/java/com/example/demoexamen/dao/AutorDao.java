package com.example.demoexamen.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demoexamen.entities.Autor;

public interface AutorDao extends JpaRepository<Autor, Integer> {

    Optional<Autor> findById(int id);
    Autor findAutorById(int id);
    List<Autor> findByLibrosId(int libroId);
    
}
