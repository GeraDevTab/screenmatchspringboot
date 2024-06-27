package com.aluracursos.screenmatchspringboot.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//se mapea con una anotacion para que la libreria json lea el nombre original y lo asigne al nombre que estamos colocando en el parametro
//json alias sirve para leer datos
//json properties es para leer y escribir

@JsonIgnoreProperties(ignoreUnknown = true) //esta anotacion es para que ignore los demas atributos que no estamos llamando en el metodo
public record DatosSerie(@JsonAlias("Title")  String titulo,
                         @JsonAlias("totalSeasons") Integer totalDeTemporadas,
                         @JsonAlias("imdbRating") String evaluacion) {
}
