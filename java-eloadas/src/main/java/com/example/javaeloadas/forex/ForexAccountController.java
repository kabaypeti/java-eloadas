package com.example.javaeloadas.forex;

import com.oanda.v20.ExecuteException;
import com.oanda.v20.RequestException;
import com.oanda.v20.account.AccountSummary;
import org.apache.http.client.methods.HttpGet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/forex")
public class ForexAccountController {

    @GetMapping("/account")
    public String getAccount(Model model) {

        try {
            AccountSummary summary = Config.CONTEXT.account.summary(Config.ACCOUNTID).getAccount();
            model.addAttribute("id", summary.getId());
            model.addAttribute("alias", summary.getAlias());
            model.addAttribute("createdTime", summary.getCreatedTime());
            model.addAttribute("balance", summary.getBalance());
            model.addAttribute("currency", summary.getCurrency());
            model.addAttribute("positionValue", summary.getPositionValue());

        } catch (Exception e) {
            System.out.println();
        }
        return "forex/account";

    }

}
