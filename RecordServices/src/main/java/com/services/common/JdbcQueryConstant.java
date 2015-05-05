package com.services.common;


/*
 * List of All JDBC Queries
 * 
 * @author Shahbaz.Alam
 */
public class JdbcQueryConstant {
	
	public static final String Query_Validate_User = "select * from USER where User_Id = ? and Password = ?";
	
	public static final String Query_Add_User = "insert into USER (Full_Name, User_Id, Password, Is_Admin) values (?, ?, ?,'N')";
	
	public static final String Remove_User = "delete from USER where User_Id = ?";
	
	public static final String Get_All_User = "select * from USER";

}
