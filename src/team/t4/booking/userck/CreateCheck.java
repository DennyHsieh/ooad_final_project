package team.t4.booking.userck;

import team.t4.booking.db.Schedule;
import team.t4.booking.tk.Station;

public class CreateCheck {
	
	private String UID = "";
	private String reservationNo = "";
	private Station destination;
	private Station depart;
	private String date = "";
	private String trainNo = "";
	private String checkDate = "";
	private String checkTime = "";
	private String timeCondition = "";
	private Station checkDepart;
	private Station checkDestination;
	
	CreateCheck (String UID, String reservationNo, 
							String destination, String depart, 
							String date, String trainNo,
							String checkDate, String checkTime,
							String timeCondition, String checkDepart,
							String checkDestination) {
		
		
		this.UID = UID;
		this.reservationNo = reservationNo;
		if(!depart.equals(""))
			this.depart = Station.valueOf(depart);
		if(!destination.equals(""))
			this.destination = Station.valueOf(destination);
		this.date = date;
		this.trainNo = trainNo;
		this.checkDate = checkDate;
		this.checkTime = checkTime;
		this.timeCondition = timeCondition;
		if(!checkDepart.equals(""))
			this.checkDepart = Station.valueOf(checkDepart);
		if(!checkDestination.equals(""))
			this.checkDestination = Station.valueOf(checkDestination);
	}

	public String[] checkWithRn() {
	    
	    String[] result = new String[4];
	    int guiRow = 0;
	    CheckTransaction checkTransaction = new CheckTransaction();
	    
	    try {
	        String[][] searchResult = checkTransaction.getTransactWithRn(UID,reservationNo);
	        int i = 0;
	        while(i<2 && searchResult[i][0]!=null) {
	            result[guiRow++] = ("<車次>     <日期>     <起站>    <訖站>    <出發時間>     <到達時間>    <行車時間>    <座位資訊>");
	            
	            
	            result[guiRow++] = "["+searchResult[i][6]+"]   "+
	            searchResult[i][1]+"     "+
	            searchResult[i][7]+"      "+searchResult[i][8]+"      "+
	            searchResult[i][3]+"         "+searchResult[i][4]+"        "+
	            searchResult[i][5]+"       "+searchResult[i][10]+" \n";
	            System.out.println("["+searchResult[i][6]+"]   "+
	    	            searchResult[i][1]+"     "+
	    	            searchResult[i][7]+"      "+searchResult[i][8]+"      "+
	    	            searchResult[i][3]+"         "+searchResult[i][4]+"        "+
	    	            searchResult[i][5]+"       "+searchResult[i][10]+" \n");
	            i++;
	        }
	        return result;
	    }
	    catch (Exception e) {
	        System.out.println(e);
	    }
	    return result;
	}
	
