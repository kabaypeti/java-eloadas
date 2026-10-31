package com.example.javaeloadas.forex;

import com.example.javaeloadas.attribute.CandleData;
import com.example.javaeloadas.attribute.InstrumentHistAttribute;
import com.oanda.v20.instrument.Candlestick;
import com.oanda.v20.instrument.InstrumentCandlesRequest;
import com.oanda.v20.instrument.InstrumentCandlesResponse;
import com.oanda.v20.primitives.InstrumentName;
import static com.oanda.v20.instrument.CandlestickGranularity.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/forex")
public class ForexHistPricesController {

    @GetMapping("/historical_prices")
    public String getHistPricesForm(Model model) {
        try {
            model.addAttribute("instruments", getInstruments());
            model.addAttribute("histForm", new InstrumentHistAttribute());
            model.addAttribute("renderTable", false);
            model.addAttribute("candles", new ArrayList<CandleData>());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "forex/historical_prices";
    }

    @PostMapping("/historical_prices")
    public String postHistPricesForm(@ModelAttribute("histForm") InstrumentHistAttribute histForm, Model model) {
        List<CandleData> candleList = new ArrayList<>();
        try {
            InstrumentCandlesRequest request = new InstrumentCandlesRequest(
                    new InstrumentName(histForm.getInstrument())
            );
            switch (histForm.getGranularity()) {
                case "M1" -> request.setGranularity(M1);
                case "H1" -> request.setGranularity(H1);
                case "D"  -> request.setGranularity(D);
                case "W"  -> request.setGranularity(W);
                case "M"  -> request.setGranularity(M);
            }

            request.setCount(10L);

            InstrumentCandlesResponse resp = Config.CONTEXT.instrument.candles(request);

            for (Candlestick candle : resp.getCandles()) {
                candleList.add(new CandleData(
                        candle.getTime().toString(),
                        candle.getMid().getC().toString()
                ));
            }

            model.addAttribute("renderTable", true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            model.addAttribute("renderTable", false);
        }

        try {
            model.addAttribute("instruments", getInstruments());
        } catch (Exception e) {
            model.addAttribute("instruments", new ArrayList<String>());
        }
        model.addAttribute("histForm", histForm);
        model.addAttribute("candles", candleList);

        return "forex/historical_prices";
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
