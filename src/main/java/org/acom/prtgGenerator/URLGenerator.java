package org.acom.prtgGenerator;


import org.acom.beans.ConfigBean;
import org.acom.beans.GraphBean;

import java.net.MalformedURLException;
import java.net.URL;

public interface URLGenerator {

    URL GraphURLGenerator(ConfigBean configBean, GraphBean graphBean);
    URL[] XMLURLGenerator(ConfigBean configBean, String obj) throws MalformedURLException;// String obj parameter receiving "device", "sensor", "channel".
}
