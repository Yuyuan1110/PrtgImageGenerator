package org.acom.configReader.Impl;
import org.acom.beans.ConfigBean;
import org.acom.configReader.ConfigReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Properties;

public class ConfigReaderImpl implements ConfigReader {

    @Override
    public ConfigBean readConfig() {
        ConfigBean configBean;
        Properties properties = new Properties();
        InputStream resourceAsStream = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties");

        try {
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