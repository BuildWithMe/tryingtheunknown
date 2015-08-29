package com.services.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.services.common.JdbcQueryConstant;
import com.services.exception.DaoException;
import com.services.model.User;


/**
 * This is the Dao Layer for all User related Database interactions
 * 
 * @author Shahbaz.Alam
 */
@Component
@Repository
public class UserDao {
	@Autowired
	@Qualifier("primaryDs")
	private DataSource datasource;
	
	/**
	 * The method gets the User details from the User table based on the UserName and Password
	 * It returns NUll if no such user is found
	 * 
	 * @return User object
	 */
	public User getUser(String username, String password) throws DaoException{
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<User> listUser = null;
		
		List<Object> paramObjs = new ArrayList<Object>();
		paramObjs.add(username);
		paramObjs.add(password);
		
		List<Map<String, Object>> resultList = null;
		try{
			resultList = template.queryForList(JdbcQueryConstant.Query_Validate_User, paramObjs.toArray());
		}catch(Exception ex){
			throw new DaoException("Exception in UserDao.getUser", ex);
		}
		if(CollectionUtils.isEmpty(resultList) || MapUtils.isEmpty(resultList.get(0))){
			return null;
		}
		listUser = getUserObject(resultList);
		return listUser.get(0);
	}
	

	/**
	 * This method adds the User details in the User Table
	 * 
	 * @return The number of rows updated
	 */
	public int addUser(User user) throws DaoException{
		JdbcTemplate template = new JdbcTemplate(datasource);		
		List<Object> paramObjs = new ArrayList<Object>();
		paramObjs.add(user.getFullName());
		paramObjs.add(user.getUserId());
		paramObjs.add(user.getPassword());
		
		int result = 0;		
		try{
			result = template.update(JdbcQueryConstant.Query_Add_User, paramObjs.toArray());
		}catch(Exception ex){
			throw new DaoException("Exception in UserDao.AddUser", ex);
		}
		return result;
	}
	
	
	/**
	 * This method deletes one User detail from the User Table based on its user_Id
	 */
	public void removeUser(User user) throws DaoException{
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Object> paramObjs = new ArrayList<Object>();
		paramObjs.add(user.getUserId());
		
		try{
			template.update(JdbcQueryConstant.Remove_User, paramObjs.toArray());
		}catch(Exception ex){
			throw new DaoException("Exception in UserDao.RemoveUser", ex);
		}
		
	}
	
	
	/**
	 * This method retrieves all the Users from the User Table
	 * 
	 * @returns List<User>
	 */
	public List<User> getAllUser() throws DaoException{
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Map<String, Object>> resultList = null;
		List<User> listUsers = null;		
		try{
			resultList = template.queryForList(JdbcQueryConstant.Get_All_User);
		}catch(Exception ex){
			throw new DaoException("Exception in UserDao.GetAllUser", ex);
		}
		if(resultList != null){
			listUsers = getUserObject(resultList);
		}
		
		return listUsers;
	}
	
	/**
	 * It checks whether the user id is present in the db or not
	 * @param userid
	 * @return
	 * @throws DaoException 
	 */
	public boolean checkUser(String userid) throws DaoException {
		boolean value = true;
		JdbcTemplate template = new JdbcTemplate(datasource);
		List<Map<String, Object>> resultList = null;
		List<Object> paramObjs = new ArrayList<>();
		paramObjs.add(userid);
		try{
			resultList = template.queryForList(JdbcQueryConstant.Check_User, paramObjs.toArray());
		}catch(Exception ex){
			throw new DaoException("Exception in UserDao.CheckUser",ex);
		}
		if(CollectionUtils.isEmpty(resultList) || MapUtils.isEmpty(resultList.get(0))){
			value = false;
		}		
		return value;
	}
	
	
	/**
	 * This method is used to populate the User Object from the ResultList
	 * 
	 * @return List<User>
	 */
	private List<User> getUserObject(List<Map<String, Object>> resultList) {
		User user  = null;
		List<User> listUser = new ArrayList<User>();
		for(Map<String, Object> result : resultList){
			user  = new User();
			user.setAdmin(result.get("Is_Admin").equals("Y") ? true : false);
			user.setFullName((String) result.get("Full_Name"));
			user.setPassword((String) result.get("Password"));
			user.setUserId((String) result.get("User_Id"));
			listUser.add(user);
		}		
		return listUser;
	}

}
