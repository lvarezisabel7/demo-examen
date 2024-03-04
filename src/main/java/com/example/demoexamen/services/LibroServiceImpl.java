package com.example.demoexamen.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demoexamen.dao.LibroDao;
import com.example.demoexamen.entities.Autor;
import com.example.demoexamen.entities.Libro;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LibroServiceImpl implements LibroService{

    private final LibroDao libroDao;

    @Override
    public Page<Libro> findAll(Pageable pageable) {
        return libroDao.findAll(pageable);
    }

    @Override
    public List<Libro> findAll(Sort sort) {
       return  libroDao.findAll(sort);
    }

    @Override
    public List<Libro> findAll() {
        return libroDao.findAll();
    }

    @Override
    public Libro findById(int id) {
        return libroDao.findById(id);
    }

    @Override
    public Libro save(Libro libro) {
       return libroDao.save(libro);
    }

    @Override
    public void delete(Libro libro) {
        libroDao.delete(libro);
    }

    @Override
    public List<Libro> findLibrosByAutorId(int id) {
        return libroDao.findLibrosByAutoresId(id);
    }

    @Override
    public List<Libro> findLibrosByAutorAndFechaPublicacionAfterOrEqual(Autor autor, LocalDate date) {
        return libroDao.findByAutoresAndDate(autor, date);
    }

    

}
