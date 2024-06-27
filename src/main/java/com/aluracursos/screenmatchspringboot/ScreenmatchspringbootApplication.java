package com.aluracursos.screenmatchspringboot;

import com.aluracursos.screenmatchspringboot.model.DatosEpisodio;
import com.aluracursos.screenmatchspringboot.model.DatosSerie;
import com.aluracursos.screenmatchspringboot.model.DatosTemporadas;
import com.aluracursos.screenmatchspringboot.service.ConsumoAPI;
import com.aluracursos.screenmatchspringboot.service.ConvierteDatos;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchspringbootApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchspringbootApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Hola mundo desde screenmatch con Springboot");

		var consumoApi = new ConsumoAPI();
		var json = consumoApi.obtenerDatos("http://www.omdbapi.com/?apikey=4830f843&t=game+of+thrones");
		System.out.println(json);

		ConvierteDatos conversor = new ConvierteDatos();
		//aqui puede ser recibir cualquier clase, y en este caso ponemos datosSerie
		var datos = conversor.obtenerDatos(json, DatosSerie.class);
		System.out.println(datos);

		json = consumoApi.obtenerDatos("http://www.omdbapi.com/?apikey=4830f843&t=game+of+thrones&Season=1&episode=1");
		DatosEpisodio episodios = conversor.obtenerDatos(json, DatosEpisodio.class);
		System.out.println(episodios);

		List<DatosTemporadas> temporadas = new ArrayList<>();
		for (int i = 1; i <= datos.totalDeTemporadas(); i++) {
			json = consumoApi.obtenerDatos("http://www.omdbapi.com/?apikey=4830f843&t=game+of+thrones&Season="+i);
			var datosTemporadas = conversor.obtenerDatos(json, DatosTemporadas.class);
			temporadas.add(datosTemporadas);
		}
		temporadas.forEach(System.out::println);
	}
}
