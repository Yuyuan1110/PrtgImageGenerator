package org.acom.httpClient;

import org.acom.beans.ConfigBean;

import java.net.HttpURLConnection;
import java.net.URL;

public interface HttpConnector {
    int detectServer(ConfigBean configBean);
    HttpURLConnection setConnection(URL url);
}
