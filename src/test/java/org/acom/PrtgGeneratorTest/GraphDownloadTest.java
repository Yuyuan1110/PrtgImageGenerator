package org.acom.PrtgGeneratorTest;

import org.acom.Exception.InvalidDateException;
import org.acom.prtgGenerator.PRTGGenerator;
import org.acom.prtgGenerator.Impl.PRTGGeneratorImpl;
import org.junit.Test;

public class GraphDownloadTest {

    @Test
    public void downloadGraphTest() throws InvalidDateException {
        PRTGGenerator graphGenerator = new PRTGGeneratorImpl();
        graphGenerator.graphDownload("2023-08-31-00-00-00", "2024-01-01-00-00-00");
    }
}
