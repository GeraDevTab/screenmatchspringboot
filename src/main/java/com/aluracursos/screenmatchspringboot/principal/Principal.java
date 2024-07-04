package com.aluracursos.screenmatchspringboot.principal;

import com.aluracursos.screenmatchspringboot.model.DatosEpisodio;
import com.aluracursos.screenmatchspringboot.model.DatosSerie;
import com.aluracursos.screenmatchspringboot.model.DatosTemporadas;
import com.aluracursos.screenmatchspringboot.model.Episodio;
import com.aluracursos.screenmatchspringboot.service.ConsumoAPI;
import com.aluracursos.screenmatchspringboot.service.ConvierteDatos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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
        //temporadas.forEach(t  -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        //Convertir todas las informaciones a una lista del tipo DatosEpisodio

        List<DatosEpisodio> datosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                //con el tolist crearia una lista inmutable
                //.toList()
                //se puede seguir agregando mas datos en el collector
                .collect(Collectors.toList());

        //top 5 episodios
        System.out.println("Top 5 episodios");
        datosEpisodios.stream()
                .filter(e -> !e.evaluacion().equalsIgnoreCase("N/A"))
                .peek(e -> System.out.println("Pasando por el primer filytro"+e))
                .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
                .peek(e -> System.out.println("Pasando por el 2do filtro" + e))
                .map(e -> e.titulo().toUpperCase())
                .peek(e -> System.out.println("Pasando por el 3er filytro"+e))
                .limit(5)
                .peek(e -> System.out.println("Pasando por el 4to filtro"+e))
                .forEach(System.out::println);

        //convirtiendo los datos a una lista del tipo Episodio
        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                    .map(d -> new Episodio(t.numero(),d)))
                .collect(Collectors.toList());

        episodios.forEach(System.out::println);

        //busqueda de episodios a partir de x año
        /*System.out.println("Por favor indica el año a partir del cual deseas ver los episodios");
        var fecha = teclado.nextInt();
        teclado.nextLine();

        LocalDate fechaDeBusqueda = LocalDate.of(fecha,1,1);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodios.stream()
                .filter(e -> e.getFechaDeLanzamiento() != null && e.getFechaDeLanzamiento().isAfter(fechaDeBusqueda))
                .forEach(e -> System.out.println(
                        "Temporada "+e.getNumeroTemporada()+
                                " Episodio "+e.getTitulo()+
                                " Fecha "+e.getFechaDeLanzamiento().format(dtf)
                ));*/

        //Busca episodios por pedazo de texto del titulo
        System.out.println("por favor escriba el titulo o parte del titulo, del episodio que desea ver");
        var pedazoTitulo = teclado.nextLine();

        Optional<Episodio> first = episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(pedazoTitulo.toUpperCase()))
                //findAny es para traer cualquier elemento que coincida
                //findFirst() es para buscar el primero que encuentre y seria una operaciond e tipo final
                .findFirst();
        if(first.isPresent()){
            System.out.println("Episodio encontrado: ");;
            System.out.println("Lo datos son: "+first.get());
        } else{
            System.out.println("Episodio no encontrado");
        }


    }
}
