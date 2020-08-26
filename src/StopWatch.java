import java.text.DecimalFormat;

public class StopWatch {

	public static final int STATUS_OFF = 0;
	public static final int STATUS_ON = 1;
	public static final int STATUS_PAUSE = 2;

	public static final String ACTION_START = "START";
	public static final String ACTION_PAUSE = "PAUSE";
	public static final String ACTION_STOP = "STOP";

	public static final String TYPE_TOTAL = "TOTAL";
	public static final String TYPE_JERK = "JERK";

	private int status = STATUS_OFF;

	private int seconds;
	private int minutes;
	private int hours;

	public DecimalFormat df = new DecimalFormat("00");

	public StopWatch() {
		seconds = 0;
		minutes = 0;
		hours = 0;
	}

	public void start() {
		status = STATUS_ON;
	}

	public void stop() {
		status = STATUS_OFF;
	}

	public void pause() {
		status = STATUS_PAUSE;
	}

	public void reset() {
		seconds = 0;
		minutes = 0;
		hours = 0;
	}

	public void run() {
		if (status != STATUS_PAUSE) {
			seconds++;
		}

		if (seconds > 59) {
			seconds = 0;
			minutes++;
		}

		if (minutes > 59) {
			minutes = 0;
			hours++;
		}
	}

	public int getStatus() {
		return status;
	}

	public String getTimeString() {
		return getHours() + ":" + getMinutes() + ":" + getSeconds();
	}

	public String getSeconds() {
		return df.format(seconds);
	}

	public String getMinutes() {
		return df.format(minutes);
	}

	public String getHours() {
		return df.format(hours);
	}

	public void setSeconds(String sec) {
		this.seconds = Integer.parseInt(sec);
	}

	public void setMinutes(String min) {
		this.minutes = Integer.parseInt(min);
	}

	public void setHours(String hrs) {
		this.hours = Integer.parseInt(hrs);
	}
}
