package org.alancesar;

import org.alancesar.itinerary.FullItineraryNameGenerator;
import org.alancesar.model.Itinerary;
import org.alancesar.model.Route;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

class FullItineraryNameGeneratorTest {

    @Test
    void longName() {
        Itinerary itinerary = new Itinerary(
                Arrays.asList(
                        new Route("GRU", "BRC", 0),
                        new Route("BRC", "SCL", 0),
                        new Route("SCL", "ORL", 0),
                        new Route("ORL", "CDG", 0)),
                true);

        String routeName = new FullItineraryNameGenerator("-").generate(itinerary);
        Assertions.assertEquals("GRU - BRC - SCL - ORL - CDG", routeName);
    }

    @Test
    void shortName() {
        Itinerary itinerary = new Itinerary(Collections.singletonList(new Route("GRU", "BRC", 0)), true);
        String routeName = new FullItineraryNameGenerator(">>").generate(itinerary);
        Assertions.assertEquals("GRU >> BRC", routeName);
    }

    @Test
    void noName() {
        Itinerary itinerary = new Itinerary(Collections.emptyList(), true);
        String routeName = new FullItineraryNameGenerator("doesn't matter").generate(itinerary);
        Assertions.assertEquals("", routeName);
    }
}
