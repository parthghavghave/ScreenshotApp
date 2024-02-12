package main.Services;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DisplayImagePane {
    
    public static void displayImage(File imageFile, JPanel imagePanel, JTextArea textArea) {
        try {
        	File textFile = new File(imageFile.getParentFile(), imageFile.getName() + ".txt");
            String txt = "";
			try {
				txt += DisplayImagePane.txtFileExists(textFile);
				textArea.setText(txt);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
            BufferedImage image = ImageIO.read(imageFile);

            // Scale the image to fit within the imagePanel
            ImageIcon imageIcon = new ImageIcon(image.getScaledInstance(900, 500, Image.SCALE_SMOOTH));
            JLabel imageLabel = new JLabel(imageIcon);

            // Create a JPanel to contain both the image and text area
            JPanel contentPanel = new JPanel(new BorderLayout());
            contentPanel.add(new JScrollPane(imageLabel), BorderLayout.CENTER);

            // Initialize the text area
            textArea = new JTextArea();
            contentPanel.add(new JScrollPane(textArea), BorderLayout.SOUTH);

            // Clear previous components and display the new contentPanel
            imagePanel.removeAll();
            imagePanel.add(contentPanel, BorderLayout.CENTER);
            imagePanel.revalidate();
            imagePanel.repaint();  
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    // Method to save text associated with an image
    public static void saveText(File imageFile, String text) {
        try {
            File textFile = new File(imageFile.getParentFile(), imageFile.getName() + ".txt");
            FileWriter writer = new FileWriter(textFile, true);
            writer.write(text);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}