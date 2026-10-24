package com.example.javaeloadas.forex;

import com.example.javaeloadas.attribute.InstrumentOpenAttribute;
import com.oanda.v20.order.MarketOrderRequest;
import com.oanda.v20.order.OrderCreateRequest;
import com.oanda.v20.order.OrderCreateResponse;
import com.oanda.v20.primitives.InstrumentName;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/forex")
public class ForexOpenPositionController {

    @GetMapping("/open_position")
    public String getOpenPositionForm(Model model) {
        try {
            model.addAttribute("instruments", getInstruments());
            model.addAttribute("openForm", new InstrumentOpenAttribute());
            model.addAttribute("renderResult", false);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "forex/open_position";
    }

    @PostMapping("/open_position")
    public String postOpenPositionForm(@ModelAttribute("openForm") InstrumentOpenAttribute openForm, Model model) {
        String tradeId = "";
        try {
            InstrumentName instrument = new InstrumentName(openForm.getInstrument());
            OrderCreateRequest request = new OrderCreateRequest(Config.ACCOUNTID);
            MarketOrderRequest marketorderrequest = new MarketOrderRequest();
            marketorderrequest.setInstrument(instrument);
            marketorderrequest.setUnits(openForm.getUnits());
            request.setOrder(marketorderrequest);

            OrderCreateResponse response = Config.CONTEXT.order.create(request);
            tradeId = "tradeId: " + response.getOrderFillTransaction().getId();
            model.addAttribute("renderResult", true);
            model.addAttribute("success", true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            tradeId = "Hiba a pozíció nyitása során: " + e.getMessage();
            model.addAttribute("renderResult", true);
            model.addAttribute("success", false);
        }

        try {
            model.addAttribute("instruments", getInstruments());
        } catch (Exception e) {
            model.addAttribute("instruments", new ArrayList<String>());
        }
        model.addAttribute("openForm", openForm);
        model.addAttribute("tradeId", tradeId);

        return "forex/open_position";
    }

    private List<String> getInstruments() throws Exception {
        var instruments = Config.CONTEXT.account.instruments(Config.ACCOUNTID).getInstruments();
        var list = new ArrayList<>(
                instruments.stream()
                        .map(instrument -> instrument.getName().toString())
                        .toList()
        );
        Collections.sort(list);
        return list;
    }
}
