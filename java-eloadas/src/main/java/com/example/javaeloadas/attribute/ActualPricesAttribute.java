package com.example.javaeloadas.attribute;

import com.oanda.v20.pricing_common.PriceValue;

import java.util.ArrayList;
import java.util.List;

public class ActualPricesAttribute {

    private final List<PriceValue> bids = new ArrayList<>();
    private final List<PriceValue> asks = new ArrayList<>();

    public List<PriceValue> getBids() {
        return bids;
    }

    public void addBid(PriceValue bid) {
        this.bids.add(bid);
    }

    public List<PriceValue> getAsks() {
        return asks;
    }

    public void addAsk(PriceValue ask) {
        this.asks.add(ask);
    }
}
