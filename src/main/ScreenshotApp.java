package main;

import javax.swing.*;

import main.Frames.InterfaceService;

public class ScreenshotApp{
	
	/**
	 *  Author: Parth Ghavghave 
	 */	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new InterfaceService());
	}
}