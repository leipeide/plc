package com.waho.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.waho.dao.UserDao;
import com.waho.domain.User;
import com.waho.util.C3P0Utils;

public class UserDaoImpl implements UserDao {

	@Override
	public User selectUserByUsernameAndPassword(String username, String password) throws SQLException{
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		User u = qr.query("select * from users where username=? and password=?", new BeanHandler<User>(User.class),
				username, password);
		return u;
	}

	@Override
	public int insertUser(User user) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		return qr.update("INSERT INTO users (`id`, `username`, `password`, `email`, "
				+ "`phone`, `vercode`, `operatenum`) VALUES (?, ?, ?, ?, ?, ?, ?)",
				 user.getId(), user.getUsername(),user.getPassword(),user.getEmail(),
				 user.getPhone(), user.getVercode(), user.getOperateNum());
	}

	@Override
	public User selectUserByUsername(String username) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		User u = qr.query("select*from users where username=?",new BeanHandler<User>(User.class),username);
	    return u;
	}

	@Override
	public User selectUserByUserid(int userid)throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		User u = qr.query("select*from users where id=?",new BeanHandler<User>(User.class),userid);
	    return u;
	}

	@Override
	public int updateUserPasswordByPassword(User user) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		return qr.update("UPDATE users SET password=? WHERE id=?",user.getPassword(),user.getId());
	}

	@Override
	public User selectByEmail(String email) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		User u = qr.query("select*from users where email=?", new BeanHandler<User>(User.class), email);
		return u;
	}

	@Override
	public int updateVerCodeAndOperateNumByPrimaryKey(User admin) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
	    return qr.update("UPDATE users SET vercode=?, operatenum=? WHERE id=?",admin.getVercode(), admin.getOperateNum(), admin.getId());
	}

	@Override
	public boolean updateUserPasswordById(int id, String password) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
	    int num = qr.update("UPDATE users SET password=? WHERE id=?", password, id);
	    if(num == 1) {
	    	return true;
	    }else {
	    	return false;
	    }  
	}

	@Override
	public void clearVercodeAndOpreateNum(String verCode, int operateNum) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		qr.update("update users set vercode=?, operatenum=?",verCode, operateNum);
		
	}

	
	
}
