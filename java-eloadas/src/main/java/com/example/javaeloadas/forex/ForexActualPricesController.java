package com.example.javaeloadas.forex;

import com.example.javaeloadas.attribute.ActualPricesAttribute;
import com.example.javaeloadas.attribute.InstrumentAttribute;
import com.oanda.v20.ExecuteException;
import com.oanda.v20.RequestException;
import com.oanda.v20.account.AccountSummary;
import com.oanda.v20.pricing.ClientPrice;
import com.oanda.v20.pricing_common.PriceValue;
import com.oanda.v20.primitives.Instrument;
import com.oanda.v20.primitives.InstrumentName;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/forex")
public class ForexActualPricesController {

    @GetMapping("/actual_prices")
    public String getActualPricesForm(Model model) {

        try {
            model.addAttribute("instruments", getInstruments());
            model.addAttribute("instrumentForm", new InstrumentAttribute());
            model.addAttribute("renderTable", false);
            model.addAttribute("actualPrices", new ActualPricesAttribute());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "forex/actual_prices";

    }

    @PostMapping("/actual_prices")
    public String postActualPricesForm(@ModelAttribute("instrumentForm") InstrumentAttribute instrumentForm, Model model) {
        try {
            List<String> instruments = new ArrayList<>();
            instruments.add(instrumentForm.getInstrument());
            var priceResponse = Config.CONTEXT.pricing.get(Config.ACCOUNTID, instruments);
            ActualPricesAttribute actualPrices = new ActualPricesAttribute();
            for (var price : priceResponse.getPrices().getFirst().getAsks()) {
                actualPrices.addAsk(price.getPrice());
            }
            for (var price : priceResponse.getPrices().getFirst().getBids()) {
                actualPrices.addBid(price.getPrice());
            }

            model.addAttribute("instruments", getInstruments());
            model.addAttribute("instrumentForm", instrumentForm);
            model.addAttribute("renderTable", true);
            model.addAttribute("actualPrices", actualPrices);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "forex/actual_prices";
    }

    private List<String> getInstruments() throws ExecuteException, RequestException {
        var instruments = Config.CONTEXT.account.instruments(Config.ACCOUNTID).getInstruments();
        var list = new ArrayList<>(
                instruments.stream()
                        .map(instrument -> instrument.getName().toString()).
                        toList()
        );
        Collections.sort(list);
        return list;
    }

}
