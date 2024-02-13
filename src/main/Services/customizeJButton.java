package main.Services;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
//        button.setRolloverIcon(new ImageIcon("hover_icon.png")); // Example: Rollovers icon
        
        // Set button pressed effect
//        button.setPressedIcon(new ImageIcon("pressed_icon.png")); // Example: Pressed icon
    }

	public static void customizeTxtButton(JButton button) {
		// TODO Auto-generated method stub
		
		button.setBackground(Color.BLACK);
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Arial", Font.BOLD, 16));
		button.setPreferredSize(new Dimension(100, 25));
		
	}
}
