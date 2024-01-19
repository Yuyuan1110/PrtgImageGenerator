package org.acom.prtgGenerator.Impl;

import org.acom.prtgGenerator.XMLGenerator;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class XMLGeneratorImpl implements XMLGenerator {
    @Override
    public void downloadXML(URL[] urls) {
        HttpURLConnection connection = null;
        InputStream in = null;
        InputStreamReader isr = null;
        BufferedReader bf = null;
        FileOutputStream fileOutputStream = null;
        for (URL url : urls) {
            try {
                System.out.println("connecting to "+url.toString());
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(30000);

                int responseCode = connection.getResponseCode();
                System.out.println("Response Code: " + responseCode);

                in = connection.getInputStream();
                isr = new InputStreamReader(in);
                bf = new BufferedReader(isr);
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = bf.readLine()) != null) {
                    response.append(line);
                }


                fileOutputStream = new FileOutputStream(".\\response.xml");
                fileOutputStream.write(response.toString().getBytes());
                System.out.println("Response written to file: response.txt");
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                if(connection != null){
                    connection.disconnect();
                }
                in != null ? in.close():null;
            }
        }
    }

    @Override
    public void createXMLConfig() {

    }
}
