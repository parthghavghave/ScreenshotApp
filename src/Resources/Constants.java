package Resources;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Constants {

	private static final String DESKTOP_PATH = System.getProperty("user.home") + File.separator + "Downloads";

	public static final String SCREENSHOTS_FOLDER = DESKTOP_PATH + File.separator + "Regression screenshots";

	public static String dateToday = new SimpleDateFormat(" dd_MM_yyyy").format(new Date());

}
