import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class DataTable extends JTable {

	private static final long serialVersionUID = 1L;

	private final DefaultTableModel model;

	private final ArrayList<TableEntry> dataTable;

	private String[] columnNames;

	static String lastTimestamp = "00:00:00";

	public static String orgasmType = "";

	private int round;
	private int sessionID = 0;

	public DataTable() {
		super();

		model = new DefaultTableModel(Config.TABLE_COLUMN_NAMES, 0);
		setModel(model);

		dataTable = new ArrayList<TableEntry>();
	}

	public String[] getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(final String[] columnNames) {
		this.columnNames = columnNames;
	}

	public void setColumnAlignmentCenter() {
		// center all columns
		final DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < columnNames.length; i++) {
			getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
	}

	public void addTableRow(final String action, final StopWatch watch, String orgasmType) {
		
		String row = null;
		
		try {
			final String currWatchTimestamp = watch.getTimeString();

			long difference = 0;

			Date currWatchTimestampD = null;
			Date lastWatchTimestampD;

			currWatchTimestampD = Config.hourMinuteSecondParser.parse(currWatchTimestamp);
			lastWatchTimestampD = Config.hourMinuteSecondParser.parse(lastTimestamp);
			
			round++;

			if (action == StopWatch.ACTION_START) {
				sessionID++;
				round = 0;
				difference = currWatchTimestampD.getTime() - lastWatchTimestampD.getTime();
				orgasmType = "";
			} else if (action == StopWatch.ACTION_PAUSE && round == 2) {
				difference = currWatchTimestampD.getTime() - lastWatchTimestampD.getTime();
			} else if (action == StopWatch.ACTION_STOP && round == 2) {
				difference = currWatchTimestampD.getTime() - lastWatchTimestampD.getTime();
			} else {
				if (action == StopWatch.ACTION_PAUSE) {
					orgasmType = "";
				}

				// add countdown time to lastTimeStamp to get the actual jerked time
				difference = currWatchTimestampD.getTime()
						- (lastWatchTimestampD.getTime() + CountDown.COUNT_DOWN_SIZE * 1000);

				if (currWatchTimestampD
						.getTime() < (lastWatchTimestampD.getTime() + CountDown.COUNT_DOWN_SIZE * 1000)) {
					difference = 0;
				}
			}
			
			final Date todayD = new Date();
			final String today = Config.dayMonthYearFormatter.format(todayD);

			dataTable
					.add(new TableEntry(sessionID, todayD, round, action, currWatchTimestampD, difference, orgasmType));

			// reformat difference (long) to readable string
			final Date differenceDate = new Date(difference);
			final String diff = Config.minuteSecondFormatter.format(differenceDate);

			// add row to table
			model.addRow(new Object[] { sessionID, today, round, action, currWatchTimestamp, diff, orgasmType });

			// reset lastTimestamp on stop
			// else: save current watch timestamp for later use as lastTimestamp
			if (action == StopWatch.ACTION_STOP) {
				lastTimestamp = "00:00:00";
			} else {

				lastTimestamp = currWatchTimestamp;
			}

			changeSelection(getRowCount() - 1, 0, false, false);
			
			row = sessionID + "," + today + "," + round + "," + action + "," + currWatchTimestamp + "," + diff + "," + orgasmType;

		} catch (final ParseException e) {
			e.printStackTrace();
		}
		
		FileWorker.writeToFile(row);
	}
	
	public void initTable(final String sessionID, final String today, final String round, final String action, final String currWatchTimestamp, final String diff, final String orgasmType) {
		model.addRow(new Object[] { sessionID, today, round, action, currWatchTimestamp, diff, orgasmType });		
	}

}
