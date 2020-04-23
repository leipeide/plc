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
	/**
	 * 根据邮箱查找用户
	 * @param email
	 * @return
	 * @throws Exception
	 */
	public User selectByEmail(String email)throws Exception;
	/**
	 * 根据主键id更新验证码和操作次数
	 * @param admin
	 * @throws Exception
	 */
	public int updateVerCodeAndOperateNumByPrimaryKey(User admin)throws Exception;
	/**
	 * 根据主键id更新用户密码
	 * @param id
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public boolean updateUserPasswordById(int id, String password)throws Exception;
	/**
	 * 每天定时清除用户验证码和操作数字
	 * @param verCode
	 * @param operateNum
	 */
	public void clearVercodeAndOpreateNum(String verCode, int operateNum)throws Exception;
	
}
