package main.Services;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import Resources.Constants;

public class InterfaceService extends JFrame {

	private static final long serialVersionUID = 1L;
	private static String subfolderName;
	private int initialX;
	private int initialY;
	private boolean isDragging = false;

	public InterfaceService() {
		setTitle("Screenshot App");
		setLayout(new FlowLayout());
		setAlwaysOnTop(true);
		setResizable(false);
		setUndecorated(true);

		File screenshotsFolder = new File(Constants.SCREENSHOTS_FOLDER);
		if (!screenshotsFolder.exists()) {
			screenshotsFolder.mkdir();
		}

		subfolderName = SessionFolderName.getSubFolderName();

		if (subfolderName == null || subfolderName.trim().isEmpty()) {
			System.exit(0);
		}

		File sessionFolder = new File(Constants.SCREENSHOTS_FOLDER + File.separator + subfolderName);
		if (!sessionFolder.exists()) {
			sessionFolder.mkdir();
		}

		ImageIcon captureIcon = new ImageIcon(getClass().getResource("/Resources/icons/capture.png"));
		captureIcon = new ImageIcon(captureIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));

		ImageIcon closeIcon = new ImageIcon(getClass().getResource("/Resources/icons/close.png"));
		closeIcon = new ImageIcon(closeIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));

		JButton captureButton = new JButton(captureIcon);
		JButton closeButton = new JButton(closeIcon);

		// Add mouse listener for dragging
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				initialX = e.getX();
				initialY = e.getY();
				isDragging = true;
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				isDragging = false;
			}
		});

		// Add mouse motion listener for dragging
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if (isDragging) {
					int xOffset = e.getXOnScreen() - initialX;
					int yOffset = e.getYOnScreen() - initialY;
					setLocation(xOffset, yOffset);
				}
			}
		});

		captureButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				ScreenShotService.captureScreenshot(sessionFolder);
				setVisible(true);
			}
		});
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0); // Exit the application when the close button is clicked
			}
		});

		add(captureButton);
		add(closeButton);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int) screenSize.getWidth();
		setLocation(screenWidth - getWidth() - 200, 100);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(150, 40);
		setVisible(true);
	}
}
