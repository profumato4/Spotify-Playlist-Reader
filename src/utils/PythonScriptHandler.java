package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PythonScriptHandler {

    // Method to find the Python script either in the resources or on the filesystem
    public static File getPythonScript(String resourcePath) throws IOException {
        // Check if the file exists on the filesystem (IDE case)
        File file = new File(resourcePath);
        if (file.exists()) {
            return file;
        }
        
        // Otherwise, extract it from the JAR (JAR execution case)
        
        if(resourcePath.contains("main")) {
        	return extractExecutableFromJar("/main.exe");
        }else if(resourcePath.contains("download_audio")){
        	return extractExecutableFromJar("/download_audio.exe");
        }
        
        
        return null;
    }

    // Method to extract the executable file from JAR to a temporary location
    public static File extractExecutableFromJar(String resourcePath) throws IOException {
        InputStream in = PythonScriptHandler.class.getResourceAsStream(resourcePath);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + resourcePath);
        }

        // Create a temp file for the executable
        File tempFile = File.createTempFile(resourcePath.replace(".exe", ""), ".exe");
        tempFile.deleteOnExit();

        // Copy the executable content to the temp file
        try (OutputStream out = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }

        return tempFile;
    }

    // Method to run the Python script
    public static String runPythonScript(File script) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(script.getAbsolutePath());
        Process process = processBuilder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        // Read the token from the Python script output
        String token = reader.readLine();
        reader.close();

        process.waitFor();
        return token;
    }
    
    
    // Method to run the Python script with arguments
    public static String runPythonScriptWithArgs(File script, List<String> args) throws IOException, InterruptedException {
    	
        // Prepare the command: python <script> <args...>
    	
        List<String> command = new ArrayList<>();
        command.add("python");
        command.add(script.getAbsolutePath());
        command.addAll(args);

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process process = processBuilder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        // Read the script output
        
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append(System.lineSeparator());
        }
        reader.close();

        process.waitFor();
        return output.toString().trim();
    }
    
}
