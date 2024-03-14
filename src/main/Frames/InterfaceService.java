package main.Frames;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Resources.Constants;
import main.Services.ScreenShotService;
import main.Services.SessionFolderName;
import main.Services.customizeJButton;

public class InterfaceService extends JFrame {

	private static final long serialVersionUID = 1L;
	private static String subfolderName;
	private int initialX;
	private int initialY;
	private boolean isDragging = false;
	private boolean isSmallSize = true;
	private List<BufferedImage> screenshotBuffer = new ArrayList<>();
	private DashboardWindow dashboard;

	public InterfaceService() {
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

		ImageIcon createBufferOfSS = new ImageIcon(getClass().getResource("/Resources/icons/ImageBuffer.png"));
		createBufferOfSS = new ImageIcon(createBufferOfSS.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));

		ImageIcon storeBufferImg = new ImageIcon(getClass().getResource("/Resources/icons/storagefolder.png"));
		storeBufferImg = new ImageIcon(storeBufferImg.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));

		ImageIcon closeIcon = new ImageIcon(getClass().getResource("/Resources/icons/close.png"));
		closeIcon = new ImageIcon(closeIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));

		ImageIcon toggleicon = new ImageIcon(getClass().getResource("/Resources/icons/adv.png"));
		toggleicon = new ImageIcon(toggleicon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		
		ImageIcon dashboard = new ImageIcon(getClass().getResource("/Resources/icons/dashboard.png"));
		dashboard = new ImageIcon(dashboard.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));

		JButton captureButton = new JButton(captureIcon);
		JButton closeButton = new JButton(closeIcon);
		JButton createBufferOfSSButton = new JButton(createBufferOfSS);
		JButton storeBufferImgButton = new JButton(storeBufferImg);
		JButton toggleBufferButtons = new JButton(toggleicon);
        JButton openDashboardButton = new JButton(dashboard);
        
        customizeJButton.customizeButton(captureButton);
        customizeJButton.customizeButton(closeButton);
        customizeJButton.customizeButton(createBufferOfSSButton);
        customizeJButton.customizeButton(storeBufferImgButton);
        customizeJButton.customizeButton(toggleBufferButtons);
        customizeJButton.customizeButton(openDashboardButton);

		// Add mouse listener for dragging
		captureButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				BufferedImage screenshot = ScreenShotService.captureScreenshot();
				ScreenShotService.storeScreenShot(sessionFolder, screenshot);
				setVisible(true);
			}
		});
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!screenshotBuffer.isEmpty()) {
					String innerSubFolder = JOptionPane.showInputDialog("Unsaved screenshots will be stored in");
					ScreenShotService.storrBufferShot(screenshotBuffer, sessionFolder, innerSubFolder);
				}
				System.exit(0);
			}
		});
		toggleBufferButtons.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toggleSize();
				boolean isVisible = createBufferOfSSButton.isVisible();
				createBufferOfSSButton.setVisible(isVisible);
				storeBufferImgButton.setVisible(isVisible);
			}
		});
		createBufferOfSSButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				screenshotBuffer.add(ScreenShotService.captureScreenshot());
				setVisible(true);
			}
		});
		storeBufferImgButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!screenshotBuffer.isEmpty()) {					
					String innerSubFolder = JOptionPane.showInputDialog("All captured will be stored in ");
					ScreenShotService.storrBufferShot(screenshotBuffer, sessionFolder, innerSubFolder);
				}
			}
		});
		
		 // Add a button to open the dashboard window
        openDashboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openDashboard();
            }
        });

		add(captureButton);
		add(closeButton);
		add(toggleBufferButtons);
		add(createBufferOfSSButton);
		add(storeBufferImgButton);
		add(openDashboardButton);

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

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int) screenSize.getWidth();
		setLocation(screenWidth - getWidth() - 170, 120);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(Constants.Interface_Width, 40);
		setVisible(true);
	}
	
	private void openDashboard() {
	    if (dashboard == null || !dashboard.isVisible()) {
	        dashboard = new DashboardWindow();
	    } else {
	        dashboard.toFront();
	    }
	}

	private void toggleSize() {
		if (isSmallSize) {
			setSize(Constants.Interface_Width, 75);
			setAlwaysOnTop(true);
			isSmallSize = false;
		} else {
			setSize(Constants.Interface_Width, 40);
			isSmallSize = true;
			setAlwaysOnTop(true);
		}
	}
}
