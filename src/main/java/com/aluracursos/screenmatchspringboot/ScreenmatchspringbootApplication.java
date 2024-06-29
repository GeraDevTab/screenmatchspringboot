package com.aluracursos.screenmatchspringboot;

import com.aluracursos.screenmatchspringboot.model.DatosEpisodio;
import com.aluracursos.screenmatchspringboot.model.DatosSerie;
import com.aluracursos.screenmatchspringboot.model.DatosTemporadas;
import com.aluracursos.screenmatchspringboot.principal.EjemploStreams;
import com.aluracursos.screenmatchspringboot.principal.Principal;
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
		//Principal principal = new Principal();
		//principal.muestraElMenu();
		EjemploStreams ejemploStreams = new EjemploStreams();
		ejemploStreams.muestraEjemplo();


	}
}
