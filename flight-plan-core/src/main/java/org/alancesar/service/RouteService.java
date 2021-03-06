package org.alancesar.service;

import org.alancesar.model.Route;
import org.alancesar.repository.RouteRepository;

import java.util.List;
import java.util.function.Function;

public class RouteService implements Service<Route> {

    private final RouteRepository repository;

    private Function<Route, String> routeToString = (route) -> String.format(
            "%s,%s,%.0f\r\n", route.getOrigin(), route.getDestination(), route.getPrice());

    private Function<String, Route> stringToRoute = (line) -> {
        String[] items = line.split(",");
        double price = Double.valueOf(items[2]);
        return new Route(items[0], items[1], price);
    };

    public RouteService(RouteRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Route> getAll() {
        return repository.readAll(stringToRoute);
    }

    @Override
    public void save(Route route) {
        repository.write(route, routeToString);
    }
}
