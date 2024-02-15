package Resources;

import java.awt.Color;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Constants {

	private static final String DESKTOP_PATH = System.getProperty("user.home") + File.separator + "Documents";

	public static final String SCREENSHOTS_FOLDER = DESKTOP_PATH + File.separator + "Regression screenshots";

	public static String dateToday = new SimpleDateFormat(" dd_MM_yyyy").format(new Date());

	public static final Color black_color = Color.BLACK;

	public static final int IMAGE_WIDTH = 250;
	public static final int IMAGE_HEIGHT = 125;
	public static final int IMAGE_SPACING = 10;
	
	public static final float FLOATING_WINFOW_HEIGHT = (float)0.05;
	public static final float FLOATING_WINFOW_WIDTH = (float)0.075;
	
	//numbers are percentage respect to screen width and height
	public static final float DASHBOARD_HEIGHT = (float) 0.8; //70%
	public static final float DASHBOARD_WIDTH = (float) 0.8;
	public static final float FILESYSTEM_PANEL_WIDTH = (float) 0.2;
	public static final float IMAGE_PANEL_WIDTH = (float) (1-FILESYSTEM_PANEL_WIDTH);
	public static final float IMAGE_PANEL_HEIGHT = (float) (0.75);
	public static final int BUTTON_PANEL_WIDTH = 150;
	
	public static final int Interface_Width = 130;

}
