package team.t4.booking.tk;
/*
 * 這個自訂的例外主要是防止有不合法的輸入（例如訂票沒寫資料）
 * 然後App可以利用他來自動操作一些東西（畫面切換or訊息丟出等等）
 * 
 * 然後可以順便加分
 * 
 */
public class FillException extends Exception{
	public FillException(String message) {
		super(message);
	}

}
