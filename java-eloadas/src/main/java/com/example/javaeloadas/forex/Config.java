package com.example.javaeloadas.forex;

import com.oanda.v20.Context;
import com.oanda.v20.account.AccountID;

public class Config {

    public static final String URL = "https://api-fxpractice.oanda.com";
    public static final AccountID ACCOUNTID = new AccountID("101-004-40703364-001");
    public static final String TOKEN = "c2e57b506e3aa2259e0d9af9b6f4971b-aaf958e8fd8a162ff4c054b1c53aa06b";

    public static final Context CONTEXT = new Context(Config.URL,Config.TOKEN);

}
