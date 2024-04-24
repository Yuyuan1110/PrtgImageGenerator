package org.acom.prtgGenerator.Impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.acom.Exception.InvalidDateException;
import org.acom.beans.GraphBean;
import org.acom.beans.HistoryBean;
import org.acom.httpClient.Impl.HttpConnectorImpl;
import org.acom.prtgGenerator.PRTGGenerator;
import org.acom.prtgGenerator.URLGenerator;
import org.acom.prtgGenerator.XMLDownload;
import org.acom.tools.CommonTools;
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
import java.util.Scanner;

public class PRTGGeneratorImpl implements PRTGGenerator {
    private final URLGenerator urlGenerator = new URLGeneratorImpl();
    private final String settingsPath = "." + File.separator + "settings.xml";
    private final String graphPath = "." + File.separator + "graph";
    private final String historyPath = "." + File.separator + "history";
//    private final GraphBean gb = new GraphBean();
    private final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder;
    HttpURLConnection connection = null;
    CommonTools commonTools = new CommonTools();
    @Override
    public void graphDownload(GraphBean gb) {

        new File(graphPath).mkdir();

        try {
            builder = factory.newDocumentBuilder();
            Document settingsXML = builder.parse(new File(settingsPath));
            Element rootElement = settingsXML.getDocumentElement();
            NodeList deviceList = rootElement.getElementsByTagName("device");

            for (int i = 0; i < deviceList.getLength(); i++) {
                Element deviceElement = (Element) deviceList.item(i);
                String deviceName = deviceElement.getElementsByTagName("deviceName").item(0).getTextContent();
                deviceName = CommonTools.rename(deviceName);
                new File(graphPath + File.separator + deviceName).mkdir();
                NodeList sensorsList = deviceElement.getElementsByTagName("sensors");
                Element sensorsElement = (Element) sensorsList.item(0);
                processSensors(gb, sensorsElement, deviceName);
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void graphDownload(GraphBean gb, String settingsFile) {
        new File(graphPath).mkdir();

        try {
            builder = factory.newDocumentBuilder();
            Document settingsXML = builder.parse(new File(settingsFile));
            Element rootElement = settingsXML.getDocumentElement();
            NodeList deviceList = rootElement.getElementsByTagName("device");

            for (int i = 0; i < deviceList.getLength(); i++) {
                Element deviceElement = (Element) deviceList.item(i);
                String deviceName = deviceElement.getElementsByTagName("deviceName").item(0).getTextContent();
                deviceName = CommonTools.rename(deviceName);
                new File(graphPath + File.separator + deviceName).mkdir();
                NodeList sensorsList = deviceElement.getElementsByTagName("sensors");
                Element sensorsElement = (Element) sensorsList.item(0);
                processSensors(gb, sensorsElement, deviceName);
            }
        } catch (ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e){
            System.out.println("Can't not open settings file.");
            e.printStackTrace();
        }
    }

    @Override
    public void historyDownload(HistoryBean historyBean, String type) {

        new File(historyPath).mkdir();
        try {
            builder = factory.newDocumentBuilder();
            Document settingsXML = builder.parse(new File(settingsPath));
            Element rootElement = settingsXML.getDocumentElement();
            NodeList deviceList = rootElement.getElementsByTagName("device");
            XMLDownload xmlDownload = new XMLDownloadImpl();
            for (int i = 0; i < deviceList.getLength(); i++) {
                Element deviceElement = (Element) deviceList.item(i);
                String deviceName = deviceElement.getElementsByTagName("deviceName").item(0).getTextContent();
                deviceName = CommonTools.rename(deviceName);
                new File(historyPath + File.separator + deviceName).mkdir();
                NodeList sensorsList = deviceElement.getElementsByTagName("sensors");
                Element sensorsElement = (Element) sensorsList.item(0);
                NodeList sensorList = sensorsElement.getElementsByTagName("sensor");
                for (int j = 0; j < sensorList.getLength(); j++) {
                    Element sensorElement = (Element) sensorList.item(j);
                    String id = sensorElement.getElementsByTagName("sensorID").item(0).getTextContent();
                    historyBean.setId(id);
                    URL url = urlGenerator.XMLURLGenerator("history", historyBean);
                    xmlDownload.downloadXML(url, historyPath + File.separator + deviceName + File.separator + id + "." + type);
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException | InvalidDateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void graphSingleDownload(GraphBean gb) {

        try {
            URL url = urlGenerator.XMLURLGenerator("json", gb.getId());
            connection = new HttpConnectorImpl().setConnection(url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.toString());
            System.out.println("Type channel id if show ot in graphic. e.g.: 0,1,2");
            for (JsonNode channel : jsonNode.get("channels")) {
                System.out.println("Channel ID: "+ channel.get("objid"));
                System.out.println("Channel name: "+ channel.get("name"));
                System.out.println("\n--------------------");
            }
            Scanner scanner = new Scanner(System.in);
            String tmp = scanner.nextLine();
            gb.setHide(tmp.split(","));
            URL graphUrl = urlGenerator.GraphURLGenerator(gb);
            new File(graphPath).mkdir();
            downLoadImage(graphUrl, graphPath + File.separator + gb.getId() + ".png");


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void processSensors(GraphBean gb, Element sensorsElement, String deviceName) throws IOException {
        NodeList sensorList = sensorsElement.getElementsByTagName("sensor");
        for (int j = 0; j < sensorList.getLength(); j++) {
            Element sensorElement = (Element) sensorList.item(j);
            String sensorName = sensorElement.getElementsByTagName("sensorName").item(0).getTextContent();
            sensorName = CommonTools.rename(sensorName);
            Element channelsElement = (Element) sensorElement.getElementsByTagName("channels").item(0);
            processChannels(gb, channelsElement);
            gb.setId(sensorElement.getElementsByTagName("sensorID").item(0).getTextContent());
            URL url = urlGenerator.GraphURLGenerator(gb);
            downLoadImage(url, graphPath + File.separator + deviceName + File.separator + sensorName + ".png");
        }
    }

    private void processChannels(GraphBean gb,Element channelsElement) {
        NodeList channelList = channelsElement.getElementsByTagName("channel");
        String[] channels = new String[channelList.getLength()];
        for (int k = 0; k < channelList.getLength(); k++) {
            Element channelElement = (Element) channelList.item(k);
            channels[k] = channelElement.getElementsByTagName("channelID").item(0).getTextContent();
        }
//        System.out.println(channels.length);
        gb.setHide(channels);
    }

    private void downLoadImage(URL url, String path) {

        connection = new HttpConnectorImpl().setConnection(url);
        try (InputStream inputStream = connection.getInputStream();
             OutputStream outputStream = Files.newOutputStream(Paths.get(path))) {

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                commonTools.svgToPng(inputStream, outputStream);
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
}
