/**
 * 
 */
package server.net;

import java.util.List;

import server.equip.entity.EquipPo;
import server.player.entity.PlayerPo;

/**
 * @author Xiong
 *
 */
public class NetDownLogin extends NetDown {
	private byte limit;
	private PlayerPo dbPly;
	private List<EquipPo> dbAllEquips;
	
	public byte getLimit() {
		return limit;
	}
	
	public void setLimit(byte limit) {
		this.limit = limit;
	}
	
	public PlayerPo getDbPly() {
		return dbPly;
	}
	
	public void setDbPly(PlayerPo dbPly) {
		this.dbPly = dbPly;
	}
	
	public List<EquipPo> getDbAllEquips() {
		return dbAllEquips;
	}
	
	public void setDbAllEquips(List<EquipPo> dbAllEquips) {
		this.dbAllEquips = dbAllEquips;
	}

}
