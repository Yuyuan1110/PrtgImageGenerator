package org.acom.configReader;

import org.acom.Exception.InvalidDateException;
import org.acom.beans.ConfigBean;

import java.nio.file.Path;

public interface ConfigReader {
    ConfigBean readConfig();
    String[] readXML(Path path);


}
