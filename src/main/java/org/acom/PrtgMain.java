package org.acom;


import org.acom.mainFunction.CommonsCLI;
import org.acom.mainFunction.Impl.CommonsCLIImpl;
import org.acom.Exception.CannotDetectServerException;
import org.acom.Exception.InvalidDateException;
import org.acom.mainFunction.Impl.LogicCheckerImpl;
import org.acom.mainFunction.LogicChecker;
import org.acom.prtgGenerator.PRTGGenerator;
import org.acom.prtgGenerator.Impl.PRTGGeneratorImpl;
import org.acom.prtgGenerator.Impl.XMLGeneratorImpl;
import org.acom.prtgGenerator.XMLGenerator;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.Scanner;

public class PrtgMain {
    public static void main(String[] args) throws InvalidDateException, CannotDetectServerException, IOException, ParseException {
        Scanner scanner = new Scanner(System.in);
        CommonsCLI commonsCLI = new CommonsCLIImpl();
        CommandLine cmd = commonsCLI.getCommonsCLI(args);
        LogicChecker logicChecker = new LogicCheckerImpl();
        PRTGGenerator prtgGenerator = new PRTGGeneratorImpl();
        switch (cmd.getOptionValue("f")) {
            case "graphic":
                logicChecker.configFileExistsChecker();
                System.out.println("Start to download graphic since \""+cmd.getOptionValue("s")+"\" to \"" + cmd.getOptionValue("e")+ "\" ? [y/N]");

                String tmp = scanner.nextLine();
                if(tmp.equalsIgnoreCase("y") || tmp.equalsIgnoreCase("yes")){
                    prtgGenerator.graphDownload(cmd.getOptionValue("s"), cmd.getOptionValue("e"));
                } else {
                    System.out.println("bye bye!");
                    System.exit(0);
                }
            case "history":
                logicChecker.configFileExistsChecker();
                System.out.println("Start to download history file, xml or csv? [xml/csv] default: xml");
                if(!scanner.nextLine().equalsIgnoreCase("csv")){
                    prtgGenerator.historyDownload(cmd.getOptionValue("s"), cmd.getOptionValue("e"), "xml");
                } else{
                    prtgGenerator.historyDownload(cmd.getOptionValue("s"), cmd.getOptionValue("e"), "csv");
                }
            case "rebuild":
                System.out.println("start to re-build the \"settings.xml\" file.");
                XMLGenerator xmlGenerator = new XMLGeneratorImpl();
                xmlGenerator.settingsXMLGenerator();
                System.out.println("re-build completed, please check the file content.");
            default:
                System.out.println("command: java PrtgMain --[graph/history] --[start date] --[end date] \nNOTE: date format to \"yyyy-MM-dd-HH-mm-ss\"");
        }
    }
}
