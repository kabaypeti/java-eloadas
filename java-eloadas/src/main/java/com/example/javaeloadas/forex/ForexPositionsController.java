package com.example.javaeloadas.forex;

import com.example.javaeloadas.attribute.PositionData;
import com.oanda.v20.Context;
import com.oanda.v20.trade.Trade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/forex")
public class ForexPositionsController {

    private final Context ctx = new Context(Config.URL, Config.TOKEN);

    @GetMapping("/positions")
    public String getOpenPositions(Model model) {
        List<PositionData> positionList = new ArrayList<>();
        try {
            List<Trade> trades = ctx.trade.listOpen(Config.ACCOUNTID).getTrades();
            for (Trade trade : trades) {
                positionList.add(new PositionData(
                        trade.getId().toString(),
                        trade.getInstrument().toString(),
                        trade.getOpenTime().toString(),
                        trade.getCurrentUnits().toString(),
                        trade.getPrice().toString(),
                        trade.getUnrealizedPL().toString()
                ));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        model.addAttribute("positions", positionList);
        return "forex/positions";
    }
}
