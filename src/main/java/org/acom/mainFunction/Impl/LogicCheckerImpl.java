package org.acom.mainFunction.Impl;

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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class LogicCheckerImpl implements LogicChecker {
    private static final String settingsPath = "." + File.separator + "settings.xml";
    private static final Scanner scanner = new Scanner(System.in);
    private static final URLGenerator urlGenerator = new URLGeneratorImpl();
    private static final String devicePath = "." + File.separator + "devices" + File.separator + "devices.xml";
    private static final PRTGGenerator prtgGenerator = new PRTGGeneratorImpl();
    @Override
    public void configFileExistsChecker() {

        System.out.println("checking if the \"config.properties\" file exists?");
        new ConfigReaderImpl().readConfig();
        System.out.println("\"config.properties OK!\"");


        System.out.println("checking if the \"setting.xml\" file exists?");
        if(new File(settingsPath).exists()){
            System.out.println("\"setting.xml OK!\"");
        } else {
            System.out.println("settings.xml not found, please check if file is exists!");
            buildSettingXMLChecker();
        }
    }

    @Override
    public void featureGraphic(CommandLine cmd) {

        if(cmd.hasOption("id")){
            System.out.println("Starting to download graphic since \""+cmd.getOptionValue("s")+"\" to \"" + cmd.getOptionValue("e")+"\", id: " + cmd.getOptionValue("id")+" ? [y/N]");
            String tmp = scanner.nextLine();
            if(tmp.equalsIgnoreCase("y") || tmp.equalsIgnoreCase("yes")){
                prtgGenerator.graphSingleDownload(cmd.getOptionValue("s"), cmd.getOptionValue("e"), cmd.getOptionValue("id"));
            }
            System.exit(0);
        }


        System.out.println("Starting to download graphic since \""+cmd.getOptionValue("s")+"\" to \"" + cmd.getOptionValue("e")+ "\" ? [y/N]");

        String tmp = scanner.nextLine();
        if(tmp.equalsIgnoreCase("y") || tmp.equalsIgnoreCase("yes")){
            prtgGenerator.graphDownload(cmd.getOptionValue("s"), cmd.getOptionValue("e"));
        } else {
            System.out.println("bye bye!");
            System.exit(0);
        }
    }

    @Override
    public void featureHistory(CommandLine cmd) {
        System.out.println("Starting to download history file, xml or csv? [xml/csv] default: xml");
        if(!scanner.nextLine().equalsIgnoreCase("csv")){
            prtgGenerator.historyDownload(cmd.getOptionValue("s"), cmd.getOptionValue("e"), "xml");
        } else{
            prtgGenerator.historyDownload(cmd.getOptionValue("s"), cmd.getOptionValue("e"), "csv");
        }
        System.exit(0);
    }

    @Override
    public void featureRebuild(CommandLine cmd) {
        System.out.println("starting to re-build the \"settings.xml\" file.");
        XMLGenerator xmlGenerator = new XMLGeneratorImpl();
        xmlGenerator.settingsXMLGenerator();
        System.out.println("re-build completed, please check the file content.");
        System.exit(0);
    }


    private void buildSettingXMLChecker() {
        System.out.println("Do you want to download the setting.xml or rebuild? [D]ownload / [R]ebuild / [E]xit, default: [E]. ");
        String tmp = scanner.nextLine();
        String answer = tmp.isEmpty() ? "E" : tmp;
        if (answer.equalsIgnoreCase("d") || answer.equalsIgnoreCase("download")) {
            System.out.println("Do you want to set the group ID or download all device? type [\"ID\"] or [S]kip, default [S]. ");
            tmp = scanner.nextLine();
            if(isInteger(tmp)){
                System.out.println("String to download devices.xml of group ID: "+tmp);
                downloadDeviceXML(tmp);
                XMLGenerator xmlGenerator = new XMLGeneratorImpl();
                xmlGenerator.settingsXMLGenerator();
            }else {
                System.out.println("Download devices.xml of all devices.");
                System.exit(0);
                downloadDeviceXML("");
                XMLGenerator xmlGenerator = new XMLGeneratorImpl();
                xmlGenerator.settingsXMLGenerator();
            }


        } else if (answer.equalsIgnoreCase("R") || answer.equalsIgnoreCase("rebuild")){
            if(!new File(devicePath).exists()){
                System.out.println("Devices.xml file not found, starting to download devices.xml of all devices.");
                downloadDeviceXML("");
            }
            XMLGenerator xmlGenerator = new XMLGeneratorImpl();
            xmlGenerator.settingsXMLGenerator();
        }
        System.out.println("Please check if the \"settings.xml\" file exists then re-run script.");
        System.exit(0);
    }

    private void downloadDeviceXML(String objid){
        XMLDownload xmlDownload = new XMLDownloadImpl();
        try {
            URL url = urlGenerator.XMLURLGenerator("device", objid);
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
    private boolean isInteger(String str){
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
