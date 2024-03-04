package com.example.demoexamen.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demoexamen.entities.Libro;
import java.util.List;


public interface LibroDao extends JpaRepository<Libro, Integer> {

    Libro findById(int id);

}
