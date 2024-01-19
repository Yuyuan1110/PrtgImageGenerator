package org.acom.prtgGenerator;

import org.acom.beans.ConfigBean;

import java.net.URL;

public interface XMLGenerator {
    public void downloadXML(URL[] urls);
    public void createXMLConfig();
}
