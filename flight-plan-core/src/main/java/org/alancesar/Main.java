package org.alancesar;

import org.alancesar.itinerary.FullItineraryNameGenerator;
import org.alancesar.itinerary.ItineraryProcessor;
import org.alancesar.model.BestRoute;
import org.alancesar.model.Itinerary;
import org.alancesar.model.Route;
import org.alancesar.repository.CsvRouteRepository;
import org.alancesar.route.BestRouteProcessor;
import org.alancesar.route.RouteProcessor;
import org.alancesar.service.RouteService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;

public class Main {

    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.out.println("É necessário informar o path do arquivo de rotas!");
            return;
        }

        String path = args[0];
        RouteService service = new RouteService(new CsvRouteRepository(path));

        List<Route> routes = service.getRoutes();
        ItineraryProcessor processor = new ItineraryProcessor(routes);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("Please type the origin: ");
            String origin = reader.readLine().toUpperCase();
            System.out.println("And the destination: ");
            String destination = reader.readLine().toUpperCase();

            List<Itinerary> starter = processor.findStarterItineraries(origin, destination);
            List<Itinerary> itineraries = processor.findItineraries(starter, destination);

            if (itineraries.isEmpty()) {
                System.out.println("Sorry, any route was found.");
            } else {
                RouteProcessor routeProcessor = new BestRouteProcessor(new FullItineraryNameGenerator("-"));
                BestRoute bestRoute = routeProcessor.find(itineraries);
                System.out.println(String.format("Best route: %s > %.0f", bestRoute.getRoute(), bestRoute.getPrice()));
            }
        }
    }
}
