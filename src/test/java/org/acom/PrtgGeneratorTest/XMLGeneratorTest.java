package org.acom.PrtgGeneratorTest;

import org.acom.beans.ConfigBean;
import org.acom.configReader.ConfigReader;
import org.acom.configReader.Impl.ConfigReaderImpl;
import org.acom.prtgGenerator.Impl.URLGeneratorImpl;
import org.acom.prtgGenerator.Impl.XMLGeneratorImpl;
import org.acom.prtgGenerator.URLGenerator;
import org.acom.prtgGenerator.XMLGenerator;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;

public class XMLGeneratorTest {
    ConfigBean cb;
    ConfigReader rc = new ConfigReaderImpl();

    @Test
    public void XMLDownloadTest() throws IOException {
        URLGenerator ug = new URLGeneratorImpl();
        XMLGenerator xg = new XMLGeneratorImpl();
        cb = rc.readConfig();
        URL url = ug.XMLURLGenerator(cb, "device", "");
        xg.downloadXML(url, ".\\devices\\devices.xml");
    }

    @Test
    public void configPathTest() throws ParserConfigurationException, IOException, SAXException {
        XMLGenerator xmlGenerator = new XMLGeneratorImpl();
        cb = rc.readConfig();
        xmlGenerator.createXMLConfig();
    }
}
