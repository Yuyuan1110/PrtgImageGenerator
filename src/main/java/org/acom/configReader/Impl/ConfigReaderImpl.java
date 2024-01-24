package org.acom.configReader.Impl;

import org.acom.beans.ConfigBean;
import org.acom.configReader.ConfigReader;
import org.acom.Exception.InvalidDateException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigReaderImpl implements ConfigReader {
    static final String configPath =  "." + File.separator + "config.properties";
    @Override
    public ConfigBean readConfig() {
        ConfigBean configBean;
        Properties properties = new Properties();
        if(!new File(configPath).exists()){
            System.out.println("Can't find the config.properties file, please check if file is exists!");
            System.exit(-1);
        }
        try {
            InputStream resourceAsStream = Files.newInputStream(Paths.get(configPath));
            properties.load(resourceAsStream);
            configBean = ConfigBean.fromProperties(properties);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return configBean;
    }

    @Override
    public String[] readXML(Path path) {

        return new String[0];
    }
}