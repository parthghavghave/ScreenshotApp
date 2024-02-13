package main.Services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class textDataService {

	// Method to save text associated with an image
	public static void saveText(File imageFile, String text) {
		try {
			Properties properties = new Properties();
			String filePath = imageFile.getAbsolutePath()+"data.properties";
			
			properties.setProperty(imageFile.getName(), text);
			
			FileOutputStream fileOutputStream = new FileOutputStream(filePath);
			properties.store(fileOutputStream, "Regression data");
			fileOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getTxtForImg(File imageFile) {
		Properties properties = new Properties();
        
        //null check for filepath
        
        try {
        	String filePath = imageFile.getAbsolutePath()+"data.properties";
        	FileInputStream fileInputStream = new FileInputStream(filePath);
			properties.load(fileInputStream);
			fileInputStream.close();
		} catch (IOException e) {
		}
        String associatedTxt = properties.getProperty(imageFile.getName());
        return associatedTxt;
	}

	public static String txtFileExists(File textFile) throws IOException {
		String txt = "";
		if (textFile.exists()) {
			// Read and display the text from the text file
			FileReader reader = new FileReader(textFile);
			BufferedReader bufferedReader = new BufferedReader(reader);
			StringBuilder text = new StringBuilder();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				text.append(line).append("\n");
			}
			txt += text;
			reader.close();
		}
		return txt;
	}

}
