package org.acom.PrtgGeneratorTest;

import org.acom.beans.ConfigBean;
import org.acom.configReader.ConfigReader;
import org.acom.configReader.Impl.ConfigReaderImpl;
import org.acom.prtgGenerator.Impl.URLGeneratorImpl;
import org.acom.prtgGenerator.Impl.XMLGeneratorImpl;
import org.acom.prtgGenerator.URLGenerator;
import org.acom.prtgGenerator.XMLGenerator;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class XMLGeneratorTest {

    @Test
    public void XMLDownloadTest() throws MalformedURLException {
        URLGenerator ug = new URLGeneratorImpl();
        XMLGenerator xg = new XMLGeneratorImpl();
        ConfigBean cb;
        ConfigReader rc = new ConfigReaderImpl();
        cb = rc.readConfig();
        URL[] urls = ug.XMLURLGenerator(cb, "device");
        xg.downloadXML(urls);
    }
}
