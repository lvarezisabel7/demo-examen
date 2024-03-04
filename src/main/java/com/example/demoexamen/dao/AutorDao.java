package com.example.demoexamen.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demoexamen.entities.Autor;

public interface AutorDao extends JpaRepository<Autor, Integer> {

}
