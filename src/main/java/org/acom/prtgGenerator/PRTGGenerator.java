package org.acom.prtgGenerator;

public interface PRTGGenerator {
    public void graphDownload(String sdate, String edate);

    void historyDownload(String sdate, String edate, String type);

    public void graphSingleDownload(String sdate, String edate, String id);
}
