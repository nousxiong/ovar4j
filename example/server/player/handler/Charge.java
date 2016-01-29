/**
 * 
 */
package server.player.handler;

import server.RequestContext;
import server.net.ErrorCode;
import server.net.NetDown;
import server.net.NetDownCharge;
import server.player.entity.PlayerPo;
import server.player.service.PlayerService;

/**
 * @author Xiong
 *
 */
public class Charge {
	public static NetDownCharge handle(long uid, int money){
		try{
			NetDownCharge down = new NetDownCharge();
			PlayerPo dbPly = PlayerService.getPlayerPo(uid);
			if (dbPly == null){
				return NetDown.returnError(ErrorCode.USER_NOT_FOUND);
			}
			
			dbPly.money += money;
			dbPly.charge += money;
			down.addProp("player.money", String.valueOf(dbPly.money));
			down.addProp("player.charge", String.valueOf(dbPly.charge));
			PlayerService.updatePlayerPo(dbPly);
			return down;
		}catch (Exception e){
			return NetDown.returnError(ErrorCode.SERVER_EXCEPTION);
		}finally{
			RequestContext.clear();
		}
	}
}
