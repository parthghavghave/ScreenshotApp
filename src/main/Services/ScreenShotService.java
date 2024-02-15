package main.Services;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.datatransfer.Clipboard;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import javax.imageio.ImageIO;

public class ScreenShotService {
	
	public static BufferedImage captureScreenshot() {
		try {
			Robot robot = new Robot();
			Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage screenshot = robot.createScreenCapture(screenRect);
			return screenshot;

		} catch (AWTException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void storeScreenShot(File folderPath, BufferedImage screenshot) {
		String fileName = folderPath + File.separator + "screenshot_" + UUID.randomUUID().toString() + ".png";
		File outputfile = new File(fileName);
		try {
			ImageIO.write(screenshot, "png", outputfile);
			Image outputSS = ImageIO.read(outputfile);;
			copyImageToClipboard(outputSS);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void storrBufferShot(List<BufferedImage> screenshotBuffer, File subfolderName,
			String InnerSubFolder) {
		File innerSubfolderFile = new File(subfolderName + File.separator + InnerSubFolder);
		if (!innerSubfolderFile.exists()) {
			innerSubfolderFile.mkdir();
		}
		for (BufferedImage screenshot : screenshotBuffer) {
			ScreenShotService.storeScreenShot(innerSubfolderFile, screenshot);
		}
		screenshotBuffer.clear();
	}
	
	public static void copyImageToClipboard(Image imageFile) {
        try {
//            Image image = Toolkit.getDefaultToolkit().getImage(imageFile);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            TransferableImage transferable = new TransferableImage(imageFile);
            clipboard.setContents(transferable, null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
	
	public static void openImageFile(File imageFile) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.OPEN)) {
                try {
                    desktop.open(imageFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
