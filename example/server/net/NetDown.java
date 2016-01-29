/**
 * 
 */
package server.net;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import server.RequestContext;
import server.util.DataKey;

/**
 * @author Xiong
 * 下行消息父类
 */
public class NetDown {
	/** 操作结果状态码, 0成功，非0失败，代表错误代码*/
	private ErrorCode errc = ErrorCode.OK;
	/** 通用字段 */
	private long uid = -1;
	/** 发生变化的变量 */
	private Map<String, String> changedProps = new HashMap<String, String>();
	
	public NetDown(){
		RequestContext.put(DataKey.NET_DOWN.getKey(), this);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T returnError(ErrorCode errc){
		NetDown down = RequestContext.get(DataKey.NET_DOWN.getKey());
		assertTrue(down != null);
		down.setErrc(errc);
		return (T) down;
	}
	
	public void addProp(String key, String value){
		changedProps.put(key, value);
	}
	
	public String getProp(String key){
		return changedProps.get(key);
	}
	
	public Map<String, String> getProps(){
		return changedProps;
	}

	public ErrorCode getErrc() {
		return errc;
	}

	public void setErrc(ErrorCode errc) {
		this.errc = errc;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}
}
