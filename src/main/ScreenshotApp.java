package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;



import javax.imageio.ImageIO;

public class ScreenshotApp extends JFrame{
	
	/**
	 *  Author: Parth Ghavghave 
	 */
	private static final long serialVersionUID = 1L;

	private static final String DESKTOP_PATH = System.getProperty("user.home") + File.separator + "Downloads";
	
	private static final String SCREENSHOTS_FOLDER = DESKTOP_PATH + File.separator + "Regression screenshots";
		
	private static String subfolderName;
	
	private int initialX;
    private int initialY;
    private boolean isDragging = false;
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new ScreenshotApp());
	}	

	public ScreenshotApp() {
        setTitle("Screenshot App");
        setLayout(new FlowLayout());
        setAlwaysOnTop(true);
        setResizable(false);  
        setUndecorated(true);
        
        File screenshotsFolder = new File(SCREENSHOTS_FOLDER);
        if (!screenshotsFolder.exists()) {
            screenshotsFolder.mkdir();
        }
        
        subfolderName = getSubFolderName();
        	
        if(subfolderName == null || subfolderName.trim().isEmpty()) {
        	System.exit(0);
        }
        
        
        File sessionFolder = new File(SCREENSHOTS_FOLDER + File.separator + subfolderName);
        if (!sessionFolder.exists()) {
            sessionFolder.mkdir();
        }
        
        ImageIcon captureIcon = new ImageIcon(getClass().getResource("/icons/capture.png"));
        captureIcon = new ImageIcon(captureIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));

        ImageIcon closeIcon = new ImageIcon(getClass().getResource("/icons/close.png"));
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
                captureScreenshot(sessionFolder);
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
        
//        addKeyListener(new MyKeyListener());
//        setFocusable(true);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        setLocation(screenWidth - getWidth()-200, 100);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(150, 40);
        setVisible(true);
    }

	private String getSubFolderName() {
		String subfolderName = JOptionPane.showInputDialog("Enter the folder name for the session");
		return subfolderName;
	}
	

	private void captureScreenshot(File sessionFolder) {
		try {
			Robot robot = new Robot();
	        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
	        BufferedImage screenshot = robot.createScreenCapture(screenRect);
	        
	        // Generate a unique filename for each screenshot
	        String fileName = sessionFolder +
                    File.separator + "screenshot_" + UUID.randomUUID().toString() + ".png";
	        // Save the screenshot to the specified folder
	        File outputfile = new File(fileName);
	        ImageIO.write(screenshot, "png", outputfile);
	    } catch (AWTException | IOException e) {
	        e.printStackTrace();
	    }
	}

	
}

//	private class MyKeyListener implements KeyListener {
//
//        @Override
//        public void keyPressed(KeyEvent e) {
//            // Invoked when a key is pressed
//        	if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_INSERT) {
//                captureScreenshot(new File(SCREENSHOTS_FOLDER + File.separator + subfolderName));
//            }
//        }
//
//		@Override
//		public void keyTyped(KeyEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void keyReleased(KeyEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//    }