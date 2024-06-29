package com.aluracursos.screenmatchspringboot.principal;

import com.aluracursos.screenmatchspringboot.model.DatosEpisodio;
import com.aluracursos.screenmatchspringboot.model.DatosSerie;
import com.aluracursos.screenmatchspringboot.model.DatosTemporadas;
import com.aluracursos.screenmatchspringboot.service.ConsumoAPI;
import com.aluracursos.screenmatchspringboot.service.ConvierteDatos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "http://www.omdbapi.com/?&t=";
    private final String API_KEY = "&apikey=4830f843";
    private ConvierteDatos conversor = new ConvierteDatos();
    public void muestraElMenu(){
        System.out.println("Por favor escribe el nombre de la serie que deseas buscar");
        //Busca los datos generales de las series
        var nombreSerie = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE+nombreSerie.replace(" ","+")+API_KEY);

        //aqui puede ser recibir cualquier clase, y en este caso ponemos datosSerie
        var datos = conversor.obtenerDatos(json, DatosSerie.class);
        System.out.println(json);
        System.out.println(datos);

        //Busca los datos de todas las temporadas
        List<DatosTemporadas> temporadas = new ArrayList<>(); // se crea un arraylist para almacenar objetos DatosTemporadas
        for (int i = 1; i <= datos.totalDeTemporadas(); i++) {
            json = consumoAPI.obtenerDatos(URL_BASE+nombreSerie.replace(" ","+")+"&Season="+i+API_KEY);
            var datosTemporadas = conversor.obtenerDatos(json, DatosTemporadas.class);
            temporadas.add(datosTemporadas);
        }
        temporadas.forEach(System.out::println);

        //Mostrar solo el titulo de los episodios parar las temporadas
        for (int i = 0; i < datos.totalDeTemporadas(); i++) {
            List<DatosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
            for (int j = 0; j < episodiosTemporada.size(); j++) {
                System.out.println(episodiosTemporada.get(j).titulo());

            }
        }
        //mejoria usando funcuines lambda
        temporadas.forEach(t  -> t.episodios().forEach(e -> System.out.println(e.titulo())));
    }
}
