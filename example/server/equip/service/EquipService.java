/**
 * 
 */
package server.equip.service;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import server.RequestContext;
import server.equip.entity.EquipPo;
import server.equip.entity.EquipOb;
import server.util.DataKey;
import server.util.DataUtil;
import server.util.ObsvarKey;
import server.util.ObsvarUtil;

/**
 * @author Xiong
 *
 */
public class EquipService {
	/**
	 * 模拟数据库装备表
	 * uid为key，每个玩家都有一个组装备
	 */
	private static final Map<Long, List<EquipPo>> tabEquip = new ConcurrentHashMap<Long, List<EquipPo>>();
	/** 装备唯一id产生基础 */
	private static AtomicLong eidBase = new AtomicLong(0);

	/**
	 * 产生一个新的装备id
	 * @return
	 */
	public static long generateEid(){
		return eidBase.incrementAndGet();
	}
	
	public static void addAllEquips(long uid, List<EquipPo> equips){
		tabEquip.put(uid, equips);
	}
	
	/**
	 * 从数据库或缓存读取全部的装备
	 * @param uid
	 * @return
	 */
	public static List<EquipPo> getAllEquips(long uid){
		String key = DataUtil.getKey(DataKey.ALL_EQUIPS, uid);
		List<EquipPo> equips = RequestContext.get(key);
		if (equips == null){
			equips = tabEquip.get(uid);
			if (equips == null){
				return null;
			}
			// 更新缓存
			RequestContext.put(key, equips);
		}
		return equips;
	}
	
	/**
	 * 根据id取得对应的装备
	 * @param equips
	 * @param id
	 * @return
	 */
	public static EquipPo getEquipPo(List<EquipPo> equips, long id){
		if (equips == null || equips.isEmpty()){
			return null;
		}
		
		EquipPo po = null;
		for (EquipPo equip : equips){
			if (equip.id == id){
				po = equip;
				break;
			}
		}
		if (po != null){
			EquipOb ob = getEquipOb(po);
			assertTrue(ob != null);
			
			// 初始化equip
			ob.exp.init(po.exp);
		}
		return po;
	}
	
	/**
	 * 更新装备
	 * @param equips
	 * @param po
	 */
	public static void updateEquipPo(List<EquipPo> equips, EquipPo po){
		assertTrue(equips != null && !equips.isEmpty());
		assertTrue(po != null);
		long uid = po.uid;
		
		// 更新到数据库
		for (int i=0; i<equips.size(); ++i){
			if (equips.get(i).id == po.id){
				equips.set(i, po);
				break;
			}
		}
		tabEquip.put(uid, equips);
		
		// 更新缓存
		String key = DataUtil.getKey(DataKey.ALL_EQUIPS, uid);
		RequestContext.put(key, equips);
		
		// 处理变量变更
		EquipOb ob = getEquipOb(po);
		assertTrue(ob != null);
		
		ob.exp.set(po.exp);
	}
	
	/**
	 * 获取EquipPo
	 * @param po
	 * @return
	 */
	private static EquipOb getEquipOb(EquipPo po){
		String key = ObsvarUtil.getKey(ObsvarKey.EQUIP, po.uid, po.id);
		EquipOb ob = RequestContext.get(key);
		if (ob == null){
			ob = new EquipOb(po.uid, po.id);
			RequestContext.put(key, ob);
		}
		return ob;
	}
}
