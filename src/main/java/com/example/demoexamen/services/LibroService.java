package com.example.demoexamen.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.demoexamen.entities.Libro;

public interface LibroService {


    public Page<Libro> findAll(Pageable pageable);
    public List<Libro> findAll(Sort sort);
    public List<Libro> findAll();
    public Libro findById(int id);
    List<Libro> findLibrosByAutorId(int id);
    public Libro save(Libro libro);
    public void delete(Libro libro);

}
