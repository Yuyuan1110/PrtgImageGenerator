package org.acom.prtgGenerator.Impl;

import org.acom.beans.ConfigBean;
import org.acom.configReader.ConfigReader;
import org.acom.configReader.Impl.ConfigReaderImpl;
import org.acom.prtgGenerator.URLGenerator;
import org.acom.prtgGenerator.XMLGenerator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class XMLGeneratorImpl implements XMLGenerator {
    private final String devicesPath = ".\\devices";

    @Override
    public void downloadXML(URL url, String path) {
        HttpURLConnection connection = null;
        InputStream in = null;
        InputStreamReader isr = null;
        BufferedReader bf = null;
        FileOutputStream fileOutputStream = null;

        try {
            System.out.println("connecting to " + url.toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(30000);
            System.out.println("Response Code: " + connection.getResponseCode());

            in = connection.getInputStream();
            isr = new InputStreamReader(in);
            bf = new BufferedReader(isr);
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = bf.readLine()) != null) {
                response.append(line).append(System.lineSeparator());
            }


            fileOutputStream = new FileOutputStream(path);
            fileOutputStream.write(response.toString().getBytes());
            System.out.println("Response written to file: " + path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (bf != null) {
                    bf.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (in != null) {
                    in.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                //noinspection CallToPrintStackTrace
                e.printStackTrace();
            }
        }

    }

    @Override
    public void createXMLConfig() {
        ConfigReader configReader = new ConfigReaderImpl();
        ConfigBean configBean = configReader.readConfig();

        URLGenerator urlGenerator = new URLGeneratorImpl();
        if (!new File("./settings.xml").exists()) {
            try {
                downloadSensorXML(configBean, urlGenerator);
            } catch (ParserConfigurationException | IOException | SAXException e) {
                throw new RuntimeException(e);
            }

        }

    }

    private void downloadSensorXML(ConfigBean configBean, URLGenerator urlGenerator) throws ParserConfigurationException, IOException, SAXException {
        folderCheck(configBean, urlGenerator);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document deviceXML = builder.parse(new File(devicesPath + "\\devices.xml"));
        Element rootElement = deviceXML.getDocumentElement();
        NodeList itemList = rootElement.getElementsByTagName("item");
        for (int i = 0; i < itemList.getLength(); i++) {
            Element itemElement = (Element) itemList.item(i);
            String objidValue = itemElement.getElementsByTagName("objid").item(0).getTextContent();
            URL url = urlGenerator.XMLURLGenerator(configBean, "sensor",objidValue);
            System.out.println("objid: " + objidValue);
            File f = new File(devicesPath + "\\" + objidValue);
            f.mkdir();
            downloadXML(url, f.getPath()+"\\"+objidValue+".xml");
        }
    }

    private void folderCheck(ConfigBean configBean, URLGenerator urlGenerator) {
        File file = new File(devicesPath + "\\devices.xml");
        if (!file.exists()) {
            new File(devicesPath).mkdir();

            try {
                URL url = urlGenerator.XMLURLGenerator(configBean, "device", "");
                downloadXML(url, devicesPath + "\\devices.xml");
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
