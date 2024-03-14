package main.Services;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class customizeJButton {

	public static void customizeButton(JButton button) {
        button.setForeground(null);
        button.setMargin(new Insets(0, 10, 0, 5));
        button.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

	public static void customizeTxtButton(JButton button,int buttonPanelHeight) {				
		button.setBackground(Color.BLACK);
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Arial", Font.BOLD, (int)(buttonPanelHeight*0.08)));
		button.setPreferredSize(new Dimension(100, (int)(buttonPanelHeight*0.12)));
		
	}
}
