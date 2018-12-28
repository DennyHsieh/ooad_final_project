package team.t4.booking.userck;

import team.t4.booking.tk.FillException;

public class ResNo_User_CheckRequestFactory extends CheckRequestFactory {

	private String UID;
	private String depart;
	private String destination;
	private String date;
	private String trainNo;
	
	public ResNo_User_CheckRequestFactory(String UID, String depart,	String destination, 
							String date, String trainNo) {
			this.UID = UID;
			this.destination = destination;
			this.depart = depart;
			this.date = date.split("/")[0]+date.split("/")[1]+date.split("/")[2];
			this.trainNo = trainNo;
	}
	
	@Override
	public ResNo_User_CheckRequest getInstance() throws FillException {
		if (UID=="" || destination=="" || depart =="" || date=="" || trainNo=="") {
			throw new FillException("欄位不得空白");
		}
		if (UID.length()!=10 ) {
			throw new FillException("錯誤的身分證字號長度");
		}
		return new ResNo_User_CheckRequest(UID,depart,destination,date,trainNo);
	}


}
