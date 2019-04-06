package org.alancesar;

import org.alancesar.itinerary.FullItineraryNameGenerator;
import org.alancesar.itinerary.ItineraryProcessor;
import org.alancesar.model.BestRoute;
import org.alancesar.model.Itinerary;
import org.alancesar.model.Route;
import org.alancesar.route.BestRouteHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class BestRouteHandlerTest {

    private final List<Route> routes = new RoutesGenerator().generate();

    @Test
    void bestRoute() {

        String origin = "GRU";
        String destination = "CDG";
        ItineraryProcessor itineraryProcessorprocessor = new ItineraryProcessor(routes);
        List<Itinerary> starter = itineraryProcessorprocessor.findStarterItineraries(origin, destination);
        List<Itinerary> itineraries = itineraryProcessorprocessor.findItineraries(starter, destination);

        BestRouteHandler bestRouteProcessor = new BestRouteHandler(
                new FullItineraryNameGenerator("-"));

        BestRoute bestRoute = bestRouteProcessor.find(itineraries);

        Assertions.assertEquals("GRU - BRC - SCL - ORL - CDG", bestRoute.getRoute());
        Assertions.assertEquals(40, bestRoute.getPrice());
    }
}
