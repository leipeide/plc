package com.waho.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.waho.domain.Alarm;
import com.waho.domain.Device;
import com.waho.domain.Node;
import com.waho.domain.PageBean;
import com.waho.domain.User;

public interface UserService {
	/**
	 * 用户登录操作,登陆成功：返回用户的设备列表，登录失败：返回null（用户名或密码错误）
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public Map<String, Object> login(String username, String password);

	/**
	 * 根据集控器设备id获取节点信息
	 * 
	 * @return
	 */
	  public List<Node> getNodesByDeviceid(int deviceid);
	  /**
	   * 节点分页显示
	   * @param deviceid
	   * @param currentPage
	   * @param pageSize
	   * @return
	   */
	  public PageBean getNodesPageByDeviceid(int deviceid,int currentPage,int pageSize);
	  /**
	   * Ajax动态刷新节点页面
	   * @param deviceid
	   * @param currentPage
	   * @param pageSize
	   * @return
	   */
	  public PageBean refreshNodesPageByDeviceid(int deviceid, int currentPage, int pageSize);
	/**
	 * 用户发送节点控制指令（将用户指令写入数据库）
	 * 
	 * @param nodeid
	 * @param light1State
	 * @param light2State
	 * @param light1PowerPercent
	 * @param light2PowerPercent
	 */
	public void userWriteNodeCmd(int nodeid, String light1State, String light2State, String light1PowerPercent,
			String light2PowerPercent);

	/**
	 * 用户发送广播控制指令
	 * 
	 * @param deviceid
	 * @param light1State
	 * @param light2State
	 * @param light1PowerPercent
	 * @param light2PowerPercent
	 */
	public void userWriteBroadcastCmd(int deviceid, String light1State, String light2State, String light1PowerPercent,
			String light2PowerPercent);
	
	/**
	 * 用户手动刷新节点状态信息
	 * @param nodeid
	 */
	public void refreshNodeDataById(int nodeid);
	/**
	 * 用户发送开启节点主动注册指令
	 * @param deviceid
	 * @param startTime
	 * @param keepMinutes
	 */
	public void userWriteNodesRegisterOpenCmd(int deviceid, Date startTime, int keepMinutes);
	/**
	 * 将集控器的userid修改为传入的userid，即将集控器添加到相应用户。
	 * @param deviceMac
	 * @param userid
	 * @return
	 */
	public boolean addDeviceToUserByDeviceMac(String deviceMac, int userid);
	/**
	 * 从当前用户删除集控器
	 * @param deviceid
	 * @return
	 */
	public boolean removeDeviceByDeviceName(String[] arrdeviceName,int userid);
	/**
	 *根据userid获取集控器信息 
	 * @param userid
	 */
	public List<Device> getDevicesByUserid(int userid);
	/**
	 * 核对该用户是否已注册
	 * @param username
	 */
	public boolean checkUserByUsername(String username);
	/**
	 * 用户注册
	 * @param username
	 * @param password
	 * @param email
	 * @return
	 */
	public boolean registUser(String username,String password,String email);
	/**
	 * 向用户中的集控器中添加新的节点
	 * @param deviceid
	 * @param nodeAddr
	 */
	public void addNodesCmd(int deviceid,String [] nodeAddrString);
	/**
	 * 删除集控器下的节点
	 * @param deviceid
	 * @param nodename
	 */
	public int delNodesCmd(int deviceid,String [] nodeAddr);
	/**
	 *获取用户资料
	 * @return
	 */
	public User getUserMessage(int userid);
	/**
	    * 写入节点的新的名称
	 * @param nodeNameStr
	 * @param nodeid 
	 * @return
	 */
	public boolean nodeRename(String nodeName, int nodeid);
    /**
                * 写入集控器新的名称
     * @param deviceid
     * @param deviceRename
     * @return
     */
	public boolean deviceRename(int deviceid, String deviceRename);
	/**
	 * 修改用户密码
	 * @param userid
	 * @param newPassword
	 * @return
	 */
	public boolean updateUserPassword(int userid, String newPassword);
	/**
	 * 搜索节点功能
	 * @param deviceid 
	 * @param nodeAddr
	 * @return
	 */
	public Node serachNode(int deviceid, String nodeAddr);
	/**
	 * 时间范围内节点功率与工作时长折线图
	 * @param nodeAddr
	 * @param date 
	 */
	public Map<String, Object> DateRangeNodeChartMessage(String nodeAddr, String date);
	/**
	 * 获取报警信息
	 * @param userid
	 * @return
	 */
	public Map<String, Object> getWarnningMessageById(int userid);
	/**
	 * 根据报警信息id删除报警信息
	 * @param arrWarnningMessage
	 * @param userid 
	 * @return
	 */
	public Map<String, Object> delWarnningMessage(String[] arrWarnningMessage, int userid);
	/**
	 * 根据userid获取报警信息数量和信息
	 * @param parseInt
	 * @return
	 */
	public Map<String, Object> getWarnningMessageAndNumById(int userid);
	/**
	 * 根据nodeAddr获取节点页面信息，返回到节点页面
	 * @param nodeAddr
	 * @param deviceId 
	 * @return
	 */
	public PageBean returnNodesPageByReturnButton(String nodeAddr, int deviceId);
	/**
	 * 根据条件查找报警信息
	 * @param userid
	 * @param deviceMac
	 * @param nodeAddr
	 * @param type
	 * @param date
	 */
	public Map<String, Object>  serachWarnningMessageByFactor(int userid, String deviceMac, String nodeAddr, String typeStr,
			String date);
	
}
