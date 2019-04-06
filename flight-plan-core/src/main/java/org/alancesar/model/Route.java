package org.alancesar.model;

public class Route {

    private String origin;
    private String destination;
    private double price;

    public Route(String origin, String destination, double price) {
        this.origin = origin;
        this.destination = destination;
        this.price = price;
    }

    public Route() {
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public double getPrice() {
        return price;
    }
}
