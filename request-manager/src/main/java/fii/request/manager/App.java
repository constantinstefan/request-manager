package fii.request.manager;

import fii.request.manager.service.OcrService;
import fii.request.manager.service.OcrServiceImpl;
import fii.request.manager.service.preprocessing.PreprocessingHandlerChain;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
	@Bean
	public CommandLineRunner run(ApplicationContext appContext) {
		return args -> {
			String[] beans = appContext.getBeanDefinitionNames();
			Arrays.stream(beans).sorted().forEach(System.out::println);
			//var preprocessingHandlerChain = (PreprocessingHandlerChain) appContext.getBean("preprocessingHandlerChain");

		};
	}
}
