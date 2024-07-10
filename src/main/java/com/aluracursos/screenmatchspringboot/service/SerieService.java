package com.aluracursos.screenmatchspringboot.service;

import com.aluracursos.screenmatchspringboot.dto.SerieDTO;
import com.aluracursos.screenmatchspringboot.model.Serie;
import com.aluracursos.screenmatchspringboot.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {

    @Autowired
    private SerieRepository repositorio;
    public List<SerieDTO> obtenerTodasLasSeries(){
        return convierteDatosParaStream(repositorio.findAll());
    }

    public List<SerieDTO> obtenerTop5() {
        return convierteDatosParaStream(repositorio.findTop5ByOrderByEvaluacionDesc());
    }

    public List<SerieDTO> convierteDatosParaStream(List<Serie> serie){
        return serie.stream()
                .map(s -> new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getEvaluacion(),
                        s.getPoster(), s.getGenero(), s.getActores(), s.getSinopsis()))
                .collect(Collectors.toList());
    }

    public List<SerieDTO> obtenerLanzamientosMasRecientes(){
        return convierteDatosParaStream(repositorio.lanzamientoMasReciente());
    }

    public SerieDTO obtenerPorId(Long id){
        Optional<Serie> serie = repositorio.findById(id);
        if(serie.isPresent()){
            Serie s = serie.get();
            return new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getEvaluacion(),
                s.getPoster(), s.getGenero(), s.getActores(), s.getSinopsis());
        }
        return null;
    }
}
