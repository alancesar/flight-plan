package org.alancesar.route;

import org.alancesar.itinerary.ItineraryNameGenerator;
import org.alancesar.model.BestRoute;
import org.alancesar.model.Itinerary;
import org.alancesar.model.Route;

import java.util.Comparator;
import java.util.List;

public class BestRouteProcessor implements RouteProcessor {

    private final ItineraryNameGenerator nameGenerator;

    public BestRouteProcessor(ItineraryNameGenerator nameGenerator) {
        this.nameGenerator = nameGenerator;
    }

    public BestRoute find(List<Itinerary> itineraries) {
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
