package org.alancesar.route;

import org.alancesar.itinerary.ItineraryNameGenerator;
import org.alancesar.model.BestRoute;
import org.alancesar.model.Itinerary;
import org.alancesar.model.Route;
import org.alancesar.itinerary.FullItineraryNameGenerator;

import java.util.Comparator;
import java.util.List;

public class BestRouteProcessor implements RouteProcessor {

    public BestRoute find(List<Itinerary> itineraries, ItineraryNameGenerator nameGenerator) {
        return itineraries.stream()
                .map(itinerary -> {
                    String name = nameGenerator.generate(itinerary);
                    Float total = itinerary.getRoutes().stream().map(Route::getPrice).reduce((a, b) -> a + b).get();

                    return new BestRoute(name, total);
                })
                .sorted(Comparator.comparingDouble(BestRoute::getPrice))
                .findFirst()
                .get();
    }
}