	 public String[] checkWithoutRn() {
	        String[] searchWithoutRn = new String[10];
	        int gui_row = 0;

	        try {
	            System.out.println("createCheck line 71");
	            CheckTransaction checkTransaction = new CheckTransaction();
	            System.out.println("createCheck line 73");
	            String[][] searchResult = checkTransaction.getTransactResultWithoutRN(UID, depart, destination, date, trainNo);
	            int i = 0;

	            while (i < 10 && searchResult[i][0] != null) {
	                searchWithoutRn[gui_row++] = ("<訂位代號>               <交易狀態>               <UID>        "
	                        + "<去回程>     <車次>     <日期>     <起站>    <訖站>    <出發時間>     <到達時間>    <行車時間>    <座位資訊>");


	                searchWithoutRn[gui_row++] = (
	                        searchResult[i][17] + "      " +
	                                searchResult[i][18] + "     " +
	                                searchResult[i][0] + "       " +
	                                searchResult[i][2] + "       " +
	                                "[" + searchResult[i][6] + "]   " +
	                                searchResult[i][1] + "    " +
	                                searchResult[i][7] + "      " + searchResult[i][8] + "       " +
	                                searchResult[i][3] + "         " + searchResult[i][4] + "        " +
	                                searchResult[i][5] + "       " + searchResult[i][10]);
	                searchWithoutRn[gui_row++] = ("<車廂>                   <票種數量>                                <總價>");
	                searchWithoutRn[gui_row++] = (
	                        searchResult[i][11] + "   " +
	                                "一般票:" + searchResult[0][12] + "張 " +
	                                "大學生票:" + searchResult[0][13] + "張 " +
	                                "兒童票:" + searchResult[0][14] + "張 " +
	                                "敬老票:" + searchResult[0][15] + "張 " +
	                                "愛心票:" + searchResult[0][16] + "張      " +
	                                searchResult[0][9] + "元" + "\n");
//	                searchWithoutRn[gui_row++] = ("");
	                i++;
	            }
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	        return searchWithoutRn;
	    }

	    public String[] checkNormalTable() {
//			printline
	        String[] searchNormalTable = new String[250];

	        Schedule schedule = new Schedule();
	        String[][] south = schedule.getSouthTimeTable(checkDate);
	        String[][] north = schedule.getNorthTimeTable(checkDate);

	        int row = 0;
	        int gui_row = 0;
	       // searchNormalTable[gui_row++] = ("\n\n");
	       // searchNormalTable[gui_row++] += ("");
	        //searchNormalTable[gui_row++] += ("");
	        searchNormalTable[gui_row++] = ("<<南下列車>>" + "\n");
	       // searchNormalTable[gui_row++] += ("");
	        searchNormalTable[gui_row++] = ("<車次>    <南港站>   <台北站>   <板橋站>   <桃園站>   <新竹站>   <苗栗站>   <台中站>   <彰化站>   <雲林站>   <嘉義站>   <台南站>   <左營站>");
	       // searchNormalTable[gui_row++] += ("");
	        while (row < south.length && south[row][0] != null) {
	            searchNormalTable[gui_row] = ("[" + south[row][0] + "]         ");
	            for (int i = 1; i < south[row].length; i++) {
	                if (south[row][i].equals(""))
	                    searchNormalTable[gui_row] += ("                   ");
	                else
	                    searchNormalTable[gui_row] += (south[row][i] + "          ");
	            }
	            gui_row++;
	           // searchNormalTable[gui_row++] += ("\n");
	           // searchNormalTable[gui_row++] += ("");
	            row++;
	        }

	        row = 0;
	      //  searchNormalTable[gui_row++] = ("\n\n");
	       // searchNormalTable[gui_row++] += ("");
	       // searchNormalTable[gui_row++] += ("");
	        searchNormalTable[gui_row++] = ("<<北上列車>>" + "\n");
	       // searchNormalTable[gui_row++] += ("");
	        searchNormalTable[gui_row++] = ("<車次>    <左營站>   <台南站>   <嘉義站>   <雲林站>   <彰化站>   <台中站>   <苗栗站>   <新竹站>   <桃園站>   <板橋站>   <台北站>   <南港站>");
	       // searchNormalTable[gui_row++] += ("");
	        while (row < north.length && north[row][0] != null) {
	            searchNormalTable[gui_row] = ("[" + north[row][0] + "]         ");
	            for (int i = 1; i < north[row].length; i++) {
	                if (north[row][i].equals(""))
	                    searchNormalTable[gui_row] += ("                   ");
	                else
	                    searchNormalTable[gui_row] += (north[row][i] + "          ");
	            }
	            gui_row++;
	         //   searchNormalTable[gui_row++] += ("\n");
	         //  searchNormalTable[gui_row++] += ("");
	            row++;
	        }
	        return searchNormalTable;
	    }

	    public String[] checkConcessionTable() {
	        String[] searchConcessionTable = new String[250];
	        int gui_row = 0;

	        try {
	            CheckTimeTable checkTimeTable = new CheckTimeTable();
	            String[][] info = checkTimeTable.getConcessionTable(checkDate, checkTime, timeCondition, checkDepart, checkDestination);

	            //searchConcessionTable[gui_row++] = ("info[0][0] = " + info[0][0]);
	            //searchConcessionTable[gui_row++] = ("info[1][0] = " + info[1][0]);
	            if (info[0][0] != null) {
	                int trainCount = 0;
	                searchConcessionTable[gui_row++] = (" <車次>     <大學生折數>   <早鳥折數>     <起站>   <訖站>   <出發時間>  <到達時間>  <行車時間>" + "\n");
	                //searchConcessionTable[gui_row++] = ("");
	                while (trainCount < 10 && info[trainCount][0] != null) {
	                    searchConcessionTable[gui_row++] = (" [" + info[trainCount][0] + "]          " +
	                            (info[trainCount][1] == null ? "                        " : " " + info[trainCount][1] + "折          ") +
	                            (info[trainCount][2] == null ? "                     " : (" " + info[trainCount][2]) + ((info[trainCount][2].length() == 4) ? "折          " : "折           ")) +
	                            info[trainCount][4] + "        " +
	                            info[trainCount][5] + "         " +
	                            info[trainCount][6] + "             " +
	                            info[trainCount][7] + "             " +
	                            info[trainCount][8]);
	                    //searchConcessionTable[gui_row++] = ("\n");
	                   // searchConcessionTable[gui_row++] = ("");
	                    trainCount++;
	                }
	            } else {
	                System.out.println("查無相關車次");
	            }
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	        }
	        return searchConcessionTable;
	    }
	}
