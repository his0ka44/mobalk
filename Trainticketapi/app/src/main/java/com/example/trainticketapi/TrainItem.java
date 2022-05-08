package com.example.trainticketapi;

public class TrainItem {
    private String name;
    private String route;
    private String date;
    private String price;
    private final int imageResource;

    public TrainItem(String name, String route, String date, String price, int imageResource) {
        this.name = name;
        this.route = route;
        this.date = date;
        this.price = price;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public String getRoute() {
        return route;
    }

    public String getDate() {
        return date;
    }

    public String getPrice() {
        return price;
    }

    public int getImageResource(){ return imageResource;}
}




