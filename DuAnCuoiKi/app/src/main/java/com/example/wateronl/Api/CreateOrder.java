package com.example.wateronl.Api;

import com.example.wateronl.Constant.AppInfo;
import com.example.wateronl.Helper.Helpers;

import org.json.JSONObject;

import java.util.Date;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class CreateOrder {
    private class CreateOrderData {
        String AppId;
        String AppUser;
        String AppTime;
        String Amount;
        String AppTransId;
        String EmbedData;
        String Item;
        String BankCode;
        String Description;
        String Mac;

        private CreateOrderData(String amount, String item) throws Exception {
            long appTime = new Date().getTime();
            AppId = String.valueOf(AppInfo.APP_ID);
            AppUser = "Android_Demo";
            AppTime = String.valueOf(appTime);
            Amount = amount;
            AppTransId = Helpers.getAppTransId();
            EmbedData = "{}";
            Item = item;
            BankCode = "zalopayapp";
            Description = "Merchant pay for order #" + AppTransId;
            String inputHMac = String.format("%s|%s|%s|%s|%s|%s|%s",
                    this.AppId,
                    this.AppTransId,
                    this.AppUser,
                    this.Amount,
                    this.AppTime,
                    this.EmbedData,
                    this.Item);

            Mac = Helpers.getMac(AppInfo.MAC_KEY, inputHMac);
        }
    }

    public JSONObject createOrder(String amount, String item) throws Exception {
        CreateOrderData input = new CreateOrderData(amount, item);

        RequestBody formBody = new FormBody.Builder()
                .add("appid", input.AppId)
                .add("appuser", input.AppUser)
                .add("apptime", input.AppTime)
                .add("amount", input.Amount)
                .add("apptransid", input.AppTransId)
                .add("embeddata", input.EmbedData)
                .add("item", input.Item)
                .add("bankcode", input.BankCode)
                .add("description", input.Description)
                .add("mac", input.Mac)
                .build();

        JSONObject data = HttpProvider.sendPost(AppInfo.URL_CREATE_ORDER, formBody);
        return data;
    }
}
