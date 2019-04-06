package org.alancesar;

import org.alancesar.itinerary.FullItineraryNameGenerator;
import org.alancesar.itinerary.ItineraryProcessor;
import org.alancesar.itinerary.PossibleConnectionsItineraryProcessor;
import org.alancesar.model.BestRoute;
import org.alancesar.model.Itinerary;
import org.alancesar.model.Route;
import org.alancesar.repository.CsvRouteRepository;
import org.alancesar.route.BestPriceRouteHandler;
import org.alancesar.route.RouteHandler;
import org.alancesar.service.RouteService;
import org.alancesar.service.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.out.println("It's necessary inform the routes file path as argument!");
            return;
        }

        String path = args[0];
        Service<Route> service = new RouteService(new CsvRouteRepository(path));

        List<Route> routes = service.getAll();
        ItineraryProcessor processor = new PossibleConnectionsItineraryProcessor(routes);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("Please type the origin: ");
            String origin = reader.readLine().toUpperCase();
            System.out.println("And the destination: ");
            String destination = reader.readLine().toUpperCase();

            List<Itinerary> itineraries = processor.process(origin, destination);

            if (itineraries.isEmpty()) {
                System.out.println("Sorry, any route was found.");
            } else {
                RouteHandler routeHandler = new BestPriceRouteHandler(new FullItineraryNameGenerator("-"));
                BestRoute bestRoute = routeHandler.find(itineraries);
                System.out.println(String.format("Best route: %s > %.0f", bestRoute.getRoute(), bestRoute.getPrice()));
            }

            System.out.println("Press Q to exit or Enter to continue");
            if (reader.readLine().toUpperCase().equals("Q")) {
                System.out.println("Bye bye!");
                return;
            }
        }
    }
}
