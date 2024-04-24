package org.acom.mainFunction;

import org.acom.Exception.FileMissingException;
import org.acom.Exception.InvalidDateException;
import org.apache.commons.cli.CommandLine;

public interface LogicChecker {
    public void configFileExistsChecker(CommandLine cmd);
    public void featureGraphic(CommandLine cmd);
    public void featureHistory(CommandLine cmd);
    public void featureRebuild(CommandLine cmd);
}
