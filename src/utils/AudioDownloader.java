package utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AudioDownloader {

    public static void downloadTrackAsAudio(String trackName, String artistName, String format, String outputFolder) {
        File pythonScript = null;
        try {
            pythonScript = PythonScriptHandler.getPythonScript("res/download_audio.py");
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> args = new ArrayList<>();
        args.add(trackName); 
        args.add(artistName); 
        
        if (format != null && !format.isEmpty()) {
            args.add("--format"); 
            args.add(format);     
        }
        
        args.add("--output");
        args.add(outputFolder);
        
        
        try {
            String result = PythonScriptHandler.runPythonScriptWithArgs(pythonScript, args);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
