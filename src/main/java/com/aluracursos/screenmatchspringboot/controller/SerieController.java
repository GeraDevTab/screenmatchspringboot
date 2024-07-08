package com.aluracursos.screenmatchspringboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SerieController {
    @GetMapping("/series")
    public String mostrarMensajes(){
        return "Hola Gerardo en curso de Alura";
    }
}
