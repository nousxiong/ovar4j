/**
 * 
 */
package server.equip.observer;

import static org.junit.Assert.assertTrue;

import java.util.List;

import ovar.AbstractObserver;
import server.ConfigData;
import server.RequestContext;
import server.equip.entity.EquipCtx;
import server.equip.entity.EquipPo;
import server.equip.service.EquipService;
import server.net.NetDown;
import server.util.DataKey;

/**
 * @author Xiong
 * 
 */
public class EquipLevelUp extends AbstractObserver<Long, EquipCtx> {

	public EquipLevelUp(EquipCtx ctx) {
		super(ctx);
	}

	@Override
	public void onNext(EquipCtx ctx, Long oldValue, Long newValue) {
		List<EquipPo> dbEquips = EquipService.getAllEquips(ctx.uid);
		assertTrue(dbEquips != null);
		
		EquipPo dbEquip = EquipService.getEquipPo(dbEquips, ctx.id);
		assertTrue(dbEquip != null);
		
		// 简单的升级规则：每expPerEquipLevel点金币涨1级
		int oldLv = (int) (oldValue / ConfigData.expPerEquipLevel);
		int newLv = (int) (newValue / ConfigData.expPerEquipLevel);
		if (oldLv == newLv){
			return;
		}
		
		dbEquip.level = newLv;
		NetDown down = RequestContext.get(DataKey.NET_DOWN.getKey());
		assertTrue(down != null);
		down.addProp("equip.level#"+dbEquip.id, String.valueOf(dbEquip.level));
		EquipService.updateEquipPo(dbEquips, dbEquip);
	}

}
