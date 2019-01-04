package team.t4.booking.tk;

/**
 * This enum grasps all the station name to which a unique value is assigned into a group.
 */
public enum Station {
	南港(1),台北(2),板橋(3),桃園(4),新竹(5),苗栗(6),
	台中(7),彰化(8),雲林(9),嘉義(10),台南(11),左營(12);
	private int value;
	private Station(int value) {
		this.value = value;
	}
	public int getValue() {
		return this.value;
	}
	
}
