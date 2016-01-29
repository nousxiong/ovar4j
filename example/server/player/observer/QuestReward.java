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
public class QuestReward extends AbstractObserver<Integer, Long> {

	public QuestReward(Long ctx) {
		super(ctx);
	}

	@Override
	public void onNext(Long ctx, Integer oldValue, Integer newValue) {
		PlayerPo dbPly = PlayerService.getPlayerPo(ctx);
		assertTrue(dbPly != null);
		
		// 任务完成固定奖励玩家经验和金币
		dbPly.exp += ConfigData.questExp;
		dbPly.coin += ConfigData.questCoin;
		NetDown down = RequestContext.get(DataKey.NET_DOWN.getKey());
		assertTrue(down != null);
		down.addProp("player.exp", String.valueOf(dbPly.exp));
		down.addProp("player.coin", String.valueOf(dbPly.coin));
		PlayerService.updatePlayerPo(dbPly);
	}

}
