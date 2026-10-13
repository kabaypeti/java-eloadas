package com.example.javaeloadas;

import com.example.javaeloadas.attribute.PriceAttribute;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import soapclient.MNBArfolyamServiceSoap;
import soapclient.MNBArfolyamServiceSoapGetExchangeRatesStringFaultFaultMessage;
import soapclient.MNBArfolyamServiceSoapImpl;

@Controller
@RequestMapping(value = "/mnbsoap")
public class MNBSoapController {

    @GetMapping
    public String getMnbSoap(Model model) {
        model.addAttribute("price", new PriceAttribute());
        return "mnb_soap/form";
    }

    @PostMapping
    public String postMnbSoap(@ModelAttribute PriceAttribute price, Model model) throws MNBArfolyamServiceSoapGetExchangeRatesStringFaultFaultMessage {
        MNBArfolyamServiceSoapImpl impl = new MNBArfolyamServiceSoapImpl();
        MNBArfolyamServiceSoap service = impl.getCustomBindingMNBArfolyamServiceSoap();

        String output = String.format("Megadott adatok:\nCurrency: %s; Start date: %s; End date: %s\n\n", price.getCurrency(), price.getStartDate(), price.getEndDate());

        output += service.getExchangeRates(price.getStartDate(), price.getEndDate(), price.getCurrency());
        model.addAttribute("output", output);

        return "mnb_soap/result";
    }

}
