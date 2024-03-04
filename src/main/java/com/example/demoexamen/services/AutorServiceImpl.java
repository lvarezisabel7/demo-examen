package com.example.demoexamen.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demoexamen.dao.AutorDao;
import com.example.demoexamen.entities.Autor;

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
    public Autor findById(int id) {
        return autorDao.findById(id).get();
    }

    @Override
    public void save(Autor autor) {
        autorDao.save(autor);
    }

    @Override
    public void delete(Autor autor) {
        autorDao.delete(autor);
    }

}
