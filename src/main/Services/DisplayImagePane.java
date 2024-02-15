package main.Services;

import java.awt.BorderLayout;
import java.awt.Dimension;
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

import Resources.Constants;

public class DisplayImagePane {

	public static void displayImage(File imageFile, JPanel imagePanel, JTextArea textArea, int dashboardWidth, int dashboardHeight) {
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

			JSplitPane ImageAndText = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			JSplitPane TextAndButton = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

			int imageLableWidth = (int)(dashboardWidth*Constants.IMAGE_PANEL_WIDTH);
			int imageLableHeight = (int)(dashboardHeight*Constants.IMAGE_PANEL_HEIGHT);
			
			ImageIcon imageIcon = new ImageIcon(image.getScaledInstance(imageLableWidth, imageLableHeight, Image.SCALE_SMOOTH));
			JLabel imageLabel = new JLabel(imageIcon);
			JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			JPanel textPanel = new JPanel();
			textPanel.add(textArea);
			
			imageLabel.setPreferredSize(new Dimension(imageLableWidth,imageLableHeight));
			
			TextAndButton.setPreferredSize(new Dimension(imageLableWidth,dashboardHeight-imageLableHeight));
			
			ImageAndText.setTopComponent(imageLabel);
			ImageAndText.setBottomComponent(TextAndButton); 

			// Create three buttons
			JButton saveButton = new JButton("Save");
			JButton copyButton = new JButton("Copy");
			JButton openButton = new JButton("Open");
			JButton clearButton = new JButton("Clear");

			customizeJButton.customizeTxtButton(saveButton);
			customizeJButton.customizeTxtButton(copyButton);
			customizeJButton.customizeTxtButton(openButton);
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
			
			openButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					ScreenShotService.openImageFile(imageFile);
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
			buttonPanel.add(openButton);
			buttonPanel.add(clearButton);

			TextAndButton.setLeftComponent(textArea);
			TextAndButton.setRightComponent(buttonPanel);
			TextAndButton.setDividerLocation(imageLableWidth-Constants.BUTTON_PANEL_WIDTH);

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