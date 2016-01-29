/**
 * 
 */
package server.player.observer;

import static org.junit.Assert.assertTrue;
import ovar.AbstractObserver;
import server.ConfigData;
import server.RequestContext;
import server.net.NetDown;
import server.player.entity.PlayerPo;
import server.player.service.PlayerService;
import server.util.DataKey;

/**
 * @author Xiong
 *
 */
public class PlayerLevelUp extends AbstractObserver<Long, Long> {

	public PlayerLevelUp(Long ctx) {
		super(ctx);
	}

	@Override
	public void onNext(Long ctx, Long oldValue, Long newValue) {
		PlayerPo dbPly = PlayerService.getPlayerPo(ctx);
		assertTrue(dbPly != null);
		
		// 简单的升级规则：每expPerLevel点经验值涨1级
		int oldLv = (int) (oldValue / ConfigData.expPerLevel);
		int newLv = (int) (newValue / ConfigData.expPerLevel);
		if (oldLv == newLv){
			return;
		}
		dbPly.level = newLv;
		NetDown down = RequestContext.get(DataKey.NET_DOWN.getKey());
		assertTrue(down != null);
		down.addProp("player.level", String.valueOf(dbPly.level));
		PlayerService.updatePlayerPo(dbPly);
	}

}
