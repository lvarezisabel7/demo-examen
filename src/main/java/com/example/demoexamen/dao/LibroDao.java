package com.example.demoexamen.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demoexamen.entities.Libro;

public interface LibroDao extends JpaRepository<Libro, Integer> {


}
