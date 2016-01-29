/**
 * 
 */
package server.player.entity;

import ovar.Variable;
import server.player.observer.PlayerLevelUp;
import server.player.observer.QuestReward;
import server.player.observer.VipLevelUp;
import server.player.observer.VipPrivilege;

/**
 * @author Xiong
 * 可观察对象Player
 */
public class PlayerOb {
	public Variable<Long> exp = new Variable<Long>();
	public Variable<Long> charge = new Variable<Long>();
	public Variable<Integer> vip = new Variable<Integer>();
	public Variable<Integer> quest = new Variable<Integer>();
	
	public PlayerOb(long uid){
		exp.init((long) 0).subscribe(new PlayerLevelUp(uid));
		charge.init((long) 0).subscribe(new VipLevelUp(uid));
		vip.init(0).subscribe(new VipPrivilege(uid));
		quest.init(0).subscribe(new QuestReward(uid));
	}
}
