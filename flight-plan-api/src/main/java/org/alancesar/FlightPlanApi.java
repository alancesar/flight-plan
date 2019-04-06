package org.alancesar;

import org.alancesar.itinerary.FullItineraryNameGenerator;
import org.alancesar.itinerary.ItineraryNameGenerator;
import org.alancesar.repository.CsvRouteRepository;
import org.alancesar.service.RouteService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@SpringBootApplication
public class FlightPlanApi {

    public static void main(String[] args) {
        SpringApplication.run(FlightPlanApi.class, args);
    }

    @Bean
    public ItineraryNameGenerator getItineraryNameGenerator() {
        return new FullItineraryNameGenerator(">>");
    }

    @Bean
    public RouteService getRouteService() {
        try {
            String path = new ClassPathResource("routes.csv").getFile().getAbsolutePath();
            CsvRouteRepository repository = new CsvRouteRepository(path);
            return new RouteService(repository);
        } catch (IOException e) {
        }

        return null;
    }
}
