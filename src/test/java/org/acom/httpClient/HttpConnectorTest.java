package org.acom.httpClient;

import org.acom.beans.ConfigBean;
import org.acom.configReader.ConfigReader;
import org.acom.configReader.Impl.ConfigReaderImpl;
import org.acom.httpClient.Impl.HttpConnectorImpl;
import org.junit.Test;

public class HttpConnectorTest {
    @Test
    public void detectServer(){
        ConfigBean CB = new ConfigBean();
        ConfigReader rc = new ConfigReaderImpl();
        CB = rc.readConfig();
        HttpConnector hc = new HttpConnectorImpl();
        hc.detectServer(CB);
    }
}
