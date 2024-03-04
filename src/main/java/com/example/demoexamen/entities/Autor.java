package com.example.demoexamen.entities;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "autores")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Autor implements Serializable {

    private static final long serialVersionUID = 1L;

}
