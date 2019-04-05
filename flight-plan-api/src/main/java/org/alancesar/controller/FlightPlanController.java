package org.alancesar.controller;

import org.alancesar.itinerary.FullItineraryNameGenerator;
import org.alancesar.itinerary.ItineraryProcessor;
import org.alancesar.model.BestRoute;
import org.alancesar.model.Itinerary;
import org.alancesar.model.Route;
import org.alancesar.repository.CsvRouteRepository;
import org.alancesar.route.BestRouteProcessor;
import org.alancesar.route.RouteProcessor;
import org.alancesar.service.RouteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FlightPlanController {

    @GetMapping("/bestRoute")
    public BestRoute getBestRoute(@RequestParam("origin") String origin,
                             @RequestParam("destination") String destination) {

        //TODO

        CsvRouteRepository repository = new CsvRouteRepository("/Users/acesar/avenuecode/flight-plan/flight-plan-api/src/main/resources/routes.csv");
        RouteService service = new RouteService(repository);
        List<Route> routes = service.getRoutes();
        ItineraryProcessor processor = new ItineraryProcessor(routes);

        List<Itinerary> starter = processor.findStarterItineraries(origin, destination);
        List<Itinerary> itineraries = processor.findItineraries(starter, destination);

        RouteProcessor routeProcessor = new BestRouteProcessor(new FullItineraryNameGenerator(">>"));
        BestRoute bestRoute = routeProcessor.find(itineraries);

        return bestRoute;
    }
}
