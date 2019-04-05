package org.alancesar.model;

public class Route {

    private final String origin;
    private final String destination;
    private final float price;

    public Route(String origin, String destination, float price) {
        this.origin = origin;
        this.destination = destination;
        this.price = price;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public float getPrice() {
        return price;
    }
}
