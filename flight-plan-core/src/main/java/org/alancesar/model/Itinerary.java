package org.alancesar.model;

import java.util.Collections;
import java.util.List;

public class Itinerary {

    private final List<Route> routes;
    private final boolean done;
    private final List<Route> alternativeRoutes;

    public Itinerary(List<Route> routes, boolean done) {
        this.routes = routes;
        this.done = done;
        this.alternativeRoutes = Collections.emptyList();
    }

    public Itinerary(List<Route> routes, boolean done, List<Route> alternativeRoutes) {
        this.routes = routes;
        this.done = done;
        this.alternativeRoutes = alternativeRoutes;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public boolean isDone() {
        return done;
    }

    public List<Route> getAlternativeRoutes() {
        return alternativeRoutes;
    }

    public boolean hasAlternativeRoutes() {
        return !alternativeRoutes.isEmpty();
    }
}
