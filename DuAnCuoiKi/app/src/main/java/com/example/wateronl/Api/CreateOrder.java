package com.example.wateronl.Api;

import com.example.wateronl.Helper.Helpers;
import org.json.JSONObject;
import java.util.Date;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class CreateOrder {
    private static final String APP_ID = "554";
    private static final String MAC_KEY = "8NdU5pG5R2spGHGhyO99HN1OhD8IQJBn";

    private class CreateOrderData {
        String AppId, AppUser, AppTime, Amount, AppTransId, EmbedData, Item, BankCode, Description, Mac;

        private CreateOrderData(String amount, String item) throws Exception {
            long appTime = new Date().getTime();
            AppId = APP_ID;
            AppUser = "Android_Demo";
            AppTime = String.valueOf(appTime);
            Amount = amount;
            AppTransId = Helpers.getAppTransId();
            EmbedData = "{}";
            Item = item;
            BankCode = "zalopayapp";
            Description = "Merchant pay for order #" + AppTransId;
            String inputHMac = String.format("%s|%s|%s|%s|%s|%s|%s", this.AppId, this.AppTransId, this.AppUser, this.Amount, this.AppTime, this.EmbedData, this.Item);
            Mac = Helpers.getMac(MAC_KEY, inputHMac);
        }
    }

    public JSONObject createOrder(String amount, String item) throws Exception {
        CreateOrderData input = new CreateOrderData(amount, item);
        RequestBody formBody = new FormBody.Builder()
                .add("appid", input.AppId).add("appuser", input.AppUser).add("apptime", input.AppTime)
                .add("amount", input.Amount).add("apptransid", input.AppTransId).add("embeddata", input.EmbedData)
                .add("item", input.Item).add("bankcode", input.BankCode).add("description", input.Description)
                .add("mac", input.Mac).build();
        return HttpProvider.sendPost("https://sandbox.zalopay.com.vn/v001/tpe/createorder", formBody);
    }
}