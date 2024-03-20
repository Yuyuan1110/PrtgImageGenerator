package org.acom.httpClient.Impl;

import org.acom.Exception.CannotDetectServerException;
import org.acom.beans.ConfigBean;
import org.acom.httpClient.HttpConnector;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

public class HttpConnectorImpl implements HttpConnector {
    private int responseCode;
    private HttpURLConnection connection = null;

    @Override
    public int detectServer(ConfigBean configBean) {
        try {
            URL url = new URL(configBean.getProtocol() + "://" + configBean.getServerIP() + ":" + configBean.getPort() + "/api/table.xml?content=sensors&columns=sensor&username=" + configBean.getUsername() + "&password=" + configBean.getPassword());
            setDisableSSLCert();
            connection = (HttpsURLConnection) url.openConnection();
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
                setDisableSSLCert();
                connection = (HttpsURLConnection) url.openConnection();
                connection.setRequestProperty("Accept-Charset", "UTF-8");
                connection.setRequestMethod("GET");

                connection.setConnectTimeout(5000);
                connection.setReadTimeout(15000);
                System.out.println("Response Code: " + connection.getResponseCode());
                return connection;
            } catch (IOException e) {
                // 捕捉连接异常
                if (e instanceof java.net.SocketTimeoutException) {
                    System.out.println("Connection timed out. Retrying...(" + retryCount + 1 + "/" + maxRetries + ")");
                    retryCount++;
                } else {
                    throw new RuntimeException(e);
                }
            }
        }
        System.out.println("Max retries reached. Unable to establish connection. Throwing exception...");
        throw new RuntimeException("Unable to establish connection after max retries");

    }


    private void setDisableSSLCert() {

        // 创建信任所有证书的 TrustManager
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }
        };

        // 创建 SSL 上下文，忽略证书验证
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());


            // 将忽略证书验证的 SSL 上下文设置到 HttpsURLConnection
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }

    }
}