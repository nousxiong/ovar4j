/**
 * 
 */
package server;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xiong
 * 模拟配置数据
 */
public class ConfigData {
	public static final long initCoin = 100;
	public static final byte initLimit = 3;
	public static final List<Byte> initEquipTypes = new ArrayList<Byte>(2);
	static{
		initEquipTypes.add((byte) 0);
		initEquipTypes.add((byte) 1);
	}
	public static final long questCoin = 25;
	public static final long questExp = 7;
	public static final int expPerLevel = 10;
	public static final int expPerEquipLevel = 10;
	public static final int chargePerVipLevel = 50;
}
