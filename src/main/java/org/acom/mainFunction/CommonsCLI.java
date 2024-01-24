package org.acom.mainFunction;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

public interface CommonsCLI {
    public CommandLine getCommonsCLI(String[] args) throws ParseException;
}
