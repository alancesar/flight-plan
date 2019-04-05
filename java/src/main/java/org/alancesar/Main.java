package org.alancesar;

import org.alancesar.itinerary.FullItineraryNameGenerator;
import org.alancesar.model.BestRoute;
import org.alancesar.model.Itinerary;
import org.alancesar.model.Route;
import org.alancesar.route.BestRouteProcessor;
import org.alancesar.processor.ItineraryProcessor;
import org.alancesar.route.RouteProcessor;
import org.alancesar.service.RouteService;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        String origin = "GRU";
        String destination = "CDG";

        ItineraryProcessor processor = new ItineraryProcessor();

        List<Route> routes = new RouteService().getRoutes();

        List<Itinerary> starter = processor.findStarterItinerary(origin, destination, routes);
        List<Itinerary> itineraries = processor.getItineraries(routes, starter, destination);

        RouteProcessor routeProcessor = new BestRouteProcessor();
        BestRoute bestRoute = routeProcessor.find(itineraries, new FullItineraryNameGenerator(">>"));

        System.out.println(bestRoute.getRoute());
        System.out.println(bestRoute.getPrice());
    }
}
