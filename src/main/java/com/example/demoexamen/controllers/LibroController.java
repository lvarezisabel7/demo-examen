package com.example.demoexamen.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
public class LibroController {

    private final LibroService libroService;
    private final AutorService autorService;

    // Metodo para recuperar todos los libros correspondientes a un autor
     @GetMapping("/autores/{autorId}/libros")
    public ResponseEntity<List<Libro>> getAllLibrosByAutorId(
        @PathVariable(value = "autorId") int autorId) {

            Autor autor = autorService.findById(autorId);
            if(autor == null) {
                throw new ResourceNotFoundException("Not found Autor with Id = " + autorId);
            } 

            List<Libro> libros = libroService.findLibrosByAutorId(autorId);
            return new ResponseEntity<>(libros, HttpStatus.OK);  
        }

    // Metodo para recuperar un libro con sus autores
    @GetMapping("/libros/{libroId}")
    public ResponseEntity<Libro> getLibroWithAutores(
        @PathVariable(value = "libroId") int libroId) {

            Libro libro = libroService.findById(libroId);
            if(libro == null) {
                throw new ResourceNotFoundException("Not found Libro with Id = " + libroId);
            } else {

                return new ResponseEntity<>(libro, HttpStatus.OK);
            }

            
        }

    // Metodo para recuperar los libros con paginacion y ordenamiento
    
    
    // Metodo para añadir un autor a un libro
    @PostMapping("/libros/{libroId}/autores")
    public ResponseEntity<Libro> addAutorByLibroId(
        @PathVariable(value = "libroId") int libroId,
        @RequestBody Autor autor) {
        
            Libro libro = libroService.findById(libroId);
            if(libro == null) {
                throw new ResourceNotFoundException("Not found Libro with Id = " + libroId);

            }

            libro.addAutor(autor);
            libroService.save(libro);

            return new ResponseEntity<>(libro, HttpStatus.OK);
    }

    // Metodo para eliminar un libro por su id
    @DeleteMapping("/libros/{libroId}")
    public ResponseEntity<Map<String, Object>> deleteLibro(
        @PathVariable(value = "libroId") int libroId) {

            Map<String, Object> responseMap = new HashMap<>();
            ResponseEntity<Map<String, Object>> responseEntity = null;

        try {
            Libro libro = libroService.findById(libroId);
            if (libro == null) {
                String errorMessage = "No se encontró el libro con id " + libroId;
                responseMap.put("errorMessage", errorMessage);
                responseEntity = new ResponseEntity<>(responseMap, HttpStatus.NOT_FOUND);
                return responseEntity;

            } else {
            libroService.delete(libro);
            String successMessage = "Libro con id " + libroId + " eliminado exitosamente";
            responseMap.put("successMessage", successMessage);
            responseEntity = new ResponseEntity<Map<String,Object>>(responseMap, HttpStatus.OK);
            }
        } catch (DataAccessException e) {
            String error = "Error al eliminar el libro con id " + libroId + " y la causa mas probable es: " + e.getMostSpecificCause();
            responseMap.put("error", error);
            responseEntity = new ResponseEntity<Map<String,Object>>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;


    }
    
    // Metodo para eliminar un libro correspondiente a un autor
    @DeleteMapping("/libros/{libroId}/autores/{autorId}")
    public ResponseEntity<Map<String, Object>>  deleteLibroFromAutor(
        @PathVariable(value = "autorId") int autorId, @PathVariable(value = "libroId") int libroId) {

            Map<String, Object> responseMap = new HashMap<>();
            ResponseEntity<Map<String, Object>> responseEntity = null;

            try {
                // Verificar si el autor con el id proporcionado existe
                Autor autor = autorService.findById(autorId);
                if(autor == null){
                    String errorMessage = "No se encontró el autor con id " + autorId;
                    responseMap.put("errorMessage", errorMessage);
                    responseEntity = new ResponseEntity<>(responseMap, HttpStatus.NOT_FOUND);
                    return responseEntity;
                }

                // Verificar si el autor esta asociado al libro con el id proporcionado
                boolean isAssociated = autorService.libroAssociatedWithAutor(autorId, libroId);
                if (!isAssociated) {
                    String errorMessage = "El autor con id " + autorId + " no está asociado al libro con id " + libroId;
                    responseMap.put("errorMessage", errorMessage);
                    responseEntity = new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
                    return responseEntity;
                }

                // Eliminamos el libro del autor
                autor.deleteLibro(libroId);
                autorService.save(autor);
                String successMessage = "Libro con id " + libroId + " eliminado exitosamente del autor " + autorId;
                responseMap.put("successMessage", successMessage);
                responseEntity = new ResponseEntity<Map<String,Object>>(responseMap, HttpStatus.OK);
                
            } catch (DataAccessException e) {
                String error = "Error al eliminar el libro con id " + libroId + " y la causa mas probable es: " + e.getMostSpecificCause();
                responseMap.put("error", error);
                responseEntity = new ResponseEntity<Map<String,Object>>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return responseEntity;

        }
    
        
        
}
