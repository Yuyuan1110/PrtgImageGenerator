package org.acom.prtgGenerator;


import org.acom.beans.ConfigBean;
import org.acom.beans.GraphBean;

import java.net.MalformedURLException;
import java.net.URL;

public interface URLGenerator {

    URL GraphURLGenerator(GraphBean graphBean);
    URL XMLURLGenerator(String obj, String objId) throws MalformedURLException;// String obj parameter receiving "device", "sensor", "channel".
    URL XMLURLGenerator(String obj, String objId, String sdate, String edate) throws MalformedURLException;
}
