package org.acom.prtgGenerator.Impl;

import org.acom.Exception.InvalidDateException;
import org.acom.beans.ConfigBean;
import org.acom.beans.GraphBean;
import org.acom.beans.HistoryBean;
import org.acom.configReader.Impl.ConfigReaderImpl;
import org.acom.prtgGenerator.URLGenerator;

import java.net.MalformedURLException;
import java.net.URL;

public class URLGeneratorImpl implements URLGenerator {

    static final ConfigBean configBean = new ConfigReaderImpl().readConfig();


    @Override
    public URL GraphURLGenerator(GraphBean graphBean) {
        try {
            String hides = String.join(",", graphBean.getHide());

            URL url = new URL(configBean.getFullServerIP() + "chart.png?graphid=-1" + "&id=" + graphBean.getId() + "&sdate=" + graphBean.getStartDate() + "&edate=" + graphBean.getEndDate() + "&avg=" + graphBean.getAvg() + "&graphstyling=" + graphBean.getGraphStyling()
                    + "&hide=" + hides + "&width=" + graphBean.getWidth() + "&height=" + graphBean.getHeight()
                    + "&username=" + configBean.getUsername() + "&password=" + configBean.getPassword() + "&bgcolor=%23FCFCFC");
//            graphid=-1&graphstyling=baseFontSize%3D%2710%27%20showLegend%3D%271%27&id=7877&avg=86400&sdate=2023-01-01-17-38-00&edate=2023-10-01-23-59-00&hide=-4
//            &width=850&height=270&username=acom&password=Aa1234567890


            return url;
        } catch (MalformedURLException | InvalidDateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public URL XMLURLGenerator(String obj, String objId) throws MalformedURLException {
        switch (obj) {
            case "device":
                return new URL(configBean.getFullServerIP() + "api/table.xml?content=devices&columns=objid,name&username=" + configBean.getUsername() + "&password=" + configBean.getPassword()+ "&id=" + objId);
            case "sensor":
                return new URL(configBean.getFullServerIP() + "api/table.xml?content=sensor&columns=objid,name&username=" + configBean.getUsername() + "&password=" + configBean.getPassword() + "&id=" + objId);
            case "channel":
                return new URL(configBean.getFullServerIP() + "api/table.xml?noraw=1&content=channels&columns=objid,name&username=" + configBean.getUsername() + "&password=" + configBean.getPassword() + "&id=" + objId);
            case "json":
                return new URL(configBean.getFullServerIP() + "api/table.json?noraw=1&content=channels&columns=objid,name&username=" + configBean.getUsername() + "&password=" + configBean.getPassword() + "&id=" + objId);
           default:
                return null;
        }
    }

    @Override
    public URL XMLURLGenerator(String obj, HistoryBean historyBean) throws MalformedURLException, InvalidDateException {

        switch (obj) {
            case "history":
                return new URL(configBean.getFullServerIP() + "api/historicdata.xml?username=" + configBean.getUsername() + "&password=" + configBean.getPassword() + "&id=" + historyBean.getId() + "&sdate=" + historyBean.getStartDate() + "&edate=" + historyBean.getEndDate());
            case "cvs":
                return new URL(configBean.getFullServerIP() + "api/historicdata.cvs?username=" + configBean.getUsername() + "&password=" + configBean.getPassword() + "&id=" + historyBean.getId() + "&sdate=" + historyBean.getStartDate() + "&edate=" + historyBean.getEndDate());
            default:
                return null;
        }
    }

}

