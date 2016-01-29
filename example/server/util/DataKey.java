/**
 * 
 */
package server.util;

/**
 * @author Xiong
 *
 */
public enum DataKey {
	USER("USER"),
	PLAYER("PLAYER"),
	ALL_EQUIPS("ALL_EQUIPS"),
	NET_DOWN("NET_DOWN"),
	;
	
	private String keyPre;
	private DataKey(String keyPre){
		this.keyPre = keyPre;
	}
	
	public String getKey(){
		return keyPre;
	}
}
