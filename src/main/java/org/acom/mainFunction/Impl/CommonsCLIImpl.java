package org.acom.mainFunction.Impl;

import org.acom.mainFunction.CommonsCLI;
import org.apache.commons.cli.*;

public class CommonsCLIImpl implements CommonsCLI {
    @Override
    public CommandLine getCommonsCLI(String[] args) throws ParseException {
        Options options = new Options();
        options.addOption("f", "feature",true, "Feature: graphic/history");
        options.addOption("s", "sdate",true, "Start date, format: \"yyyy-MM-dd-HH-mm-ss\"");
        options.addOption("e", "edate",true, "End date, format: \"yyyy-MM-dd-HH-mm-ss\"");
        options.addOption("r", "rebuild",false, "Re-build the \"settings.xml\" file.");
        options.addOption("H", "hide",false, "set the hide data.");
        options.addOption("id",true, "Get data from specific device ID.");
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
        if(!cmd.hasOption("f") && !cmd.hasOption("feature") && !cmd.hasOption("r") && !cmd.hasOption("rebuild")){
            System.out.println("Required \"-f\" or \"--feature\" argument to specify config file.");
            System.exit(-1);
        }

        return cmd;
    }


}
