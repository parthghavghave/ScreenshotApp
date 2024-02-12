package main.Services;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class customizeJButton {

	public static void customizeButton(JButton button) {
		
        // Set button foreground color
        button.setForeground(null);
        
        // Set button background color
//        button.setBackground(new Color(51, 153, 255)); // Example: Light blue
        
        // Set button padding
        button.setMargin(new Insets(0, 10, 0, 5)); // Example: Insets(top, left, bottom, right)
        
        // Set button border
        button.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Example: Empty border
        
        // Set button rollover effect
//        button.setRolloverEnabled(true);
//        button.setRolloverIcon(new ImageIcon("hover_icon.png")); // Example: Rollover icon
        
        // Set button pressed effect
//        button.setPressedIcon(new ImageIcon("pressed_icon.png")); // Example: Pressed icon
    }
}
