package com.example.demoexamen.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demoexamen.entities.Autor;
import com.example.demoexamen.entities.Libro;
import com.example.demoexamen.expception.ResourceNotFoundException;
import com.example.demoexamen.services.AutorService;
import com.example.demoexamen.services.LibroService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AutorController {

    private final AutorService autorService;
    private final LibroService libroService;

    // Metodo para recuperar los libros con paginacion y ordenamiento
    @GetMapping("/autores")
    public ResponseEntity<List<Autor>> findAll(
        @RequestParam(name = "page", required = false) Integer page,
        @RequestParam(name = "size", required = false) Integer size) {

            ResponseEntity<List<Autor>> responseEntity = null;
            Sort sortByName = Sort.by("nombre");
            List<Autor> autores = new ArrayList<>();

            // comprobamos si se han enviado page y size
        if (page != null && size != null) { // si esta condicion se cumple es que quieren el producto paginado
            // queremos devolver los productos paginados
            Pageable pageable = PageRequest.of(page, size, sortByName);
            Page<Autor> pageAutores = autorService.findAll(pageable);
            autores = pageAutores.getContent();
            responseEntity = new ResponseEntity<List<Autor>>(autores, HttpStatus.OK);
        } else {
            autores = autorService.findAll(sortByName);
            responseEntity = new ResponseEntity<List<Autor>>(autores, HttpStatus.OK);

        }

        return responseEntity;
    }

    // Metodo para recuperar todos los autores de un libro
    @GetMapping("/libros/{libroId}/autores")
    public ResponseEntity<List<Autor>> getAllAutoresByLibroId(
        @PathVariable(value = "libroId") int libroId) {

            Libro libro = libroService.findById(libroId);
            if(libro == null) {
                throw new ResourceNotFoundException("Not found Libro with Id = " + libroId);
            }

            List<Autor> autores = autorService.findAutoresByLibroId(libroId);
            return new ResponseEntity<>(autores, HttpStatus.OK);
        }

    // Metodo para recuperar un autor por su id
    @GetMapping("/autores/{autorId}")
    public ResponseEntity<Autor> getLibroWithAutores(
        @PathVariable(value = "autorId") int autorId) {

            Autor autor = autorService.findAutorById(autorId);
            if(autor == null) {
                throw new ResourceNotFoundException("Not found Autor with Id = " + autorId);
            } else {

                return new ResponseEntity<>(autor, HttpStatus.OK);
            }

            
        }

    // Metodo para recuperar los libros de un autor con fecha de publicacion post o igual a la
    // fecha especificada
    @GetMapping("/autores/{autorId}/libros/fecha-publicacion/{date}")
    public ResponseEntity<List<Libro>> getLibrosWithDate(
        @PathVariable(value = "autorId") int autorId,
        @PathVariable(value = "date") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date) {
            
            Autor autor = autorService.findAutorById(autorId);
            if(autor == null) {
                throw new ResourceNotFoundException("Not found Autor with Id = " + autorId);
            } 
        
            List<Libro> libros = libroService.findLibrosByAutorAndFechaPublicacionAfterOrEqual(autor, date);
        
            return new ResponseEntity<>(libros, HttpStatus.OK);
        }



    // Metodo para actualizar un autor por su id
    @PutMapping("/autores/{autorId}")
    public ResponseEntity<Autor> updateAutor(
        @PathVariable(value = "autorId") int autorId,
        @RequestBody Autor autor) {
            
           Autor autorUpdate = autorService.findAutorById(autorId);
            if(autorUpdate == null) {
                throw new ResourceNotFoundException("Not found Autor with Id = " + autorId);
            } 

            autorUpdate.setNombre(autor.getNombre());
            autorUpdate.setLibros(autor.getLibros());
            

            return new ResponseEntity<>(autorService.save(autorUpdate), HttpStatus.OK);

        }

    // Metodo para eliminar un autor por su id
    @DeleteMapping("/autores/{autorId}")
    public ResponseEntity<Map<String, Object>> deleteAutor(
        @PathVariable(value = "autorId") int autorId) {

            Map<String, Object> responseMap = new HashMap<>();
            ResponseEntity<Map<String, Object>> responseEntity = null;

            try {
                Autor autor = autorService.findAutorById(autorId);
                if (autor == null) {
                    String errorMessage = "No se encontró el autor con id " + autorId;
                    responseMap.put("errorMessage", errorMessage);
                    responseEntity = new ResponseEntity<>(responseMap, HttpStatus.NOT_FOUND);
                    return responseEntity;

            } else {
                Set<Libro> libros = autor.getLibros();
                for (Libro libro : libros) {
                    libro.getAutores().remove(autor);
                }
                autorService.delete(autor);
                String successMessage = "Autor con id " + autorId + " eliminado exitosamente";
                responseMap.put("successMessage", successMessage);
                responseEntity = new ResponseEntity<Map<String,Object>>(responseMap, HttpStatus.OK);
            }
        } catch (DataAccessException e) {
            String error = "Error al eliminar el autor con id " + autorId + " y la causa mas probable es: " + e.getMostSpecificCause();
            responseMap.put("error", error);
            responseEntity = new ResponseEntity<Map<String,Object>>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;


    }
    

}
