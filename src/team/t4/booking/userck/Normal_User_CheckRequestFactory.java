package team.t4.booking.userck;
import team.t4.booking.tk.FillException;

public class Normal_User_CheckRequestFactory extends CheckRequestFactory {
	private String UID;
	private String reservationNo;
	public Normal_User_CheckRequestFactory(String UID, String reservationNo) {
		
		this.UID = UID;
		this.reservationNo = reservationNo;
		
	}
	@Override
	public Normal_User_CheckRequest getInstance() throws FillException {
		if (UID=="" || reservationNo=="") {
			throw new FillException("欄位不得空白");
		}
		return new Normal_User_CheckRequest(UID,reservationNo);
	}

}
