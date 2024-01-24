package org.acom.prtgGenerator.Impl;

import org.acom.beans.NameAndID;
import org.acom.prtgGenerator.XMLGenerator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;


public class XMLGeneratorImpl implements XMLGenerator {
    private final String devicesPath = "." + File.separator + "devices";

    @Override
    public void settingsXMLGenerator() {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document documentNew = documentBuilder.newDocument();
            Document documentReadDevices = documentBuilder.parse(new File(devicesPath + File.separator + "devices.xml"));

            Element rootElement = documentNew.createElement("devices");
            documentNew.appendChild(rootElement);

            NameAndID devicesName = getNameAndId(documentReadDevices);
            for (int i = 0; i < devicesName.getObjID().size(); i++) {
                Element device = documentNew.createElement("device");
                rootElement.appendChild(device);
                createElementWithTextContent(documentNew, device, "deviceName", devicesName.getObjName().get(i));
                createElementWithTextContent(documentNew, device, "deviceID", devicesName.getObjID().get(i));
                Element sensors = documentNew.createElement("sensors");
                device.appendChild(sensors);
                Document documentReadSensors = documentBuilder.parse(new File(devicesPath + File.separator + devicesName.getObjID().get(i) + File.separator + devicesName.getObjID().get(i) + ".xml"));
                NameAndID sensorName = getNameAndId(documentReadSensors);
                for (int j = 0; j < sensorName.getObjID().size(); j++) {
                    Element sensor = documentNew.createElement("sensor");
                    sensors.appendChild(sensor);
                    createElementWithTextContent(documentNew, sensor, "sensorName", sensorName.getObjName().get(j));
                    createElementWithTextContent(documentNew, sensor, "sensorID", sensorName.getObjID().get(j));
                    Element channels = documentNew.createElement("channels");
                    sensor.appendChild(channels);
                    Document documentReadChannels = documentBuilder.parse(new File(devicesPath + File.separator + devicesName.getObjID().get(i) + File.separator + sensorName.getObjID().get(j) + File.separator + sensorName.getObjID().get(j) + ".xml"));
                    NameAndID channelName = getNameAndId(documentReadChannels);
                    for (int k = 0; k < channelName.getObjID().size(); k++) {
                        Element channel = documentNew.createElement("channel");
                        channels.appendChild(channel);
                        createElementWithTextContent(documentNew, channel, "channelName", channelName.getObjName().get(k));
                        createElementWithTextContent(documentNew, channel, "channelID", channelName.getObjID().get(k));
                    }
                }
            }


            // Save the document to an XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(documentNew);

            // Specify the file path
            File outputFile = new File("."+File.separator+"settings.xml");
            StreamResult result = new StreamResult(outputFile);

            // Transform and save
            transformer.transform(source, result);

            System.out.println("XML file created successfully at: " + outputFile.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createElementWithTextContent(Document document, Element parent, String elementName, String textContent) {
        // Create a generic element with text content
        Element element = document.createElement(elementName);
        element.appendChild(document.createTextNode(textContent));
        parent.appendChild(element);
    }

    private NameAndID getNameAndId(Document document) {
        Element rootElement = document.getDocumentElement();
        NodeList itemList = rootElement.getElementsByTagName("item");
        NameAndID nameAndID = new NameAndID();
        ArrayList<String> objid = new ArrayList<>();
        ArrayList<String> objname = new ArrayList<>();
        //download every devices each xml.
        for (int i = 0; i < itemList.getLength(); i++) {
            Element itemElement = (Element) itemList.item(i);
            objid.add(itemElement.getElementsByTagName("objid").item(0).getTextContent());
            objname.add(itemElement.getElementsByTagName("name").item(0).getTextContent());
        }
        nameAndID.setObjID(objid);
        nameAndID.setObjName(objname);
        return nameAndID;
    }
}


