package org.alancesar.itinerary;

import org.alancesar.model.Itinerary;
import org.alancesar.model.Route;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PossibleConnectionsItineraryProcessor implements ItineraryProcessor {

    private final List<Route> routes;

    public PossibleConnectionsItineraryProcessor(List<Route> routes) {
        this.routes = routes;
    }

    @Override
    public List<Itinerary> process(String origin, String destination) {
        List<Itinerary> starter = findStarterItineraries(origin, destination);
        return findItineraries(starter, origin, destination);
    }

    private List<Itinerary> findStarterItineraries(String origin, String destination) {
        return routes.stream()
                .filter(route -> origin.equals(route.getOrigin()))
                .map(route -> new Itinerary(
                        Collections.singletonList(route), destination.equals(route.getDestination())))
                .collect(Collectors.toList());
    }

    private List<Itinerary> findItineraries(List<Itinerary> starter, String origin, String destination) {
        List<Itinerary> itineraries = starter.stream()
                .map(itinerary -> {
                    if (itinerary.isDone()) {
                        return itinerary;
                    }

                    Optional<Route> lastRouteInItinerary = findLastRoute(itinerary);

                    if (lastRouteInItinerary.isPresent()) {
                        Route lastRoute = lastRouteInItinerary.get();

                        List<Route> connections = findConnections(lastRoute);

                        Optional<Route> mainRoute = getMainRoute(connections);

                        if (mainRoute.isPresent()) {
                            Route route = mainRoute.get();

                            ArrayList<Route> routes = new ArrayList<>(itinerary.getRoutes());
                            routes.add(route);

                            List<Route> alternativeRoutes = skipTheFirst(connections);
                            Itinerary itineraryWithTheNewRoutes = new Itinerary(
                                    routes, destination.equals(route.getDestination()), alternativeRoutes);

                            // Avoid "eternal loop", ex.: A > B > C > A ...
                            if (isInLoop(origin, itineraryWithTheNewRoutes)) {
                                return null;
                            }

                            return itineraryWithTheNewRoutes;
                        }
                    }
                    return null;
                })
                .filter(itinerary -> Optional.ofNullable(itinerary).isPresent())
                .collect(Collectors.toList());

        // Handle with "bifurcations", making the routes as a simple list
        List<Itinerary> clean = handleAlternativeRoutes(itineraries, destination);

        if (!isItineraryDone(clean)) {
            return findItineraries(clean, origin, destination);
        }

        return clean;
    }

    private List<Route> findConnections(Route toConnect) {
        return routes.stream()
                .filter(route -> toConnect.getDestination().equals(route.getOrigin()))
                .collect(Collectors.toList());
    }

    private Optional<Route> getMainRoute(List<Route> routes) {
        return routes.stream().findFirst();
    }

    private List<Route> skipTheFirst(List<Route> routes) {
        return routes.stream().skip(1).collect(Collectors.toList());
    }

    private boolean isInLoop(String origin, Itinerary itinerary) {
        return skipTheFirst(itinerary.getRoutes()).stream()
                .map(Route::getDestination)
                .collect(Collectors.toList())
                .contains(origin);
    }

    private Optional<Route> findLastRoute(Itinerary itinerary) {
        return itinerary.getRoutes().stream().reduce((first, second) -> second);
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

    private boolean isItineraryDone(List<Itinerary> itineraries) {
        return itineraries.stream().allMatch(Itinerary::isDone);
    }
}
