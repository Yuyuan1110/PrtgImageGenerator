package org.acom.prtgGenerator;


import org.acom.Exception.InvalidDateException;
import org.acom.beans.ConfigBean;
import org.acom.beans.GraphBean;
import org.acom.beans.HistoryBean;

import java.net.MalformedURLException;
import java.net.URL;

public interface URLGenerator {

    URL GraphURLGenerator(GraphBean graphBean);
    URL XMLURLGenerator(String obj, String objId) throws MalformedURLException;// String obj parameter receiving "device", "sensor", "channel".
    URL XMLURLGenerator(String obj, HistoryBean historyBean) throws MalformedURLException, InvalidDateException;
}
