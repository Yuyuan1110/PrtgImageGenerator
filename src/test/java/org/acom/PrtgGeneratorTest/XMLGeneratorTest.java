package org.acom.PrtgGeneratorTest;

import org.acom.Exception.InvalidDateException;
import org.acom.beans.ConfigBean;
import org.acom.configReader.ConfigReader;
import org.acom.configReader.Impl.ConfigReaderImpl;
import org.acom.prtgGenerator.Impl.URLGeneratorImpl;
import org.acom.prtgGenerator.Impl.XMLDownloadImpl;
import org.acom.prtgGenerator.Impl.XMLGeneratorImpl;
import org.acom.prtgGenerator.URLGenerator;
import org.acom.prtgGenerator.XMLDownload;
import org.acom.prtgGenerator.XMLGenerator;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class XMLGeneratorTest {
    ConfigBean cb;
    ConfigReader rc = new ConfigReaderImpl();

    @Test
    public void XMLDownloadTest() throws IOException, InvalidDateException {
        URLGenerator ug = new URLGeneratorImpl();
        XMLDownload xg = new XMLDownloadImpl();
        cb = rc.readConfig();
        URL url = ug.XMLURLGenerator("device", "");
        xg.downloadXML(url, "."+File.separator+"devices"+File.separator+"devices.xml");
    }

    @Test
    public void configPathTest() throws ParserConfigurationException, IOException, SAXException, InvalidDateException {
        XMLDownload xmlGenerator = new XMLDownloadImpl();
        cb = rc.readConfig();
        xmlGenerator.createXMLConfig();
    }

    @Test
    public void testTEST(){
        XMLGenerator x = new XMLGeneratorImpl();
        x.settingsXMLGenerator();
    }
}
