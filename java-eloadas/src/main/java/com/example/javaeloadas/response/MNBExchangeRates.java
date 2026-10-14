package com.example.javaeloadas.response;

import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement(name = "MNBExchangeRates")
@XmlAccessorType(XmlAccessType.FIELD)
public class MNBExchangeRates {

    @XmlElement(name = "Day")
    private List<Day> days;

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Day {

        @XmlAttribute(name = "date")
        private String date;

        @XmlElement(name = "Rate")
        private Rate rate;

        public String getDate() {
            return date;
        }

        public Rate getRate() {
            return rate;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Rate {

        @XmlAttribute(name="unit")
        private String unit;

        @XmlAttribute(name="curr")
        private String currency;

        @XmlValue
        private String rate;

        public String getUnit() {
            return unit;
        }

        public String getCurrency() {
            return currency;
        }

        public String getRate() {
            return rate;
        }
    }

    public List<Day> getDays() {
        return days;
    }
}
