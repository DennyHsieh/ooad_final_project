package team.t4.booking.ref;

/**
 * This factory creates refundRequestFactory
 */
public class AddModify {
	
	private String UID;
	private String reservationNo;
	private String[][] searchResult;
	
	public	AddModify (String UID, String reservationNo) {
		this.UID = UID;
		this.reservationNo = reservationNo;
	}
	public CreateModify getInstance() throws Exception{
		if (UID=="" || reservationNo=="") {
			throw new Exception("欄位不得空白");
		}
		return new CreateModify (UID,reservationNo);	
	}
}
