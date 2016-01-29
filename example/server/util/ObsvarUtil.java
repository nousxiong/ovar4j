/**
 * 
 */
package server.util;

/**
 * @author Xiong
 *
 */
public class ObsvarUtil {
	/**
	 * 得到key
	 * @param key
	 * @param uid
	 * @return
	 */
	public static String getKey(ObsvarKey key, long uid){
		return new StringBuilder(key.getKey()).append('#').append(uid).toString();
	}
	
	/**
	 * 得到key
	 * @param key
	 * @param uid
	 * @param id
	 * @return
	 */
	public static String getKey(ObsvarKey key, long uid, long id){
		return new StringBuilder(key.getKey()).append('#').append(uid).append("#").append(id).toString();
	}
}
