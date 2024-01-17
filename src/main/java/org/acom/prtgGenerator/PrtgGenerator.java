package org.acom.prtgGenerator;


import org.acom.beans.ConfigBean;
import org.acom.beans.GraphBean;

import java.net.URL;

public interface PrtgGenerator {

    URL GraphURLGenerator(ConfigBean configBean, GraphBean graphBean);
}
