package main.Services;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Resources.Constants;

public class SessionFolderName {

	static Boolean choosenFolderSelected = false;

	public static String getSubFolderName() {
//		String subfolderName = JOptionPane.showInputDialog("Enter the folder name for the session")+ Constants.dateToday;
//		return subfolderName;

		JPanel panel = new JPanel();
		JTextField folderField = new JTextField(20);
		JButton chooseFolderButton = new JButton("Choose..");

		// Add components to the panel
		panel.add(new JLabel("Enter the folder name for the session:"));
		panel.add(folderField);
		panel.add(chooseFolderButton);

		// Create a file chooser with the default directory set
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setCurrentDirectory(new File(Constants.SCREENSHOTS_FOLDER));

		// Add action listener to the choose folder button
		chooseFolderButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = fileChooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					folderField.setText(selectedFile.getName());
					choosenFolderSelected = true;
				}
			}
		});

		// Show the option dialog
		int option = JOptionPane.showConfirmDialog(null, panel, "Custom Input Dialog", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			String folderName = folderField.getText();
			if (folderName.isEmpty() || folderName.trim() == "") {
				JOptionPane.showMessageDialog(null, "Please enter a folder name", "Error", JOptionPane.ERROR_MESSAGE);
				return getSubFolderName();
			} else if (choosenFolderSelected) {
				return folderName;
			}
			return folderName + Constants.dateToday;
		} else {
			return null;
		}
	}
}
