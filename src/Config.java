import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Config {
	static final String APP_TITLE = "EdgeTimer v1.0";
	
	static final String APP_FONT = "Arial";

	static final int APP_WIDTH = 410;
	static final int APP_HEIGHT = 500;	
	
	public final static String[] TABLE_COLUMN_NAMES = { "ID", "Date", "Round", "Action", "Total", "Jerked", "Orgasm" };
	
	public static final String BUTTON_LABEL_RESET = "Reset";
	public static final String BUTTON_LABEL_START = "Go!";
	public static final String BUTTON_LABEL_PAUSE = "Pause...";
	public static final String BUTTON_LABEL_STOP = "Stop.";
	
	public static final String LABEL_TOTAL_TIME = "Session Time:";
	public static final String LABEL_JERK_TIME = "Jerking Time:";
	public static final String LABEL_COUNTDOWN = "Pause:";

	public final static String ORGASM_TYPE_NONE = "none";
	public final static String ORGASM_TYPE_RUINED = "ruiend";
	public final static String ORGASM_TYPE_ORGASM = "orgasm";

	public final static DateFormat minuteSecondFormatter = new SimpleDateFormat("mm:ss");
	public final static SimpleDateFormat dayMonthYearFormatter = new SimpleDateFormat("dd.MM.yyyy");
	public final static SimpleDateFormat hourMinuteSecondParser = new SimpleDateFormat("HH:mm:ss");
}
