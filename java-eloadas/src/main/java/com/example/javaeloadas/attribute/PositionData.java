package com.example.javaeloadas.attribute;

public class PositionData {
    private final String id;
    private final String instrument;
    private final String openTime;
    private final String currentUnits;
    private final String price;
    private final String unrealizedPL;

    public PositionData(String id, String instrument, String openTime, String currentUnits, String price, String unrealizedPL) {
        this.id = id;
        this.instrument = instrument;
        this.openTime = openTime;
        this.currentUnits = currentUnits;
        this.price = price;
        this.unrealizedPL = unrealizedPL;
    }

    public String getId() { return id; }
    public String getInstrument() { return instrument; }
    public String getOpenTime() { return openTime; }
    public String getCurrentUnits() { return currentUnits; }
    public String getPrice() { return price; }
    public String getUnrealizedPL() { return unrealizedPL; }
}