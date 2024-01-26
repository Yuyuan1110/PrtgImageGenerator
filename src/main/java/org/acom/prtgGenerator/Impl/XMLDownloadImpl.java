package org.acom.prtgGenerator.Impl;

import org.acom.httpClient.Impl.HttpConnectorImpl;
import org.acom.prtgGenerator.URLGenerator;
import org.acom.prtgGenerator.XMLDownload;
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
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class XMLDownloadImpl implements XMLDownload {
    private final String devicesPath = "." + File.separator + "devices";
    private final URLGenerator urlGenerator = new URLGeneratorImpl();
    private final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    private final DocumentBuilder builder;

    {
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void downloadXML(URL url, String path) {
        HttpURLConnection connection = new HttpConnectorImpl().setConnection(url);

        try (InputStream in = connection.getInputStream();
             InputStreamReader isr = new InputStreamReader(in);
             BufferedReader bf = new BufferedReader(isr);
             FileOutputStream fileOutputStream = new FileOutputStream(path);
             OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8)

        ) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = bf.readLine()) != null) {
                response.append(line).append(System.lineSeparator());
            }

            writer.write(response.toString());
            System.out.println("Response written to file: " + path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

    }

    @Override
    public void createXMLConfig() {
        try {
            folderCheck();
            downloadSensorXML();
            downloadChannelXML();
        } catch (IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }


    private void downloadChannelXML() {
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(devicesPath))) {
            for (Path path : directoryStream) {
                if (Files.isDirectory(path)) {
                    Document deviceXML = builder.parse(new File(devicesPath + File.separator + path.getFileName() + File.separator + path.getFileName() + ".xml"));
                    Element rootElement = deviceXML.getDocumentElement();
                    NodeList itemList = rootElement.getElementsByTagName("item");
                    for (int i = 0; i < itemList.getLength(); i++) {
                        Element itemElement = (Element) itemList.item(i);
                        String objidValue = itemElement.getElementsByTagName("objid").item(0).getTextContent();
                        URL url = urlGenerator.XMLURLGenerator("channel", objidValue);
                        System.out.println("objid: " + objidValue);
                        File f = new File(devicesPath + File.separator + path.getFileName() + File.separator + objidValue);
                        f.mkdir();
                        downloadXML(url, f.getPath() + File.separator + objidValue + ".xml");
                    }
                }
            }

        } catch (IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    private void downloadSensorXML() throws IOException, SAXException {
        Document deviceXML = builder.parse(new File(devicesPath + File.separator + "devices.xml"));
        Element rootElement = deviceXML.getDocumentElement();
        NodeList itemList = rootElement.getElementsByTagName("item");

        for (int i = 0; i < itemList.getLength(); i++) {
            Element itemElement = (Element) itemList.item(i);
            String objidValue = itemElement.getElementsByTagName("objid").item(0).getTextContent();
            URL url = urlGenerator.XMLURLGenerator("sensor", objidValue);
            System.out.println("objid: " + objidValue);
            File f = new File(devicesPath + File.separator + objidValue);
            f.mkdir();
            downloadXML(url, f.getPath() + File.separator + objidValue + ".xml");
        }
    }

    private void folderCheck() {
        new File(devicesPath).mkdir();
        File file = new File(devicesPath + File.separator + "devices.xml");
        if (!file.exists()) {
            try {
                URL url = urlGenerator.XMLURLGenerator("device", "");
                downloadXML(url, devicesPath + File.separator + "devices.xml");
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
