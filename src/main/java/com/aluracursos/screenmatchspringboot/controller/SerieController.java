package com.aluracursos.screenmatchspringboot.controller;

import com.aluracursos.screenmatchspringboot.dto.SerieDTO;
import com.aluracursos.screenmatchspringboot.model.Serie;
import com.aluracursos.screenmatchspringboot.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SerieController {
    @Autowired
    private SerieRepository repositorio;
    @GetMapping("/series")
    public List<SerieDTO> obtenerTodasLasSeries(){
        return repositorio.findAll().stream()
                .map(s -> new SerieDTO(s.getTitulo(), s.getTotalTemporadas(), s.getEvaluacion(),
                s.getPoster(), s.getGenero(), s.getActores(), s.getSinopsis()))
                .collect(Collectors.toList());
    }

    @GetMapping("/inicio")
    public String muestraMensaje(){
        return "Probando el LiveReloading de SpringBoot cambio2";
    }
}
