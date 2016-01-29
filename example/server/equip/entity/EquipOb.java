/**
 * 
 */
package server.equip.entity;

import ovar.Variable;
import server.equip.observer.EquipLevelUp;

/**
 * @author Xiong
 * 可观察对象Equip
 */
public class EquipOb {
	public Variable<Long> exp = new Variable<Long>();
	
	public EquipOb(long uid, long id){
		EquipCtx ctx = new EquipCtx();
		ctx.uid = uid;
		ctx.id = id;
		exp.init((long) 0).subscribe(new EquipLevelUp(ctx));
	}
}
