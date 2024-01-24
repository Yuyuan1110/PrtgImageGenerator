package org.acom.prtgGenerator.Impl;

import org.acom.Exception.InvalidDateException;
import org.acom.beans.GraphBean;
import org.acom.httpClient.Impl.HttpConnectorImpl;
import org.acom.prtgGenerator.PRTGGenerator;
import org.acom.prtgGenerator.URLGenerator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PRTGGeneratorImpl implements PRTGGenerator {
    URLGenerator urlGenerator = new URLGeneratorImpl();
    final String settingsPath = "." + File.separator + "settings.xml";
    final String graphPath = "." + File.separator + "graph";
    final String historyPath = "." + File.separator + "history";
    GraphBean gb = new GraphBean();
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder;


    HttpURLConnection connection = null;

    @Override
    public void graphDownload(String sdate, String edate) {


        new File(graphPath).mkdir();

        try {
            gb.setStartDate(sdate);
            gb.setEndDate(edate);
            builder = factory.newDocumentBuilder();
            Document settingsXML = builder.parse(new File(settingsPath));
            Element rootElement = settingsXML.getDocumentElement();
            NodeList deviceList = rootElement.getElementsByTagName("device");

            for (int i = 0; i < deviceList.getLength(); i++) {
                Element deviceElement = (Element) deviceList.item(i);
                String deviceName = deviceElement.getElementsByTagName("deviceName").item(0).getTextContent();
                new File(graphPath + File.separator + deviceName).mkdir();
                NodeList sensorsList = deviceElement.getElementsByTagName("sensors");
                Element sensorsElement = (Element) sensorsList.item(0);
                processSensors(sensorsElement, deviceName);
            }
        } catch (ParserConfigurationException | IOException | SAXException | InvalidDateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void historyDownload(String sdate, String edate, String type) {

        new File(historyPath).mkdir();

        try {
            builder = factory.newDocumentBuilder();
            Document settingsXML = builder.parse(new File(settingsPath));
            Element rootElement = settingsXML.getDocumentElement();
            NodeList deviceList = rootElement.getElementsByTagName("device");

            for (int i = 0; i < deviceList.getLength(); i++) {
                Element deviceElement = (Element) deviceList.item(i);
                String deviceName = deviceElement.getElementsByTagName("deviceName").item(0).getTextContent();
                new File(historyPath + File.separator + deviceName).mkdir();
                NodeList sensorsList = deviceElement.getElementsByTagName("sensors");
                Element sensorsElement = (Element) sensorsList.item(0);
                NodeList sensorList = sensorsElement.getElementsByTagName("sensor");
                for (int j = 0; j < sensorList.getLength(); j++) {
                    Element sensorElement = (Element) sensorList.item(j);
                    String id = sensorElement.getElementsByTagName("sensorID").item(0).getTextContent();
                    URL url = urlGenerator.XMLURLGenerator("history", id, sdate, edate);
                    downLoadXML(url, graphPath + File.separator + deviceName + File.separator + id + "."+type);
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    private void processSensors(Element sensorsElement, String deviceName) throws IOException {
        NodeList sensorList = sensorsElement.getElementsByTagName("sensor");
        for (int j = 0; j < sensorList.getLength(); j++) {
            Element sensorElement = (Element) sensorList.item(j);
            String sensorName = sensorElement.getElementsByTagName("sensorName").item(0).getTextContent();
            Element channelsElement = (Element) sensorElement.getElementsByTagName("channels").item(0);
            processChannels(channelsElement);
            gb.setId(Integer.parseInt(sensorElement.getElementsByTagName("sensorID").item(0).getTextContent()));
            URL url = urlGenerator.GraphURLGenerator(gb);
            sensorName = rename(sensorName);
            downLoadImage(url, graphPath + File.separator + deviceName + File.separator + sensorName + ".png");
        }
    }

    private void processChannels(Element channelsElement) {
        NodeList channelList = channelsElement.getElementsByTagName("channel");
        int[] channels = new int[channelList.getLength()];
        for (int k = 0; k < channelList.getLength(); k++) {
            Element channelElement = (Element) channelList.item(k);
            channels[k] = Integer.parseInt(channelElement.getElementsByTagName("channelID").item(0).getTextContent());
        }
        gb.setHide(channels);
    }

    private void downLoadImage(URL url, String path) {

        try (InputStream inputStream = connection.getInputStream();
             OutputStream outputStream = Files.newOutputStream(Paths.get(path))) {
            connection = new HttpConnectorImpl().setConnection(url);

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                System.out.println("Image downloaded successfully to: " + path);
            } else {
                System.err.println("Failed to download image. Response Code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private String rename(String original) {

        String stringWithoutSpecialChars = original.replaceAll("[<>:\\\"/\\\\\\\\|?*]", "");


        return stringWithoutSpecialChars.replaceAll("\\s{2,}", " ").replaceAll("\\s", "_");
    }
}
