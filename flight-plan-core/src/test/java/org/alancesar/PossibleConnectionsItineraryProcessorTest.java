package org.alancesar;

import org.alancesar.itinerary.ItineraryProcessor;
import org.alancesar.itinerary.PossibleConnectionsItineraryProcessor;
import org.alancesar.model.Itinerary;
import org.alancesar.model.Route;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class PossibleConnectionsItineraryProcessorTest {

    private final List<Route> routes = new RoutesGenerator().generate();

    @Test
    void findRoutes() {

        String origin = "GRU";
        String destination = "CDG";
        ItineraryProcessor processor = new PossibleConnectionsItineraryProcessor(routes);
        List<Itinerary> itineraries = processor.process(origin, destination);
        Assertions.assertEquals(4, itineraries.size());
        Assertions.assertEquals(4, itineraries.get(0).getRoutes().size());
        Assertions.assertEquals(destination, itineraries.get(0).getRoutes().get(3).getDestination());
    }

    @Test
    void avoidEternalLoop() {
        List<Route> routes = Arrays.asList(
                new Route("A", "B", 10),
                new Route("B", "C", 10),
                new Route("C", "A", 10));

        String origin = "A";
        String destination = "D";
        ItineraryProcessor processor = new PossibleConnectionsItineraryProcessor(routes);
        List<Itinerary> itineraries = processor.process(origin, destination);

        Assertions.assertTrue(itineraries.isEmpty());
    }
}
