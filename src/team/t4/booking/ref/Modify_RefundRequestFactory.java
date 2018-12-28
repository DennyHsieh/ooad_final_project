package team.t4.booking.ref;

import team.t4.booking.tk.FillException;

/**
 * This factory creates refundRequestFactory
 */
public class Modify_RefundRequestFactory implements RefundRequestFactory{
	
	private String UID;
	private String reservationNo;
	private String[][] searchResult;
	
	public Modify_RefundRequestFactory(String UID, String reservationNo) {
		this.UID = UID;
		this.reservationNo = reservationNo;
	}
	public Modify_RefundRequest getInstance() throws Exception{
		if (UID=="" || reservationNo=="") {
			throw new FillException("欄位不得空白");
		}
		return new Modify_RefundRequest(UID,reservationNo);	
	}
}
