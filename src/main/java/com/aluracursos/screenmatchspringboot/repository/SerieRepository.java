package com.aluracursos.screenmatchspringboot.repository;

import com.aluracursos.screenmatchspringboot.model.Categoria;
import com.aluracursos.screenmatchspringboot.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie,Long> {
    Optional<Serie> findByTituloContainsIgnoreCase(String nombreSerie);
    List<Serie> findTop5ByOrderByEvaluacionDesc();

    List<Serie> findByGenero(Categoria categoria);

    List<Serie> findByTotalTemporadasLessThanEqualAndEvaluacionGreaterThanEqual(Integer totalTemporadas,Double evaluacion);
    //@Query(value = "SELECT * FROM series WHERE series.total_temporadas <= 6 AND series.evaluacion >= 7.5", nativeQuery = true) //este es un ejemplo de utlizacion de un native query
    @Query("SELECT s FROM Serie s WHERE s.totalTemporadas <= :totalTemporadas AND s.evaluacion >= :evaluacion") //se usan los dos puntos para diferenciar el atributo de la clase y el parametro
    List<Serie> seriesPorTemporadaYEvaluacion(Integer totalTemporadas,Double evaluacion);
}
