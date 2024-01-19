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
        HttpURLConnection connection = null;
        try {
            URL url = new URL(configBean.getProtocol()+"://"+configBean.getServerIP()+":"+configBean.getPort());
            System.out.println("print the url: "+url);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            responseCode = connection.getResponseCode();
            System.out.println("HTTP Response Code: " + responseCode);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(connection != null){
                connection.disconnect();
            }
        }
        return responseCode;
    }
}
