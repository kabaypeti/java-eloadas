package com.example.javaeloadas.attribute;

public class CandleData {
    private final String time;
    private final String closePrice;

    public CandleData(String time, String closePrice) {
        this.time = time;
        this.closePrice = closePrice;
    }

    public String getTime() { return time; }
    public String getClosePrice() { return closePrice; }
}
