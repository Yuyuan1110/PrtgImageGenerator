package org.acom.mainFunction.Impl;

import org.acom.mainFunction.CommonsCLI;
import org.apache.commons.cli.*;

public class CommonsCLIImpl implements CommonsCLI {
    @Override
    public CommandLine getCommonsCLI(String[] args) {
        Options options = new Options();
        options.addOption("f", "feature",true, "Feature: graphic/history");
        options.addOption("s", "sdate",true, "Start date, format: \"yyyy-MM-dd-HH-mm-ss\"");
        options.addOption("e", "edate",true, "End date, format: \"yyyy-MM-dd-HH-mm-ss\"");
        options.addOption("S", "settingsFile",false, "Specify settings file.");
        options.addOption("i", "interval",false, "set interval, no interval = 0, 1 hour = 3600, 1 day = 86400.");
        options.addOption("r", "rebuild",false, "Re-build the \"settings.xml\" file.");
        options.addOption("id",true, "Get data from specific device ID.");
        options.addOption("h", "help", false, "help");
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        if(cmd.hasOption("help") || cmd.hasOption("h")){
            help();
        }
        return cmd;
    }

    private void help(){
        System.out.println("HOW TO USED: ");
        System.out.println("[-f] [--feature]: set feature. \nNeed type parameter: \n\"graphic\" to download history graphic \n\"history\" to download history data. \n\"rebuild\" to rebuild settings.xml file.");
        System.out.println("[-s] [--sdate]: set query start date, format to \"yyyy-MM-dd-HH-mm-ss\"");
        System.out.println("[-e] [--edate]: set query end date, format to \"yyyy-MM-dd-HH-mm-ss\"");
        System.out.println("\nCOMMAND: java -jar PRTG_Generator.jar -f [graph/history] --[start date] --[end date] \nNOTE: date format to \"yyyy-MM-dd-HH-mm-ss\"\n");
        System.exit(0);
    }

}
