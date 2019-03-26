package com.waho.dao;

import com.waho.domain.User;

public interface UserDao {
	/**
	 * 根据用户名和密码查询用户信息 
	 * @param username
	 * @param password
	 * @return user对象
	 * @throws Exception
	 */
	public User selectUserByUsernameAndPassword(String username, String password) throws Exception;
	/**
	 *向数据库中添加一个新的用户
	 * @return
	 * @throws Exception
	 */
	public int insertUser(User user)throws  Exception;
	/**
	 * 根据用户名查询用户信息
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public User selectUserByUsername(String username) throws Exception;
	/**
	 *  根据用户id查询用户信息
	 * @param userid
	 * @return
	 */
	public User selectUserByUserid(int userid)throws Exception;
	/**
	 * 修改用户登录密码
	 * @param user
	 */
	public int updateUserPasswordByPassword(User user)throws Exception;
	
}
