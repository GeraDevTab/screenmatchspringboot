package com.aluracursos.screenmatchspringboot.dto;

import com.aluracursos.screenmatchspringboot.model.Categoria;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record SerieDTO( Long id,
                        String titulo,
         Integer totalTemporadas,
         Double evaluacion,
         String poster,
        Categoria genero,
        String actores,
        String sinopsis) {
}
