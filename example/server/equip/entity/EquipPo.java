/**
 * 
 */
package server.equip.entity;

/**
 * @author Xiong
 * 模拟数据库装备数据
 */
public class EquipPo {
	public long id; // primary 唯一实例id
	public long uid; // index
	public byte type; // 装备的类型，0~N，每个数字代表一种不同的装备
	public int level; // 装备等级
	public long exp; // 装备经验值，经验值积累越多，装备等级越高；消耗玩家金币转化为装备经验
	public long star; // 装备星级，必须花费代币才能升级，升级星级也会增加玩家经验值
}
