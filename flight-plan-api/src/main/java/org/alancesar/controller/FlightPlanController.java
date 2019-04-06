package org.alancesar.controller;

import org.alancesar.itinerary.ItineraryNameGenerator;
import org.alancesar.itinerary.ItineraryProcessor;
import org.alancesar.model.BestRoute;
import org.alancesar.model.Itinerary;
import org.alancesar.model.Route;
import org.alancesar.route.BestRouteProcessor;
import org.alancesar.route.RouteProcessor;
import org.alancesar.service.RouteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FlightPlanController {

    private ItineraryNameGenerator itineraryNameGenerator;
    private RouteService service;

    public FlightPlanController(ItineraryNameGenerator itineraryNameGenerator, RouteService service) {
        this.itineraryNameGenerator = itineraryNameGenerator;
        this.service = service;
    }

    @GetMapping("/routes/best")
    public BestRoute getBestRoute(@RequestParam("origin") String origin,
                                  @RequestParam("destination") String destination) {

        List<Route> routes = service.getRoutes();
        ItineraryProcessor processor = new ItineraryProcessor(routes);

        List<Itinerary> starter = processor.findStarterItineraries(origin, destination);
        List<Itinerary> itineraries = processor.findItineraries(starter, destination);

        if (itineraries.isEmpty()) {
            return null;
        }

        RouteProcessor routeProcessor = new BestRouteProcessor(itineraryNameGenerator);
        BestRoute bestRoute = routeProcessor.find(itineraries);

        return bestRoute;
    }

    @PostMapping("/routes")
    public void addRoute(@RequestBody  Route route) {
        service.save(route);
    }
}
