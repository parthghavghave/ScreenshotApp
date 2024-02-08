package main.Services;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;

public class ScreenShotService {

	public static void captureScreenshot(File sessionFolder) {
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
