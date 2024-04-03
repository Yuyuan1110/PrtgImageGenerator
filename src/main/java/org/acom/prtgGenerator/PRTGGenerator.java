package org.acom.prtgGenerator;

import org.acom.beans.GraphBean;
import org.acom.beans.HistoryBean;

public interface PRTGGenerator {
    public void graphDownload(GraphBean graphBean);
    void graphDownload(GraphBean graphBean, String settingsFile);

    void historyDownload(HistoryBean historyBean, String type);

    public void graphSingleDownload(GraphBean graphBean);
}
