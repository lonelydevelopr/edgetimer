import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class GUI extends JFrame implements ActionListener {

	/**
	 *
	 */
	private static final long serialVersionUID = -5477560024507213726L;
	static JTextField fieldTotalTime;
	static JTextField fieldJerkTime;
	static JTextField fieldCountDown;

	static JLabel labelStopWatch;
	static JLabel labelJerkTime;
	static JLabel labelCountDown;

	static JScrollPane scrollPane;

	static JButton buttonReset;
	static JButton buttonStart;
	static JButton buttonPause;
	static JButton buttonStop;

	static Timer timerTotalTime;
	static Timer timerCountDown;

	private StopWatch totalTimer;
	private StopWatch jerkingTimer;
	private CountDown countdown;
	private DataTable dataTable;

	public GUI(StopWatch total, StopWatch jerked, CountDown cd, DataTable table) {
		this.totalTimer = total;
		this.jerkingTimer = jerked;
		this.countdown = cd;
		this.dataTable = table;
	}

	public void launch() {
		initFrame();

		Container pane = new Container();
		pane = getContentPane();

		JPanel northPanel = new JPanel();
		JPanel centerPanel = new JPanel();
		JPanel southPanel = new JPanel();

		JPanel labelPane = new JPanel(new GridLayout(0, 1));
		JPanel fieldPane = new JPanel(new GridLayout(0, 1));

		initNorth();

		labelPane.add(labelStopWatch);
		fieldPane.add(fieldTotalTime);

		labelPane.add(labelJerkTime);
		fieldPane.add(fieldJerkTime);

		labelPane.add(labelCountDown);
		fieldPane.add(fieldCountDown);

		northPanel.add(labelPane, BorderLayout.CENTER);
		northPanel.add(fieldPane, BorderLayout.LINE_END);

		createTable();

		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(dataTable.getTableHeader(), BorderLayout.PAGE_START);
		centerPanel.add(scrollPane, BorderLayout.CENTER);

		createButtons();

		southPanel.add(buttonReset);
		southPanel.add(buttonStart);
		southPanel.add(buttonPause);
		southPanel.add(buttonStop);

		pane.add(northPanel, BorderLayout.NORTH);
		pane.add(centerPanel, BorderLayout.CENTER);
		pane.add(southPanel, BorderLayout.SOUTH);		

		setVisible(true);
	}
	
	public void kill() {
		this.dispose();
	}

	private void initFrame() {
		setTitle(Config.APP_TITLE);
		setSize(Config.APP_WIDTH, Config.APP_HEIGHT);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
	}

	private void initNorth() {
		labelStopWatch = new JLabel(Config.LABEL_TOTAL_TIME);
		labelJerkTime = new JLabel(Config.LABEL_JERK_TIME);
		labelCountDown = new JLabel(Config.LABEL_COUNTDOWN);

		fieldTotalTime = new JTextField("00:00:00");
		fieldJerkTime = new JTextField("00:00:00");
		fieldCountDown = new JTextField("00");

		labelStopWatch.setLabelFor(fieldTotalTime);
		labelJerkTime.setLabelFor(fieldJerkTime);
		labelCountDown.setLabelFor(fieldCountDown);

		fieldTotalTime.setEditable(false);
		fieldJerkTime.setEditable(false);
		fieldCountDown.setEditable(false);

		fieldTotalTime.setFont(new Font(Config.APP_FONT, Font.BOLD, 20));
		labelStopWatch.setFont(new Font(Config.APP_FONT, Font.BOLD, 20));

		labelJerkTime.setFont(new Font(Config.APP_FONT, Font.BOLD, 20));
		fieldJerkTime.setFont(new Font(Config.APP_FONT, Font.BOLD, 20));

		labelCountDown.setFont(new Font(Config.APP_FONT, Font.BOLD, 20));
		fieldCountDown.setFont(new Font(Config.APP_FONT, Font.BOLD, 20));

		labelCountDown.setVisible(false);
		fieldCountDown.setVisible(false);
	}

	private void createTable() {
		dataTable.setFillsViewportHeight(true);
		dataTable.setFont(new Font(Config.APP_FONT, Font.PLAIN, 13));
		dataTable.setColumnAlignmentCenter();

		scrollPane = new JScrollPane(dataTable);
		scrollPane.setVisible(true);

		// set column width
		dataTable.getColumnModel().getColumn(0).setPreferredWidth(25);
		// dataTable.getColumnModel().getColumn(1).setPreferredWidth(85);
		// dataTable.getColumnModel().getColumn(2).setPreferredWidth(45);
		dataTable.getColumnModel().getColumn(3).setPreferredWidth(60);
		// dataTable.getColumnModel().getColumn(4).setPreferredWidth(40);
		dataTable.getColumnModel().getColumn(5).setPreferredWidth(50);
		dataTable.getColumnModel().getColumn(6).setPreferredWidth(50);
	}

	private void createButtons() {
		buttonReset = new JButton(Config.BUTTON_LABEL_RESET);
		buttonStart = new JButton(Config.BUTTON_LABEL_START);
		buttonPause = new JButton(Config.BUTTON_LABEL_PAUSE);
		buttonStop = new JButton(Config.BUTTON_LABEL_STOP);

		buttonReset.addActionListener(this);
		buttonStart.addActionListener(this);
		buttonPause.addActionListener(this);
		buttonStop.addActionListener(this);

		buttonPause.setEnabled(false);
		buttonStop.setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case Config.BUTTON_LABEL_RESET:
			FileWorker.reset(this);
			break;
		case Config.BUTTON_LABEL_START:
			start(totalTimer, jerkingTimer);
			break;
		case Config.BUTTON_LABEL_PAUSE:
			pause(totalTimer, jerkingTimer, countdown);
			break;
		case Config.BUTTON_LABEL_STOP:
			stop(totalTimer, jerkingTimer, countdown);
			break;
		default:
			break;
		}
	}

	private void start(StopWatch total, StopWatch jerking) {
	
		dataTable.addTableRow(StopWatch.ACTION_START, totalTimer, "");

		setTimer(new Timer(), total, fieldTotalTime, StopWatch.TYPE_TOTAL);
		setTimer(new Timer(), jerking, fieldJerkTime, StopWatch.TYPE_JERK);		
		
		buttonStart.setEnabled(false);
		buttonPause.setEnabled(true);
		buttonStop.setEnabled(true);
	}

	private void pause(StopWatch totalTimer, StopWatch jerkingTimer, CountDown countdown) {
		labelCountDown.setVisible(true);
		fieldCountDown.setVisible(true);
		
		dataTable.addTableRow(StopWatch.ACTION_PAUSE, totalTimer, "");

		buttonStop.setEnabled(true);
		buttonPause.setEnabled(false);

		timerCountDown = new Timer();

		jerkingTimer.pause();

		final TimerTask task = new TimerTask() {
			public void run() {
				countdown.go();

				if (countdown.getValue() == 0) {
					Toolkit.getDefaultToolkit().beep();
					jerkingTimer.start();

					labelCountDown.setVisible(false);
					fieldCountDown.setVisible(false);

					buttonPause.setEnabled(true);

					countdown.setValue(CountDown.COUNT_DOWN_SIZE);

					timerCountDown.cancel();
				}
			}
		};
		timerCountDown.scheduleAtFixedRate(task, 1000, 1000);
	}

	private void stop(StopWatch totalTimer, StopWatch jerkingTimer, CountDown countdown) {
		
		dataTable.addTableRow(StopWatch.ACTION_STOP, totalTimer, orgasm());

		if (timerCountDown != null) {
			timerCountDown.cancel();
		}

		labelCountDown.setVisible(false);
		fieldCountDown.setVisible(false);

		buttonStart.setEnabled(true);
		buttonStop.setEnabled(false);
		buttonPause.setEnabled(false);

		countdown.setValue(CountDown.COUNT_DOWN_SIZE);

		totalTimer.stop();
		totalTimer.reset();
		fieldTotalTime.setText(totalTimer.getTimeString());

		jerkingTimer.stop();
		jerkingTimer.reset();
		fieldJerkTime.setText(totalTimer.getTimeString());
	}

	private void setTimer(Timer timer, StopWatch watch, JTextField field, String timerType) {
		watch.start();
		final TimerTask task = new TimerTask() {
			public void run() {
				if (watch.getStatus() == StopWatch.STATUS_OFF) {
					timer.cancel();
				} else {
					watch.run();
					field.setText(watch.getTimeString());
				}
			}
		};
		timer.scheduleAtFixedRate(task, 1000, 1000);
	}

	public String orgasm() {

		Object[] options = { "Just stopped edging.", "Ruined Orgasm!", "Orgasm :(" };
		int answer = JOptionPane.showOptionDialog(this, "Please choose one of the following options.", "Question",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);

		String orgasmType = "";
		switch (answer) {
		case 0:
			orgasmType = Config.ORGASM_TYPE_NONE;
			break;
		case 1:
			orgasmType = Config.ORGASM_TYPE_RUINED;
			break;
		case 2:
			orgasmType = Config.ORGASM_TYPE_ORGASM;
			break;
		}
		return orgasmType;
	}
}
