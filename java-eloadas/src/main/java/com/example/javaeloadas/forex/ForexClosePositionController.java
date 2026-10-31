package com.example.javaeloadas.forex;

import com.example.javaeloadas.attribute.InstrumentCloseAttribute;
import com.oanda.v20.Context;
import com.oanda.v20.trade.TradeCloseRequest;
import com.oanda.v20.trade.TradeSpecifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/forex")
public class ForexClosePositionController {

    private final Context ctx = new Context(Config.URL, Config.TOKEN);

    @GetMapping("/close_position")
    public String getClosePositionForm(Model model) {
        model.addAttribute("closeForm", new InstrumentCloseAttribute());
        model.addAttribute("renderResult", false);
        return "forex/close_position";
    }

    @PostMapping("/close_position")
    public String postClosePositionForm(@ModelAttribute("closeForm") InstrumentCloseAttribute closeForm, Model model) {
        String resultMessage = "";
        try {
            int id = closeForm.getTradeId();
            ctx.trade.close(new TradeCloseRequest(Config.ACCOUNTID, new TradeSpecifier(id + "")));
            resultMessage = "Closed tradeId = " + id;
            model.addAttribute("success", true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            resultMessage = "Hiba a pozíció zárása során: " + e.getMessage();
            model.addAttribute("success", false);
        }

        model.addAttribute("closeForm", closeForm);
        model.addAttribute("resultMessage", resultMessage);
        model.addAttribute("renderResult", true);

        return "forex/close_position";
    }
}
