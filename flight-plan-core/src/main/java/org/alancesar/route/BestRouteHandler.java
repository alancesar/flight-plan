package org.alancesar.route;

import org.alancesar.itinerary.ItineraryNameGenerator;
import org.alancesar.model.BestRoute;
import org.alancesar.model.Itinerary;
import org.alancesar.model.Route;

import java.util.Comparator;
import java.util.List;

public class BestRouteHandler implements RouteHandler {

    private final ItineraryNameGenerator nameGenerator;

    public BestRouteHandler(ItineraryNameGenerator nameGenerator) {
        this.nameGenerator = nameGenerator;
    }

    public BestRoute find(List<Itinerary> itineraries) {
        return itineraries.stream()
                .map(itinerary -> {
                    String name = nameGenerator.generate(itinerary);
                    double total = itinerary
                            .getRoutes()
                            .stream()
                            .map(Route::getPrice)
                            .mapToDouble(Double::valueOf)
                            .sum();

                    return new BestRoute(name, total);
                })
                .min(Comparator.comparingDouble(BestRoute::getPrice))
                .orElse(null);
    }
}
