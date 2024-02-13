package main.Services;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

public class DisplayImagePane {

	public static void displayImage(File imageFile, JPanel imagePanel, JTextArea textArea) {
		try {
			try {
				String existTxt = textDataService.getTxtForImg(imageFile);
				if (!existTxt.isEmpty() || existTxt != null) {
					textArea.setText(existTxt);
					existTxt = null;
				}
			} catch (Exception e) {
			}

			BufferedImage image = ImageIO.read(imageFile);

			int height = (int) (image.getHeight() - (image.getHeight() * 0.4));
			int width = (int) (image.getWidth() - (image.getWidth() * 0.4));

			JSplitPane ImageAndText = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			JSplitPane TextAndButton = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

			// Scale the image to fit within the imagePanel
			ImageIcon imageIcon = new ImageIcon(image.getScaledInstance(width, height, Image.SCALE_SMOOTH));
			JLabel imageLabel = new JLabel(imageIcon);

			ImageAndText.setTopComponent(imageLabel);
			ImageAndText.setBottomComponent(TextAndButton);

			// Create a JPanel for buttons
			JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

			// Create three buttons
			JButton saveButton = new JButton("Save");
			JButton copyButton = new JButton("Copy");
			JButton clearButton = new JButton("Clear");

			customizeJButton.customizeTxtButton(saveButton);
			customizeJButton.customizeTxtButton(copyButton);
			customizeJButton.customizeTxtButton(clearButton);

			// Add action listeners to buttons
			saveButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					textDataService.saveText(imageFile, textArea.getText());
				}
			});
			
			copyButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					ScreenShotService.copyImageToClipboard(image);
				}
			});

			clearButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					textArea.setText("");
				}
			});

			// Add buttons to button panel
			buttonPanel.add(saveButton);
			buttonPanel.add(copyButton);
			buttonPanel.add(clearButton);

			TextAndButton.setLeftComponent(textArea);
			TextAndButton.setRightComponent(buttonPanel);

			TextAndButton.setDividerLocation(850);

			// Clear previous components and display the new contentPanel
			imagePanel.removeAll();
			imagePanel.add(ImageAndText, BorderLayout.CENTER);
			imagePanel.revalidate();
			imagePanel.repaint();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}