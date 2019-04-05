package org.alancesar.service;

import org.alancesar.model.Route;
import org.alancesar.repository.RouteRepository;

import java.util.List;
import java.util.function.Function;

public class RouteService {

    private final RouteRepository repository;

    private Function<Route, String> routeToString = (route) -> String.format(
            "%s,%s,%.0f\r\n", route.getOrigin(), route.getDestination(), route.getPrice());

    private Function<String, Route> stringToRoute = (line) -> {
        String[] items = line.split(",");
        Float price = Float.valueOf(items[2]);
        return new Route(items[0], items[1], price);
    };

    public RouteService(RouteRepository repository) {
        this.repository = repository;
    }

    public List<Route> getRoutes() {
        return repository.readAll(stringToRoute);
    }

    public void save(Route route) {
        repository.write(route, routeToString);
    }
}
