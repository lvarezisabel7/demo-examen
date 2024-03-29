package com.example.demoexamen.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demoexamen.dao.AutorDao;
import com.example.demoexamen.entities.Autor;
import com.example.demoexamen.entities.Libro;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutorServiceImpl implements AutorService {

    private final AutorDao autorDao;

    @Override
    public List<Autor> findAll() {
       return autorDao.findAll();
    }

    @Override
    public Autor findAutorById(int id) {
        return autorDao.findAutorById(id);
    }

    @Override
    public Autor save(Autor autor) {
       return autorDao.save(autor);
    }

    @Override
    public void delete(Autor autor) {
        autorDao.delete(autor);
    }

    @Override
    public List<Autor> findAutoresByLibroId(int id) {
        return autorDao.findByLibrosId(id);
    }

    @Override
    public boolean libroAssociatedWithAutor(int autorId, int libroId) {

       Optional<Autor> optionalAutor = autorDao.findById(autorId);

       if(optionalAutor.isPresent()) {
        Autor autor = optionalAutor.get();
             Set<Libro> libros = autor.getLibros();
        
        for (Libro libro : libros) {
            if (libro.getId() == libroId) {
                return true; // El autor está asociado al libro
            }
       }
    }

    return false; // El autor no está asociado al libro

    }

    @Override
    public Page<Autor> findAll(Pageable pageable) {
        return autorDao.findAll(pageable);
    }

    @Override
    public List<Autor> findAll(Sort sort) {
        return autorDao.findAll(sort);
    }

    
}
