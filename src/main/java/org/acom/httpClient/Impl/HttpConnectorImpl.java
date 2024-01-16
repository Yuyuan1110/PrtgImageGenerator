package org.acom.httpClient.Impl;

import org.acom.beans.ConfigBean;
import org.acom.httpClient.HttpConnector;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnectorImpl implements HttpConnector {
    private int responseCode;
    @Override
    public int detectServer(ConfigBean configBean) {

        try {
            // 定義要訪問的 URL
            URL url = new URL(configBean.getProtocol()+"://"+configBean.getServerIP()+":"+configBean.getPort());
            System.out.println("print the url: "+url);
            // 打開連接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // 設置請求方法（GET、POST 等）
            connection.setRequestMethod("GET");

            responseCode = connection.getResponseCode();
            System.out.println("HTTP Response Code: " + responseCode);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseCode;
    }
}
