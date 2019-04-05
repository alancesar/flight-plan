package org.alancesar.processor;

import org.alancesar.model.Itinerary;
import org.alancesar.model.Route;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ItineraryProcessor {

    public List<Itinerary> findStarterItinerary(String origin, String destination, List<Route> routes) {
        return routes.stream()
                .filter(route -> origin.equals(route.getOrigin()))
                .map(route -> new Itinerary(Arrays.asList(route), destination.equals(route.getDestination())))
                .collect(Collectors.toList());
    }

    public List<Itinerary> getItineraries(List<Route> routes, List<Itinerary> starter, String destination) {
        //TODO check to avoid eternal loops

        List<Itinerary> itineraries = starter.stream()
                .map(itinerary -> {
                    if (itinerary.isDone()) {
                        return itinerary;
                    }

                    Optional<Route> lastRouteInItinerary = itinerary.getRoutes().stream()
                            .reduce((first, second) -> second);

                    if (lastRouteInItinerary.isPresent()) {
                        Route lastRoute = lastRouteInItinerary.get();

                        List<Route> matchedRoutes = routes.stream()
                                .filter(route -> lastRoute.getDestination().equals(route.getOrigin()))
                                .collect(Collectors.toList());

                        Optional<Route> route = matchedRoutes.stream().findFirst();
                        List<Route> alternativeRoutes = matchedRoutes.stream().skip(1).collect(Collectors.toList());

                        if (route.isPresent()) {
                            Route match = route.get();

                            ArrayList<Route> routesList = new ArrayList<>(itinerary.getRoutes());
                            routesList.add(match);

                            return new Itinerary(routesList, destination.equals(
                                    match.getDestination()), alternativeRoutes);
                        }
                    }
                    return null;
                })
                .filter(itinerary -> Optional.ofNullable(itinerary).isPresent())
                .collect(Collectors.toList());

        List<Itinerary> clean = handleAlternativeRoutes(itineraries, destination);

        if (!isDone(clean)) {
            return getItineraries(routes, clean, destination);
        }

        return clean;
    }

    private List<Itinerary> handleAlternativeRoutes(List<Itinerary> itineraries, String destination) {

        ArrayList<Itinerary> clean = new ArrayList<>();

        itineraries.forEach(itinerary -> {
            clean.add(new Itinerary(itinerary.getRoutes(), itinerary.isDone()));

            if (!itinerary.getAlternativeRoutes().isEmpty()) {
                itinerary.getAlternativeRoutes().forEach(alternativeRoute -> {
                    List<Route> routes = new ArrayList<>(itinerary.getRoutes());
                    routes.add(alternativeRoute);

                    clean.add(new Itinerary(routes, alternativeRoute.getDestination().equals(destination)));
                });
            }
        });

        return clean;
    }

    private boolean isDone(List<Itinerary> itineraries) {
        return !itineraries.stream()
                .filter(itinerary -> !itinerary.isDone())
                .findFirst()
                .isPresent();
    }

}
