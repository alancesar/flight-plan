package org.alancesar.model;

public class BestRoute {

    private final String route;
    private final double price;

    public BestRoute(String route, double price) {
        this.route = route;
        this.price = price;
    }

    public String getRoute() {
        return route;
    }

    public double getPrice() {
        return price;
    }
}
