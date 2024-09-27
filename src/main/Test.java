package main;

import java.io.IOException;

public class Test {

	public static void main(String[] args) throws IOException {
		
		String pythonExecutable = "portable-python\\Portable Python-3.10.5 x64\\App\\Python\\python.exe";
		Process process = Runtime.getRuntime().exec(new String[]{pythonExecutable, "res/test.py"});

		
	}

}
