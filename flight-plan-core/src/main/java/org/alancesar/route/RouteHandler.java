package org.alancesar.route;

import org.alancesar.model.BestRoute;
import org.alancesar.model.Itinerary;

import java.util.List;

public interface RouteHandler {

    BestRoute find(List<Itinerary> itineraries);
}
