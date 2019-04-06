package org.alancesar;

import org.alancesar.itinerary.FullItineraryNameGenerator;
import org.alancesar.itinerary.ItineraryProcessor;
import org.alancesar.itinerary.PossibleConnectionsItineraryProcessor;
import org.alancesar.model.BestRoute;
import org.alancesar.model.Itinerary;
import org.alancesar.model.Route;
import org.alancesar.route.BestPriceRouteHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class BestPriceRouteHandlerTest {

    private final List<Route> routes = new RoutesGenerator().generate();

    @Test
    void bestRoute() {

        String origin = "GRU";
        String destination = "CDG";
        ItineraryProcessor processor = new PossibleConnectionsItineraryProcessor(routes);
        List<Itinerary> itineraries = processor.process(origin, destination);

        BestPriceRouteHandler bestRouteProcessor = new BestPriceRouteHandler(
                new FullItineraryNameGenerator("-"));

        BestRoute bestRoute = bestRouteProcessor.find(itineraries);

        Assertions.assertEquals("GRU - BRC - SCL - ORL - CDG", bestRoute.getRoute());
        Assertions.assertEquals(40, bestRoute.getPrice());
    }
}
