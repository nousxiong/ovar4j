/**
 * 
 */
package server.login.service;

import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import server.RequestContext;
import server.login.entity.UserOb;
import server.login.entity.UserPo;
import server.util.DataKey;
import server.util.DataUtil;
import server.util.ObsvarKey;
import server.util.ObsvarUtil;

/**
 * @author Xiong
 *
 */
public class LoginService {
	/**
	 * 模拟数据库用户表
	 * username为key
	 */
	private static final Map<String, UserPo> tabUser = new ConcurrentHashMap<String, UserPo>();
	/** 用户唯一id产生基础 */
	private static AtomicLong uidBase = new AtomicLong(0);
	
	/**
	 * 产生一个新的uid
	 * @return
	 */
	public static long generateUid(){
		return uidBase.incrementAndGet();
	}
	
	/**
	 * 插入一个新UserPo
	 * @param po
	 */
	public static void addUserPo(UserPo po){
		tabUser.put(po.username, po);
	}
	
	/**
	 * 从数据库或者缓存读取UserPo
	 * @param username
	 * @return
	 */
	public static UserPo getUserPo(String username){
		String key = DataUtil.getKey(DataKey.USER, username);
		UserPo po = RequestContext.get(key);
		if (po == null){
			po = tabUser.get(username);
			if (po == null){
				return null;
			}
			// 更新缓存
			RequestContext.put(key, po);
		}else{
			assertTrue(po.username.equals(username));
		}
		
		UserOb ob = getUserOb(po);
		assertTrue(ob != null);
		
		// 初始化user
		ob.password.init(po.password);
		// 返回
		return po;
	}
	
	/**
	 * 更新UserPo
	 * @param po
	 */
	public static void updateUserPo(UserPo po){
		// 更新数据库
		tabUser.put(po.username, po);
		
		// 更新缓存
		String key = DataUtil.getKey(DataKey.USER, po.username);
		RequestContext.put(key, po);
		
		// 处理变量变更
		UserOb ob = getUserOb(po);
		assertTrue(ob != null);
		
		ob.password.set(po.password);
	}
	
	/**
	 * 获取UserOb
	 * @param po
	 * @return
	 */
	private static UserOb getUserOb(UserPo po){
		String key = ObsvarUtil.getKey(ObsvarKey.USER, po.uid);
		UserOb ob = RequestContext.get(key);
		if (ob == null){
			ob = new UserOb(po.username);
			RequestContext.put(key, ob);
		}
		return ob;
	}
}
