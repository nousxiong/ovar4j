/**
 * 
 */
package server.util;

/**
 * @author Xiong
 *
 */
public class DataUtil {
	/**
	 * 得到key
	 * @param key
	 * @param uid
	 * @return
	 */
	public static String getKey(DataKey key, long uid){
		return new StringBuilder(key.getKey()).append('#').append(uid).toString();
	}
	
	/**
	 * 得到key
	 * @param key
	 * @param name
	 * @return
	 */
	public static String getKey(DataKey key, String name){
		return new StringBuilder(key.getKey()).append('#').append(name).toString();
	}
}
