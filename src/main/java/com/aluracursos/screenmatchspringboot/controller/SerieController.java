package com.aluracursos.screenmatchspringboot.controller;

import com.aluracursos.screenmatchspringboot.dto.SerieDTO;
import com.aluracursos.screenmatchspringboot.model.Serie;
import com.aluracursos.screenmatchspringboot.repository.SerieRepository;
import com.aluracursos.screenmatchspringboot.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/series")//esta anotacion identifica que es la url basica, es decir este es el principio de todas nuetros end points
public class SerieController {
    @Autowired
    private SerieService servicio;

    @GetMapping("/")
    public List<SerieDTO> obtenerTodasLasSeries(){
        return servicio.obtenerTodasLasSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> obtenerTop5(){
        return servicio.obtenerTop5();
    }

    @GetMapping("/lanzamientos")
    public List<SerieDTO> obtenerLanzamientosMasRecientes(){
        return servicio.obtenerLanzamientosMasRecientes();
    }

    @GetMapping("/{id}")//se usa las llaves para idicar una variable id que cambia, tambien en el metrodo con la anotacion
    //path variable se necesita indicar el tipo de dato del parametro ademas del nombre del parametro
    //y en la llamada al metedo , se le envia el id que se va a recibir por get en la pagina web.
    public SerieDTO obtenerPorId(@PathVariable Long id){
        return servicio.obtenerPorId(id);
    }

    @GetMapping("/inicio")
    public String muestraMensaje(){
        return "Probando el LiveReloading de SpringBoot cambio2";
    }
}
