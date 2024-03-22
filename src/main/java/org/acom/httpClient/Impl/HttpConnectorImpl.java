package org.acom.httpClient.Impl;

import org.acom.Exception.CannotDetectServerException;
import org.acom.beans.ConfigBean;
import org.acom.httpClient.HttpConnector;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

public class HttpConnectorImpl implements HttpConnector {
    private int responseCode;
    private HttpsURLConnection connection = null;

    @Override
    public int detectServer(ConfigBean configBean) {
        try {
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

            // 啟用忽略 SSL/TLS 憑證驗證
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new IgnoreHostnameVerifier());
            URL url = new URL(configBean.getProtocol() + "://" + configBean.getServerIP() + ":" + configBean.getPort() + "/api/table.xml?content=sensors&columns=sensor&username=" + configBean.getUsername() + "&password=" + configBean.getPassword());


            connection = (HttpsURLConnection) url.openConnection();
            connection.setSSLSocketFactory(sslContext.getSocketFactory());
            connection.setRequestMethod("GET");
            connection.connect();
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
//            if (httpsConnection != null) {
//                httpsConnection.disconnect();
//            }
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

                // 啟用忽略 SSL/TLS 憑證驗證
                SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
                HttpsURLConnection.setDefaultHostnameVerifier(new IgnoreHostnameVerifier());

                connection = (HttpsURLConnection) url.openConnection();
                connection.setSSLSocketFactory(sslContext.getSocketFactory());
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept-Charset", "UTF-8");
                connection.connect();
//                connection = (HttpURLConnection) httpsConnection;

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
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (KeyManagementException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Max retries reached. Unable to establish connection. Throwing exception...");
        throw new RuntimeException("Unable to establish connection after max retries");

    }


    private class IgnoreHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            // 忽略主機名稱驗證，始終返回 true
            return true;
        }
    }
}