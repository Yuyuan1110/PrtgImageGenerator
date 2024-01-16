package org.acom.httpClient;

import org.acom.beans.ConfigBean;

public interface HttpConnector {
    int detectServer(ConfigBean configBean);
}
