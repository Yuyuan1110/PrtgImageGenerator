package org.acom.configReader;

import org.acom.beans.ConfigBean;

import java.nio.file.Path;

public interface ConfigReader {
    ConfigBean readXML(Path path);
}
