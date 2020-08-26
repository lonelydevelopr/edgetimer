public class EdgeTimer {
	/*
	 * TODOS - Save to SQLite DB - use JIntellitype for system wide shortcut to
	 * pause
	 */

	

	public static void main(String[] args) {

		EdgeTimer.start();
		
	}
	
	public static void start() {

		DataTable dataTable = new DataTable();
		FileWorker.readFile(dataTable);
		dataTable.setColumnNames(Config.TABLE_COLUMN_NAMES);

		GUI ui = new GUI(new StopWatch(), new StopWatch(), new CountDown(), dataTable);
		ui.launch();
	}

}