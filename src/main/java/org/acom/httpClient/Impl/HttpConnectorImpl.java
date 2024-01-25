package org.acom.httpClient.Impl;

import org.acom.Exception.CannotDetectServerException;
import org.acom.beans.ConfigBean;
import org.acom.httpClient.HttpConnector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnectorImpl implements HttpConnector {
    private int responseCode;
    private HttpURLConnection connection = null;

    @Override
    public int detectServer(ConfigBean configBean) {
        try {
            URL url = new URL(configBean.getProtocol() + "://" + configBean.getServerIP() + ":" + configBean.getPort() + "/api/table.xml?content=sensors&columns=sensor&username=" + configBean.getUsername() + "&password=" + configBean.getPassword());

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            responseCode = connection.getResponseCode();
            System.out.println("HTTP Response Code: " + responseCode);

            if (responseCode != 200) {
                throw new CannotDetectServerException("Detect server failed, response code:" + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return responseCode;
    }

    @Override
    public HttpURLConnection setConnection(URL url) {
        System.out.println("\nConnecting to " + url.toString());
        int maxRetries = 3;
        int retryCount = 0;

        while (retryCount < maxRetries) {
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(30000);
                System.out.println("Response Code: " + connection.getResponseCode());
                return connection;
            } catch (IOException e) {
                // 捕捉连接异常
                if (e instanceof java.net.SocketTimeoutException) {
                    System.out.println("Connection timed out. Retrying...("+retryCount+1+"/"+maxRetries+")");
                    retryCount++;
                } else {
                    throw new RuntimeException(e);
                }
            }
        }
        System.out.println("Max retries reached. Unable to establish connection. Throwing exception...");
        throw new RuntimeException("Unable to establish connection after max retries");

    }
}
