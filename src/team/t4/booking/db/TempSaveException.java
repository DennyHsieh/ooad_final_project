package team.t4.booking.db;

/**
 * This Exception tags Exception for the process of
 * temporarily saving data.
 */
public class TempSaveException extends Exception {
	/**
	 * Create tmepSaveException with specified error message.
	 * @param msg
	 */
	public TempSaveException(String msg) {
		super(msg);
	}

}
