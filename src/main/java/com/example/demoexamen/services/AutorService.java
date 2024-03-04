package com.example.demoexamen.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.demoexamen.entities.Autor;


public interface AutorService {

    public List<Autor> findAll();
    public Page<Autor> findAll(Pageable pageable);
    public List<Autor> findAll(Sort sort);
    public List<Autor> findAutoresByLibroId(int id);
    Autor findAutorById(int id);
    public Autor save(Autor autor);
    public void delete(Autor autor);
    public boolean libroAssociatedWithAutor(int autorId, int libroId);


}
