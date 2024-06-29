package com.aluracursos.screenmatchspringboot.principal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EjemploStreams {
    public void muestraEjemplo(){
        List<String> nombres = Arrays.asList("Brenda","luis","maria fernanda","Eric","Genesys");
        nombres.stream()
                .sorted()
                .limit(5)
                .filter(n -> n.startsWith("l"))
                .map(n -> n.toUpperCase())
                .forEach(System.out::println);
    }
}
