package com.example.demoexamen.utilities;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demoexamen.entities.Autor;
import com.example.demoexamen.entities.Libro;
import com.example.demoexamen.services.AutorService;
import com.example.demoexamen.services.LibroService;

@Configuration
public class LoadSampleData {

      @Bean
    public CommandLineRunner saveSampleData(LibroService libroService, AutorService autorService) {

        return datos -> {

            // Autores
            Autor autor1 = Autor.builder()
                .nombre("Javier Castillo")
                .build();
            
            Autor autor2 =Autor.builder()
                .nombre("Loreto Sesma")
                .build();
                

            // Libros
            Libro libro1 = Libro.builder()
                .titulo("El día que se perdió la cordura")
                .date(LocalDate.of(2016, 3, 12))
                .build();
            
            Libro libro2 = Libro.builder()
                .titulo("El día que se perdió el amor")
                .date(LocalDate.of(2016, 3, 12))
                .build();

            Libro libro3 =Libro.builder()
                .titulo("Amor revolver")
                .date(LocalDate.of(2012, 9, 10))
                .build();

                libro1.addAutor(autor1);
                libro2.addAutor(autor1);
                libro3.addAutor(autor2);
                libro3.addAutor(autor1);

                libroService.save(libro1);
                libroService.save(libro2);
                libroService.save(libro3);


        };
    }

}
