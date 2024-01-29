package org.acom.tools;

import org.apache.batik.transcoder.*;
import org.apache.batik.transcoder.image.PNGTranscoder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CommonTools {
    public static String rename(String str){
        String stringWithoutSpecialChars = str.replaceAll("[<>:\\\"/\\\\\\\\|?*]", "");
        return stringWithoutSpecialChars.replaceAll("\\s{2,}", " ").replaceAll("\\s", "_");
    }

    private final Transcoder transcode = new PNGTranscoder();
    public void svgToPng(InputStream inputStream, OutputStream OutputStream){
        try {

//            transcode.addTranscodingHint();
            transcode.addTranscodingHint(PNGTranscoder.KEY_WIDTH, 975F);
            transcode.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, 300F);
            TranscoderInput transcoderInput = new TranscoderInput(inputStream);
            transcode.transcode(transcoderInput, new TranscoderOutput(OutputStream));

            System.out.println("SVG to PNG conversion successful.");
        } catch (TranscoderException e) {
            e.printStackTrace();
        }
    }
}
