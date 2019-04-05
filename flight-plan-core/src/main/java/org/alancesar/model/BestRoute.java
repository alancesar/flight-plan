package org.alancesar.model;

public class BestRoute {

    private final String route;
    private final float price;

    public BestRoute(String route, float price) {
        this.route = route;
        this.price = price;
    }

    public String getRoute() {
        return route;
    }

    public float getPrice() {
        return price;
    }
}
