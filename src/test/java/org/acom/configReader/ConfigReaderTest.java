package org.acom.configReader;

import org.acom.beans.ConfigBean;
import org.acom.configReader.Impl.ConfigReaderImpl;
import org.junit.Test;

public class ConfigReaderTest {
    @Test
    public void readTest(){
        ConfigBean CB = new ConfigBean();
        ConfigReader rc = new ConfigReaderImpl();
        CB = rc.readConfig();
        System.out.println(CB.getServerIP());
        System.out.println(CB.getPort());
        System.out.println(CB.getProtocol());
        System.out.println(CB.getUsername());
        System.out.println(CB.getPassword());
        System.out.println(CB.isLogger());
    }
}
