package org.acom;


import org.acom.mainFunction.CommonsCLI;
import org.acom.mainFunction.Impl.CommonsCLIImpl;
import org.acom.mainFunction.Impl.LogicCheckerImpl;
import org.acom.mainFunction.LogicChecker;
import org.acom.prtgGenerator.PRTGGenerator;
import org.acom.prtgGenerator.Impl.PRTGGeneratorImpl;
import org.acom.prtgGenerator.Impl.XMLGeneratorImpl;
import org.acom.prtgGenerator.XMLGenerator;
import org.apache.commons.cli.*;

import java.util.Scanner;

public class PrtgMain {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        CommonsCLI commonsCLI = new CommonsCLIImpl();
        CommandLine cmd = commonsCLI.getCommonsCLI(args);
        LogicChecker logicChecker = new LogicCheckerImpl();
        PRTGGenerator prtgGenerator = new PRTGGeneratorImpl();
        switch (cmd.getOptionValue("f")) {
            case "graphic":
                logicChecker.configFileExistsChecker();
                logicChecker.featureGraphic(cmd);
                break;
            case "history":
                logicChecker.configFileExistsChecker();
                logicChecker.featureHistory(cmd);
//                System.out.println("Start to download history file, xml or csv? [xml/csv] default: xml");
//                if(!scanner.nextLine().equalsIgnoreCase("csv")){
//                    prtgGenerator.historyDownload(cmd.getOptionValue("s"), cmd.getOptionValue("e"), "xml");
//                } else{
//                    prtgGenerator.historyDownload(cmd.getOptionValue("s"), cmd.getOptionValue("e"), "csv");
//                }
                break;
            case "rebuild":
                logicChecker.featureRebuild(cmd);
                break;
            case "check" :
                logicChecker.configFileExistsChecker();
            default:
                System.out.println("command: java -jar PRTG_Generator.jar --[graph/history] --[start date] --[end date] \nNOTE: date format to \"yyyy-MM-dd-HH-mm-ss\"");
                System.exit(0);
                break;

        }
    }
}
