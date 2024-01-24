package org.acom.prtgGenerator.Impl;

import org.acom.Exception.InvalidDateException;
import org.acom.beans.ConfigBean;
import org.acom.beans.GraphBean;
import org.acom.configReader.Impl.ConfigReaderImpl;
import org.acom.prtgGenerator.URLGenerator;

import java.net.MalformedURLException;
import java.net.URL;

public class URLGeneratorImpl implements URLGenerator {

    static final ConfigBean configBean = new ConfigReaderImpl().readConfig();

    @Override
    public URL GraphURLGenerator(GraphBean graphBean) {
        try {
            URL url = new URL(configBean.getProtocol() + "://" + configBean.getServerIP() + ":" + configBean.getPort() + "/chart.png?graphid=-1" + "&graphstyling=" + graphBean.getGraphStyling() + "&id=" + graphBean.getId() + "&avg=" + graphBean.getAvg()
                    + "&sdate=" + graphBean.getStartDate() + "&edate=" + graphBean.getEndDate() + "&hide=-4" + "&width=" + graphBean.getWidth() + "&height=" + graphBean.getHeight()
                    + "&username=" + configBean.getUsername() + "&password=" + configBean.getPassword());
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
                return new URL(configBean.getFullServerIP() + "api/table.xml?content=devices&columns=objid,name&username=" + configBean.getUsername() + "&password=" + configBean.getPassword());
            case "sensor":

                return new URL(configBean.getFullServerIP() + "api/table.xml?content=sensor&columns=objid,name&username=" + configBean.getUsername() + "&password=" + configBean.getPassword() + "&id=" + objId);
            case "channel":
                return new URL(configBean.getFullServerIP() + "api/table.xml?noraw=1&content=channels&columns=objid,name&username=" + configBean.getUsername() + "&password=" + configBean.getPassword() + "&id=" + objId);
            default:
                return null;
        }
    }


    public URL XMLURLGenerator(String obj, String objId, String sdate, String edate) throws MalformedURLException {
        switch (obj) {
            case "history":
                return new URL(configBean.getFullServerIP() + "/api/historicdata.xml?username=" + configBean.getUsername() + "&password=" + configBean.getPassword() + "&id=" + objId+"&sdate="+sdate+"&edate="+edate);
            case "cvs":
                return new URL(configBean.getFullServerIP() + "/api/historicdata.cvs?username=" + configBean.getUsername() + "&password=" + configBean.getPassword() + "&id=" + objId+"&sdate="+sdate+"&edate="+edate);
            default:
                return null;
        }
    }
}
