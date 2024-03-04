package com.example.demoexamen.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demoexamen.entities.Autor;

public interface AutorDao extends JpaRepository<Autor, Integer> {


    List<Autor> findByLibrosId(int libroId);
    
}
