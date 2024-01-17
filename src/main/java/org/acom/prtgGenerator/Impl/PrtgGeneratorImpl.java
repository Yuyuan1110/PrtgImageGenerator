package org.acom.prtgGenerator.Impl;

import org.acom.Exception.InvalidDateException;
import org.acom.beans.ConfigBean;
import org.acom.beans.GraphBean;
import org.acom.prtgGenerator.PrtgGenerator;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class PrtgGeneratorImpl implements PrtgGenerator {

    @Override
    public URL GraphURLGenerator(ConfigBean configBean, GraphBean graphBean) {
        try {
            URL url = new URL(configBean.getProtocol()+"://"+configBean.getServerIP()+":"+configBean.getPort()+"/chart.png?graphid=-1"+"&graphstyling="+graphBean.getGraphStyling()+"&id="+graphBean.getId()+"&avg="+graphBean.getAvg()
            +"&sdate="+graphBean.getStartDate()+"&edate="+graphBean.getEndDate()+"&hide=-4"+"&width="+graphBean.getWidth()+"&height="+graphBean.getHeight()
            +"&username="+ graphBean.getUsername()+"&password="+ graphBean.getPassword());
//            graphid=-1&graphstyling=baseFontSize%3D%2710%27%20showLegend%3D%271%27&id=7877&avg=86400&sdate=2023-01-01-17-38-00&edate=2023-10-01-23-59-00&hide=-4
//            &width=850&height=270&username=acom&password=Aa1234567890


            return url;
        } catch (MalformedURLException | InvalidDateException e) {
            throw new RuntimeException(e);
        }
    }
}
