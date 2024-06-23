package com.aluracursos.screenmatchspringboot;

import com.aluracursos.screenmatchspringboot.model.DatosSerie;
import com.aluracursos.screenmatchspringboot.service.ConsumoAPI;
import com.aluracursos.screenmatchspringboot.service.ConvierteDatos;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
	}
}
