/**
 * 
 */
package server.player.observer;

import static org.junit.Assert.assertTrue;
import ovar.AbstractObserver;
import server.RequestContext;
import server.net.NetDown;
import server.player.entity.PlayerPo;
import server.player.service.PlayerService;
import server.util.DataKey;

/**
 * @author Xiong
 *
 */
public class VipPrivilege extends AbstractObserver<Integer, Long> {

	public VipPrivilege(Long ctx) {
		super(ctx);
	}

	@Override
	public void onNext(Long ctx, Integer oldValue, Integer newValue) {
		PlayerPo dbPly = PlayerService.getPlayerPo(ctx);
		assertTrue(dbPly != null);
		
		int vip = newValue;
		switch (vip){
		case 1:{
			dbPly.title = "小康";
			break;
		}case 2:{
			dbPly.title = "中产阶级";
			break;
		}case 3:{
			dbPly.title = "土豪";
			break;
		}case 4:{
			dbPly.title = "大富豪";
			break;
		}default:{
			assertTrue(vip > 4);
			dbPly.title = "大富豪";
			break;
		}}
		NetDown down = RequestContext.get(DataKey.NET_DOWN.getKey());
		assertTrue(down != null);
		down.addProp("player.title", dbPly.title);
		PlayerService.updatePlayerPo(dbPly);
	}

}
