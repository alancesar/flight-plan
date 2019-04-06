package org.alancesar.controller;

import org.alancesar.itinerary.ItineraryNameGenerator;
import org.alancesar.itinerary.ItineraryProcessor;
import org.alancesar.model.BestRoute;
import org.alancesar.model.Itinerary;
import org.alancesar.model.Route;
import org.alancesar.route.BestRouteHandler;
import org.alancesar.route.RouteHandler;
import org.alancesar.service.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FlightPlanController {

    private ItineraryNameGenerator itineraryNameGenerator;
    private Service<Route> service;

    public FlightPlanController(ItineraryNameGenerator itineraryNameGenerator, Service<Route> service) {
        this.itineraryNameGenerator = itineraryNameGenerator;
        this.service = service;
    }

    @GetMapping("/routes/best")
    public BestRoute getBestRoute(@RequestParam("origin") String origin,
                                  @RequestParam("destination") String destination) {

        List<Route> routes = service.getAll();
        ItineraryProcessor processor = new ItineraryProcessor(routes);

        List<Itinerary> starter = processor.findStarterItineraries(origin, destination);
        List<Itinerary> itineraries = processor.findItineraries(starter, destination);

        if (itineraries.isEmpty()) {
            return null;
        }

        RouteHandler routeHandler = new BestRouteHandler(itineraryNameGenerator);
        return routeHandler.find(itineraries);
    }

    @PostMapping("/routes")
    public void addRoute(@RequestBody  Route route) {
        service.save(route);
    }
}
