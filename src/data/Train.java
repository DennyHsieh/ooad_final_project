package data;

/**
 * This class is used to record some details of the train tickets.
 * @author Jerry
 */
public class Train {
	public int train_id;
	public int date;
	public int t1, t2;
	public double early, student;
	
	/**
	 * @param train_id Train ID.
	 * @param date Departure date.
	 * @param t1 Departure time.
	 * @param t2 Arrival Time.
	 * @param early Early discount.
	 * @param student Student discount.
	 */
	public Train(int train_id, int date, int t1, int t2, double early, double student) {
		this.train_id = train_id;
		this.date = date;
		this.t1 = t1;
		this.t2 = t2;
		this.early = early;
		this.student = student;
	}
}
