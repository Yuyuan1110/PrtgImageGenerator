package org.acom.prtgGenerator;

import org.acom.beans.ConfigBean;

import java.io.IOException;
import java.net.URL;

public interface XMLDownload {
    public void downloadXML(URL urls, String path) throws IOException;
    public void createXMLConfig();
}
