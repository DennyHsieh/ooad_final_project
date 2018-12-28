package team.t4.booking.ref;
/**
 * Factory base class for refund request classes.
 */
public interface RefundRequestFactory {
	/**
	 * Get the request object.
	 * @return Refund request object.
	 * @throws Exception
	 */
	public abstract RefundRequest getInstance() throws Exception;
}
