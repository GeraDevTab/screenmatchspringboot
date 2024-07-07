package com.aluracursos.screenmatchspringboot.principal;

import com.aluracursos.screenmatchspringboot.model.*;
import com.aluracursos.screenmatchspringboot.repository.SerieRepository;
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
    private List<DatosSerie> datosSeries = new ArrayList<>();
    private SerieRepository repositorio;
    private List<Serie> series;

    public Principal(SerieRepository repository) {
        this.repositorio= repository;
    }

    public void muestraElMenu(){

        var opcion = -1;
        while(opcion!=0){
            var menu = """
                    1- Buscar series
                    2- Buscar episodios
                    3- Mostrar series buscadas
                    4- Buscar series por titulo
                    5- Top 5 mejores series
                    6- Buscar series por categoria
                    7- Filtrar Series por temporada y evaluacion
                  
                    
                    0- Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion){
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodiosPorSerie();
                    break;
                case 3:
                    mostrarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriesPorTitulo();
                    break;
                case 5:
                    buscarTop5Series();
                    break;
                case 6:
                    burcarSeriesPorCategoria();
                    break;
                case 7:
                    filtrarSeriePorTemporadaYEvaluacion();
                    break;
                case 0:
                    System.out.println("Cerrando aplicacion");
                    break;
                default:
                    System.out.println("Opcion invalida");
            }
        }






    }



    private DatosSerie getDatosSerie(){
        System.out.println("Por favor escribe el nombre de la serie que deseas buscar");
        //Busca los datos generales de las series
        var nombreSerie = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + API_KEY);

        //aqui puede ser recibir cualquier clase, y en este caso ponemos datosSerie
        var datos = conversor.obtenerDatos(json, DatosSerie.class);
        System.out.println(json);
        System.out.println(datos);
        return datos;
    }

    private void buscarEpisodiosPorSerie(){
        //DatosSerie datos = getDatosSerie(); se omite para que no busque mas en la api de omdb
        mostrarSeriesBuscadas();
        System.out.println("escribe el nombre de la serie de la cual quieres ver los episodios:");
        var nombreSerie = teclado.nextLine();
        Optional<Serie> serie = series.stream()
                .filter(s -> s.getTitulo().toLowerCase().contains(nombreSerie.toLowerCase()))
                .findFirst();
        if(serie.isPresent()){
            var serieEncontrada = serie.get();
            //Busca los datos de todas las temporadas
            List<DatosTemporadas> temporadas = new ArrayList<>(); // se crea un arraylist para almacenar objetos DatosTemporadas

            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                // el error que mostraba en nombre serie al mandarlo en el url, se corrigio con la funcion "datos.titulo"
                var json = consumoAPI.obtenerDatos(URL_BASE+serieEncontrada.getTitulo().replace(" ","+")+"&Season="+i+API_KEY);
                var datosTemporadas = conversor.obtenerDatos(json, DatosTemporadas.class);
                temporadas.add(datosTemporadas);
            }
            temporadas.forEach(System.out::println);
            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios().stream()
                            .map(e-> new Episodio(d.numero(),e)))
                    .collect(Collectors.toList());

            serieEncontrada.setEpisodios(episodios);
            repositorio.save(serieEncontrada);
        }




        /*
        //Mostrar solo el titulo de los episodios parar las temporadas
        for (int i = 0; i < datos.totalDeTemporadas(); i++) {
            List<DatosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
            for (int j = 0; j < episodiosTemporada.size(); j++) {
                System.out.println(episodiosTemporada.get(j).titulo());

            }
        }
        //mejoria usando funciones lambda
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
        /*System.out.println("por favor escriba el titulo o parte del titulo, del episodio que desea ver");
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
        }*/
        /*Map<Integer , Double> evaluacionesPorTemporada = episodios.stream()
                .filter(e -> e.getEvaluacion()>0.0)
                .collect(Collectors.groupingBy(Episodio::getNumeroTemporada,
                        Collectors.averagingDouble(Episodio::getEvaluacion)));
        System.out.println(evaluacionesPorTemporada);
        DoubleSummaryStatistics  estadistica = episodios.stream()
                .filter(e -> e.getEvaluacion()>0.0)
                .collect(Collectors.summarizingDouble(Episodio::getEvaluacion));
        System.out.println("La media de las evaluaciones "+estadistica.getAverage());
        System.out.println("Episodio mejor evaluado "+estadistica.getMax());
        System.out.println("Episodio menor evaluado "+estadistica.getMin());
        */
    }

    private void buscarSerieWeb(){
        DatosSerie datos = getDatosSerie();
        Serie serie = new Serie(datos);
        repositorio.save(serie);
        //datosSeries.add(datos);
        System.out.println(datos);
    }

    private void mostrarSeriesBuscadas() {
        /*List<Serie> series = new ArrayList<>();
        series = datosSeries.stream()
                        .map(d -> new Serie(d))
                        .collect(Collectors.toList());*/
        //aqui en la sig linea, mandmos a llamar desde la base de datos todos lode registros
        //usando repositorio
        series = repositorio.findAll();

        series.stream()
                        .sorted(Comparator.comparing(Serie::getGenero))
                        .forEach(System.out::println);
    }

    private void buscarSeriesPorTitulo() {
        System.out.println("Escribe el nombre de la serie que deseas buscar");
        var nombreSerie = teclado.nextLine();
        Optional<Serie> serieBuscada = repositorio.findByTituloContainsIgnoreCase(nombreSerie);

        if(serieBuscada.isPresent()){
            System.out.println("LA serie buscada es: " + serieBuscada.get());
        }else {
            System.out.println("Serie no encontrada");
        }
    }

    private void buscarTop5Series(){
        List<Serie> topSeries = repositorio.findTop5ByOrderByEvaluacionDesc();
        topSeries.forEach(s -> System.out.println("Serie: "+s.getTitulo() + " Evaluacion: "+s.getEvaluacion()));
    }

    private void burcarSeriesPorCategoria(){
        System.out.println("Escriba el genero/categoria de la serie que desea buscar");
        var genero = teclado.nextLine();
        var categoria = Categoria.fromEspanol(genero);
        List<Serie> seriesPorCategoria = repositorio.findByGenero(categoria);
        System.out.println("Las series dela categoria " + genero);
        seriesPorCategoria.forEach(System.out::println);

    }

    private void filtrarSeriePorTemporadaYEvaluacion(){
        System.out.println("Filtrar series con cuantas temporadas");
        var totalTemporadas = teclado.nextInt();
        teclado.nextLine();
        System.out.println("¿con evaluacion a partir de cual valor?");
        var evaluacion = teclado.nextDouble();
        teclado.nextLine();

        //List<Serie> filtroSeries = repositorio.findByTotalTemporadasLessThanEqualAndEvaluacionGreaterThanEqual(Integer.valueOf(totalTemporadas), evaluacion);//este ejemplo es usando las palabras de acuerdo a la doc de jpa
        List<Serie> filtroSeries = repositorio.seriesPorTemporadaYEvaluacion(totalTemporadas,evaluacion);
        System.out.println("****Series filtradas****");
        filtroSeries.forEach(s-> System.out.println(s.getTitulo()+" Evaluacion: "+s.getEvaluacion()));
    }
}
