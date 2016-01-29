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
import server.net.NetDownUpgradeEquipStar;
import server.player.entity.PlayerPo;
import server.player.service.PlayerService;

/**
 * @author Xiong
 *
 */
public class UpgradeEquipStar {
	public static NetDownUpgradeEquipStar handle(long uid, long id, long consumeMoney){
		try{
			NetDownUpgradeEquipStar down = new NetDownUpgradeEquipStar();
			PlayerPo dbPly = PlayerService.getPlayerPo(uid);
			if (dbPly == null){
				return NetDown.returnError(ErrorCode.USER_NOT_FOUND);
			}
			List<EquipPo> equips = EquipService.getAllEquips(uid);
			EquipPo dbEquip = EquipService.getEquipPo(equips, id);
			if (dbEquip == null){
				return NetDown.returnError(ErrorCode.EQUIP_NOT_FOUND);
			}
			
			if (dbPly.money == 0){
				return NetDown.returnError(ErrorCode.MONEY_NOT_ENOUGH);
			}
			
			if (dbPly.money < consumeMoney){
				consumeMoney = dbPly.money;
			}
			
			// 金币必须消耗和代币同样的值
			long consumeCoin = consumeMoney;
			if (dbPly.coin < consumeCoin){
				return NetDown.returnError(ErrorCode.COIN_NOT_ENOUGH);
			}
			
			// 消耗代币和金币
			dbPly.money -= consumeMoney;
			dbPly.coin -= consumeCoin;
			down.addProp("player.money", String.valueOf(dbPly.money));
			down.addProp("player.coin", String.valueOf(dbPly.coin));
			
			// 增加装备星级和人物经验
			long updStar = consumeMoney;
			long updExp = consumeMoney;
			dbEquip.star += updStar;
			dbPly.exp += updExp;
			down.addProp("equip.star#"+dbEquip.id, String.valueOf(dbEquip.star));
			down.addProp("player.exp", String.valueOf(dbPly.exp));
			
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
