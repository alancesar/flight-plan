package org.alancesar.itinerary;

import org.alancesar.model.Itinerary;

import java.util.List;

public interface ItineraryProcessor {

    List<Itinerary> process(String origin, String destination);
}
