package com.example.mission1.openApi;

import com.example.mission1.service.MariaDBConnector;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Objects;

public class OpenApi extends MariaDBConnector {
    private static final String URL = "http://openapi.seoul.go.kr:8088/6a44486151796a6a39305858656443/json/TbPublicWifiInfo/%s/%s";

    /**
     * api를 통해 와이파이 정보 json으로 받아오기
     *
     * @param start - startIndex
     * @param end   - endIndex
     * @return JsonObject
     */
    public JsonObject getOpenApi(int start, int end) {
        JsonObject responseBody = null;

        try {
            String url = String.format(URL, start, end);
            OkHttpClient client = new OkHttpClient();
            Request.Builder builder = new Request.Builder().url(url).get();
            Request request = builder.build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                responseBody = JsonParser.parseString(Objects.requireNonNull(response.body()).string()).getAsJsonObject();
            }
        } catch (Exception e) {
            System.out.println("openApi error = " + e.getMessage());
            e.printStackTrace();
        }

        return responseBody;
    }
}
