/**
 * 
 */
package server.equip.handler;

import java.util.List;

import server.RequestContext;
import server.equip.entity.EquipPo;
import server.equip.service.EquipService;
import server.net.ErrorCode;
import server.net.NetDown;
import server.net.NetDownUpgradeEquip;
import server.player.entity.PlayerPo;
import server.player.service.PlayerService;

/**
 * @author Xiong
 *
 */
public class UpgradeEquip {
	public static NetDownUpgradeEquip handle(long uid, long id, long consumeCoin){
		try{
			NetDownUpgradeEquip down = new NetDownUpgradeEquip();
			PlayerPo dbPly = PlayerService.getPlayerPo(uid);
			if (dbPly == null){
				return NetDown.returnError(ErrorCode.USER_NOT_FOUND);
			}
			List<EquipPo> equips = EquipService.getAllEquips(uid);
			EquipPo dbEquip = EquipService.getEquipPo(equips, id);
			if (dbEquip == null){
				return NetDown.returnError(ErrorCode.EQUIP_NOT_FOUND);
			}
			
			if (dbPly.coin == 0){
				return NetDown.returnError(ErrorCode.COIN_NOT_ENOUGH);
			}
			
			if (dbPly.coin < consumeCoin){
				consumeCoin = dbPly.coin;
			}
			
			// 消耗玩家金币
			dbPly.coin -= consumeCoin;
			down.addProp("player.coin", String.valueOf(dbPly.coin));
			
			// 增加装备经验
			dbEquip.exp += consumeCoin;
			down.addProp("equip.exp#"+dbEquip.id, String.valueOf(dbEquip.exp));
			
			PlayerService.updatePlayerPo(dbPly);
			EquipService.updateEquipPo(equips, dbEquip);
			return down;
		}catch (Exception e){
			return NetDown.returnError(ErrorCode.SERVER_EXCEPTION);
		}finally{
			RequestContext.clear();
		}
	}
}
