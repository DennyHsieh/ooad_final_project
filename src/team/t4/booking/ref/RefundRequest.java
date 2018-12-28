package team.t4.booking.ref;

/**
 * Specifying properties of the refund requests.
 */
public abstract class RefundRequest {
	
	protected String UID;
	protected String reservationNo;
	protected String[][] searchResult;
	/**
	 * Print out the transaction information.
	 */
	public abstract int print();
	/**
	 * Implement operation to modify the inner data of the database.
	 * @param select The selected item number of the print out information.
	 */
	public abstract void execute(int select);
	public abstract void setAmount(int amount);
	public abstract int[] setType(String type, int select);

}
