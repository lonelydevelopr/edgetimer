
public class CountDown {
	
	static final int COUNT_DOWN_SIZE = 60; 
	
	private int countdown;
	
	public CountDown() {
		countdown = COUNT_DOWN_SIZE;
	}
	
	public void go(){
		update();
		countdown--;
	}
	
	public void update() {
		GUI.fieldCountDown.setText(String.valueOf(countdown));
	}

	public int getValue() {
		return countdown;
	}

	public void setValue(int cd) {
		this.countdown = cd;
	}

	
}
