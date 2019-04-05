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

import java.util.List;

public class Main {

    public static void main(String[] args) {

        String origin = "GRU";
        String destination = "CDG";

        RouteService service = new RouteService(
                new CsvRouteRepository("/Users/acesar/avenuecode/flight-plan/flight-plan-api/src/main/resources/routes.csv"));

        List<Route> routes = service.getRoutes();
        ItineraryProcessor processor = new ItineraryProcessor(routes);

        List<Itinerary> starter = processor.findStarterItineraries(origin, destination);
        List<Itinerary> itineraries = processor.findItineraries(starter, destination);

        RouteProcessor routeProcessor = new BestRouteProcessor(new FullItineraryNameGenerator(">>"));
        BestRoute bestRoute = routeProcessor.find(itineraries);

        System.out.println(bestRoute.getRoute());
        System.out.println(bestRoute.getPrice());
    }
}
