/**
 * 
 */
package server.player.handler;

import server.RequestContext;
import server.net.ErrorCode;
import server.net.NetDown;
import server.net.NetDownCompleteQuest;
import server.player.entity.PlayerPo;
import server.player.service.PlayerService;

/**
 * @author Xiong
 *
 */
public class CompleteQuest {
	public static NetDownCompleteQuest handle(long uid){
		try{
			NetDownCompleteQuest down = new NetDownCompleteQuest();
			PlayerPo dbPly = PlayerService.getPlayerPo(uid);
			if (dbPly == null){
				return NetDown.returnError(ErrorCode.USER_NOT_FOUND);
			}
			
			dbPly.quest++;
			down.addProp("player.quest", String.valueOf(dbPly.quest));
			PlayerService.updatePlayerPo(dbPly);
			return down;
		}catch (Exception e){
			return NetDown.returnError(ErrorCode.SERVER_EXCEPTION);
		}finally{
			RequestContext.clear();
		}
	}
}
