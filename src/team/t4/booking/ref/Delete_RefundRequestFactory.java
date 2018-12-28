package team.t4.booking.ref;

import javax.management.RuntimeMBeanException;

import team.t4.booking.tk.FillException;

/**
 * This factory creates refundRequest.
 */
public class Delete_RefundRequestFactory implements RefundRequestFactory{
	
	private String UID;
	private String reservationNo;
	private String[][] searchResult;
	
	public Delete_RefundRequestFactory(String UID, String reservationNo) {
		this.UID = UID;
		this.reservationNo = reservationNo;
	}
	public Delete_RefundRequest getInstance() throws Exception{
		if (UID=="" || reservationNo=="") {
			throw new FillException("欄位不得空白");
		}
		return new Delete_RefundRequest(UID,reservationNo);	
	}

}
