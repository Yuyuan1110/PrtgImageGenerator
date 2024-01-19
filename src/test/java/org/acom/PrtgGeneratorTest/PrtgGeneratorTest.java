package org.acom.PrtgGeneratorTest;

import org.acom.Exception.InvalidDateException;
import org.acom.beans.ConfigBean;
import org.acom.beans.GraphBean;
import org.acom.configReader.ConfigReader;
import org.acom.configReader.Impl.ConfigReaderImpl;
import org.acom.prtgGenerator.Impl.URLGeneratorImpl;
import org.acom.prtgGenerator.URLGenerator;
import org.junit.Test;

import java.net.URL;

public class PrtgGeneratorTest {
    @Test
    public void graphURLGeneratorTest() throws InvalidDateException {
        ConfigBean cb;
        ConfigReader rc = new ConfigReaderImpl();
        cb = rc.readConfig();
        GraphBean gb = new GraphBean();
        gb.setId(7877);
        gb.setConfigBean(cb);
        gb.setStartDate("2023-01-1-0-0-0");
        gb.setEndDate("2023-10-1-23-59-0");

        URLGenerator pg = new URLGeneratorImpl();
        URL url = pg.GraphURLGenerator(cb, gb);

        System.out.println("ID: " + gb.getId());
        System.out.println("Start Date: " + gb.getStartDate());
        System.out.println("End Date: " + gb.getEndDate());
        System.out.println("AVG: " + gb.getAvg());
        System.out.println("Width: " + gb.getWidth());
        System.out.println("Height: " + gb.getHeight());
        System.out.println("graphStyling: " + gb.getGraphStyling());
        System.out.println("username: " + gb.getUsername());
        System.out.println("password: " + gb.getPassword());
        System.out.println("url: " + url);

        System.out.println(cb.getFullServerIP());
    }
}
