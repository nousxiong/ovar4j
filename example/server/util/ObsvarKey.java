/**
 * 
 */
package server.util;

/**
 * @author Xiong
 *
 */
public enum ObsvarKey {
	USER("user"),
	PLAYER("player"),
	EQUIP("equip"),
	;
	
	private String keyPre;
	private ObsvarKey(String keyPre){
		this.keyPre = keyPre;
	}
	
	public String getKey(){
		return keyPre;
	}
}
