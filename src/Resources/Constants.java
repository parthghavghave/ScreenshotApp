package Resources;

import java.awt.Color;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Constants {

	private static final String DESKTOP_PATH = System.getProperty("user.home") + File.separator + "Downloads";

	public static final String SCREENSHOTS_FOLDER = DESKTOP_PATH + File.separator + "Regression screenshots";

	public static String dateToday = new SimpleDateFormat(" dd_MM_yyyy").format(new Date());

	public static final Color black_color = Color.BLACK;

	public static final int IMAGE_WIDTH = 250;
	public static final int IMAGE_HEIGHT = 125;
	public static final int IMAGE_SPACING = 10;
	
	public static final int DASHBOARD_HEIGHT = 700;
	public static final int DASHBOARD_WIDTH = 1300;
	
	public static final int Interface_Width = 130;

}
