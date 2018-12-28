package team.t4.booking.db;
/**
 * This is the exception for specifying invalid 
 * condition with searching service.
 */
public class SearchException extends Exception {
	//Exception type
	private TypeOfSearch type;
	
	/**
	 * This enumeration determing the type
	 * of SearchException and with specified message.
	 */
	public enum TypeOfSearch {
		UIDwrong("您輸入的身份識別號碼有誤，請重新輸入"),
		ResvNowrong("您輸入的訂位代號有誤，請重新輸入"),
		NotFound("查無紀錄");
		private String message;
		TypeOfSearch(String message) {
			this.message = message;
		}
		public String getMessage() {
			return this.message;
		}
	}
	/**
	 * Create SearchException with types in TypeOfSearch.
	 * @param type The type of the SearchException.
	 */
	SearchException (TypeOfSearch type) {
		super(type.getMessage());
		this.type = type;
	}
}
