/**
 * 
 */
package server.player.service;

import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import server.RequestContext;
import server.player.entity.PlayerOb;
import server.player.entity.PlayerPo;
import server.util.DataKey;
import server.util.DataUtil;
import server.util.ObsvarKey;
import server.util.ObsvarUtil;

/**
 * @author Xiong
 *
 */
public class PlayerService {
	/**
	 * 模拟数据库玩家表
	 * uid为key
	 */
	private static final Map<Long, PlayerPo> tabPlayer = new ConcurrentHashMap<Long, PlayerPo>();
	
	/**
	 * 插入一个新PlayerPo
	 * @param po
	 */
	public static void addPlayerPo(PlayerPo po){
		tabPlayer.put(po.uid, po);
	}
	
	/**
	 * 从数据库或缓存读取PlayerPo
	 * @param uid
	 * @return
	 */
	public static PlayerPo getPlayerPo(long uid){
		String key = DataUtil.getKey(DataKey.PLAYER, uid);
		PlayerPo po = RequestContext.get(key);
		if (po == null){
			po = tabPlayer.get(uid);
			if (po == null){
				return null;
			}
			// 更新缓存
			RequestContext.put(key, po);
		}else{
			po.uid = uid;
		}
		
		PlayerOb ob = getPlayerOb(po);
		assertTrue(ob != null);
		
		// 初始化player
		ob.exp.init(po.exp);
		ob.charge.init(po.charge);
		ob.vip.init(po.vip);
		ob.quest.init(po.quest);
		
		return po;
	}
	
	/**
	 * 更新PlayerPo
	 * @param po
	 */
	public static void updatePlayerPo(PlayerPo po){
		// 更新到数据库
		tabPlayer.put(po.uid, po);
		
		// 更新缓存
		String key = DataUtil.getKey(DataKey.PLAYER, po.uid);
		RequestContext.put(key, po);
		
		// 处理变量变更
		PlayerOb ob = getPlayerOb(po);
		assertTrue(ob != null);
		
		ob.exp.set(po.exp);
		ob.charge.set(po.charge);
		ob.vip.set(po.vip);
		ob.quest.set(po.quest);
	}
	
	/**
	 * 获取PlayerOb
	 * @param po
	 * @return
	 */
	private static PlayerOb getPlayerOb(PlayerPo po){
		String key = ObsvarUtil.getKey(ObsvarKey.PLAYER, po.uid);
		PlayerOb ob = RequestContext.get(key);
		if (ob == null){
			ob = new PlayerOb(po.uid);
			RequestContext.put(key, ob);
		}
		return ob;
	}
}
