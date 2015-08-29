package com.services.common;


/**
 * List of All JDBC Queries
 * 
 * @author Shahbaz.Alam
 */
public class JdbcQueryConstant {
	
	//User Sqls
	
	public static final String Query_Validate_User = "select * from USER where User_Id = ? and Password = ?";
	
	public static final String Query_Add_User = "insert into USER (Full_Name, User_Id, Password, Is_Admin) values (?, ?, ?,'N')";
	
	public static final String Remove_User = "delete from USER where User_Id = ?";
	
	public static final String Get_All_User = "select * from USER";
	
	public static final String Check_User = "select * from USER where User_Id = ?";

	//Purchaser Sqls
	
	public static final String Query_Add_Purchaser = "Insert into PURCHASER (Purchaser_Name) values (?)";
	
	public static final String Remove_Purchaser = "Delete from PURCHASER where Purchaser_Name = ?";
	
	public static final String Get_All_Purchaser = "select * from PURCHASER";
	
	//Record Sqls
	
	public static final String Save_Record = "Insert into RECORD (DATE, PURCHASER_NAME, VEHICLE_NBR, CROP_NAME, BAG_QTY, NET_WEIGHT, ACTUAL_WEIGHT, "
			+ "CROP_RATE, TOTAL_COST, MARKET_FEE, SUPERVISION_FEE, TOTAL_TAX, PAYMENT_DATE, PAYMENT_MODE, PAYMENT_STATUS, CHECQUE_NBR) "
			+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	public static final String Get_All_Record_For_Current_Date = "select * from RECORD where DATE = ?";
	
	public static final String Delete_Record = "Delete from RECORD where RECORD_ID = ?";
	
	public static final String Update_Record = "Update Record set PAYMENT_DATE = ?, PAYMENT_MODE = ?, PAYMENT_STATUS = ? ";
	
	public static final String Add_Checque_Nbr = ", CHECQUE_NBR = ? ";
	
	public static final String WHERE_RECORD_ID = "WHERE RECORD_ID = ?";
	
}
