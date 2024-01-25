package org.acom.mainFunction.Impl;

import org.acom.Exception.FileMissingException;
import org.acom.Exception.InvalidDateException;
import org.acom.beans.ConfigBean;
import org.acom.configReader.Impl.ConfigReaderImpl;
import org.acom.mainFunction.LogicChecker;
import org.acom.prtgGenerator.Impl.PRTGGeneratorImpl;
import org.acom.prtgGenerator.Impl.URLGeneratorImpl;
import org.acom.prtgGenerator.Impl.XMLDownloadImpl;
import org.acom.prtgGenerator.Impl.XMLGeneratorImpl;
import org.acom.prtgGenerator.PRTGGenerator;
import org.acom.prtgGenerator.URLGenerator;
import org.acom.prtgGenerator.XMLDownload;
import org.acom.prtgGenerator.XMLGenerator;
import org.apache.commons.cli.CommandLine;
import org.w3c.dom.ls.LSOutput;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class LogicCheckerImpl implements LogicChecker {
    private static final String settingsPath = "." + File.separator + "settings.xml";
    private static final Scanner scanner = new Scanner(System.in);
    private static final URLGenerator urlGenerator = new URLGeneratorImpl();
    private static final String devicePath = "." + File.separator + "devices" + File.separator + "devices.xml";
    @Override
    public void configFileExistsChecker() {

        System.out.println("checking if the \"config.properties\" file exists?");
        new ConfigReaderImpl().readConfig();
        System.out.println("\"config.properties OK!\"");


        System.out.println("checking if the \"setting.xml\" file exists?");
        if(new File(settingsPath).exists()){
            System.out.println("\"setting.xml OK!\"");
        } else {
            System.out.println("Can't find the setting.xml file, please check if file is exists!");
            buildSettingXMLChecker();
        }
    }




    private void buildSettingXMLChecker() {
        System.out.println("Do you want to download the setting.xml or rebuild? [D]ownload / [R]ebuild / [E]xit, default: [E]. ");
        String tmp = scanner.nextLine();
        String answer = tmp.isEmpty() ? "E" : tmp;
        if (answer.equalsIgnoreCase("d") || answer.equalsIgnoreCase("download")) {
            downloadDeviceXML();
            XMLGenerator xmlGenerator = new XMLGeneratorImpl();
            xmlGenerator.settingsXMLGenerator();

        } else if (answer.equalsIgnoreCase("R") || answer.equalsIgnoreCase("rebuild")){
            if(!new File(devicePath).exists()){
                downloadDeviceXML();
            }
            XMLGenerator xmlGenerator = new XMLGeneratorImpl();
            xmlGenerator.settingsXMLGenerator();
        }
        System.out.println("Please check if the \"settings.xml\" file exists then re-run script.");
        System.exit(0);
    }

    private void downloadDeviceXML(){
        XMLDownload xmlDownload = new XMLDownloadImpl();
        try {
            URL url = urlGenerator.XMLURLGenerator("device", "");
            File f = new File("." + File.separator + "devices");
            if (f.exists()) {
                f.delete();
            }
            f.mkdir();
            xmlDownload.downloadXML(url,devicePath );
            xmlDownload.createXMLConfig();

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
