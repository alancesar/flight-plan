package org.alancesar.itinerary;

import org.alancesar.model.Itinerary;
import org.alancesar.model.Route;

public class FullItineraryNameGenerator implements ItineraryNameGenerator {

    private String splitter;

    public FullItineraryNameGenerator(String splitter) {
        this.splitter = splitter;
    }

    public String generate(Itinerary itinerary) {
        if (itinerary.getRoutes() == null || itinerary.getRoutes().isEmpty()) {
            return "";
        }

        String text = itinerary.getRoutes().stream()
                .map(Route::getOrigin)
                .reduce((a, b) -> String.format("%s %s %s", a, splitter, b))
                .get();

        Route lastRoute = itinerary.getRoutes().get(itinerary.getRoutes().size() - 1);
        return String.format("%s %s %s", text, splitter, lastRoute.getDestination());
    }
}
