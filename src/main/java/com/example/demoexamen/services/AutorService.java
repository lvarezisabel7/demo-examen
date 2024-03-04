package com.example.demoexamen.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.example.demoexamen.entities.Autor;

public interface AutorService {

    public List<Autor> findAll();
    public List<Autor> findAutoresByLibroId(int id);
    public Autor findById(int id);
    public Autor save(Autor autor);
    public void delete(Autor autor);
    public boolean libroAssociatedWithAutor(int autorId, int libroId);


}
