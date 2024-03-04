package com.example.demoexamen.services;

import java.util.List;
import java.util.Set;

import com.example.demoexamen.entities.Autor;

public interface AutorService {

    public List<Autor> findAll();
    public Autor findById(int id);
    public void save(Autor autor);
    public void delete(Autor autor);

}
