package com.example.javaeloadas;

import com.example.javaeloadas.attribute.PriceAttribute;
import com.example.javaeloadas.response.MNBExchangeRates;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import soapclient.*;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/mnbsoap")
public class MNBSoapController {

    @GetMapping
    public String getMnbSoap(Model model) {
        model.addAttribute("price", new PriceAttribute());
        return "mnb_soap/form";
    }

    @PostMapping
    public String postMnbSoap(@ModelAttribute PriceAttribute price, Model model) throws MNBArfolyamServiceSoapGetExchangeRatesStringFaultFaultMessage, JAXBException {
        MNBArfolyamServiceSoapImpl impl = new MNBArfolyamServiceSoapImpl();
        MNBArfolyamServiceSoap service = impl.getCustomBindingMNBArfolyamServiceSoap();

        //String output = String.format("Megadott adatok: Currency: %s; Start date: %s; End date: %s", price.getCurrency(), price.getStartDate(), price.getEndDate());

        String exchangeRates = service.getExchangeRates(price.getStartDate(), price.getEndDate(), price.getCurrency());

        StringReader sr = new StringReader(exchangeRates);
        JAXBContext context = JAXBContext.newInstance(MNBExchangeRates.class);
        MNBExchangeRates response = (MNBExchangeRates) context.createUnmarshaller().unmarshal(sr);


        model.addAttribute("output", response.getDays());
        return "mnb_soap/result";
    }

}
