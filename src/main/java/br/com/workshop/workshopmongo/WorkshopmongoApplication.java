package br.com.workshop.workshopmongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import static br.com.workshop.workshopmongo.QuickStart.conectar;

@SpringBootApplication
public class WorkshopmongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkshopmongoApplication.class, args);
		conectar();
		System.out.println("------------------------");
		System.out.println("-------Rodando----------");
		System.out.println("------------------------");
	}

}
