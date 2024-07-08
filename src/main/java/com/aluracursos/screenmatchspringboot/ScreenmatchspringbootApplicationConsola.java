//package com.aluracursos.screenmatchspringboot;
//
//import com.aluracursos.screenmatchspringboot.principal.Principal;
//import com.aluracursos.screenmatchspringboot.repository.SerieRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class ScreenmatchspringbootApplicationConsola implements CommandLineRunner {
//	@Autowired
//	private SerieRepository repository;
//	public static void main(String[] args) {
//		SpringApplication.run(ScreenmatchspringbootApplicationConsola.class, args);
//	}
//
//	@Override
//	public void run(String... args) throws Exception {
//		System.out.println("Hola mundo desde screenmatch con Springboot");
//		Principal principal = new Principal(repository);
//		principal.muestraElMenu();
//		//EjemploStreams ejemploStreams = new EjemploStreams();
//		//ejemploStreams.muestraEjemplo();
//
//
//	}
//}
