package org.alancesar;

import org.alancesar.itinerary.ItineraryProcessor;
import org.alancesar.model.Itinerary;
import org.alancesar.model.Route;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ItineraryProcessorTest {

    private final List<Route> routes = new RoutesGenerator().generate();

    @Test
    public void findStarterRoutes() {

        ItineraryProcessor processor = new ItineraryProcessor(routes);
        List<Itinerary> itineraries = processor.findStarterItineraries("GRU", "BRC");
        Assertions.assertEquals(4, itineraries.size());
        Assertions.assertTrue(itineraries.get(0).isDone());
    }

    @Test
    public void findRoutes() {

        String origin = "GRU";
        String destination = "CDG";
        ItineraryProcessor processor = new ItineraryProcessor(routes);
        List<Itinerary> starter = processor.findStarterItineraries(origin, destination);
        List<Itinerary> itineraries = processor.findItineraries(starter, destination);
        Assertions.assertEquals(4, itineraries.size());
        Assertions.assertEquals(4, itineraries.get(0).getRoutes().size());
        Assertions.assertEquals(destination, itineraries.get(0).getRoutes().get(3).getDestination());
    }
}
