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
public class VipLevelUp extends AbstractObserver<Long, Long> {

	public VipLevelUp(Long ctx) {
		super(ctx);
	}

	@Override
	public void onNext(Long ctx, Long oldValue, Long newValue) {
		PlayerPo dbPly = PlayerService.getPlayerPo(ctx);
		assertTrue(dbPly != null);
		
		// 简单的vip升级规则：每充chargePerVipLevel点代币涨1级vip
		int oldVip = (int) (oldValue / ConfigData.chargePerVipLevel);
		int newVip = (int) (newValue / ConfigData.chargePerVipLevel);
		if (oldVip == newVip){
			return;
		}
		dbPly.vip = newVip;
		NetDown down = RequestContext.get(DataKey.NET_DOWN.getKey());
		assertTrue(down != null);
		down.addProp("player.vip", String.valueOf(dbPly.vip));
		PlayerService.updatePlayerPo(dbPly);
	}

}
