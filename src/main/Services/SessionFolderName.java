package main.Services;

import javax.swing.JOptionPane;

import Resources.Constants;

public class SessionFolderName {

	public static String getSubFolderName() {
		String subfolderName = JOptionPane.showInputDialog("Enter the folder name for the session")+ Constants.dateToday;
		return subfolderName;
	}
}
