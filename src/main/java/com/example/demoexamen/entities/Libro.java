package com.example.demoexamen.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "libros")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Libro implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "The title cannot be empty")
    private String titulo;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
        })
    @JoinTable(name = "libro_autores",
        joinColumns = { @JoinColumn(name = "libro_id")},
        inverseJoinColumns = { @JoinColumn(name = "autor_id")
    })
    @Builder.Default
    private Set<Autor> autores = new HashSet<>();

    public void addAutor(Autor autor) {
        this.autores.add(autor);
        autor.getLibros().add(this);
    }
    
    // Metodo para eliminar el autor de un libro
    public void deleteAutor(int autorId) {
        Autor autor = this.autores.stream().filter(p -> p.getId() == autorId).findFirst().orElse(null);
        if (autor != null) {
          this.autores.remove(autor); // eliminas el autor
          autor.getLibros().remove(this); // eliminas el autor del libro
        }
    }
}
