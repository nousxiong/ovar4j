/**
 * 
 */
package server;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Xiong
 * 用户请求上下文
 */
public class RequestContext {
	
	/**
	 * 往线程变量存放一个对象
	 * @param key
	 * @param obj
	 */
	public static <T> void put(String key, T obj){
		getThreadLocalParam().put(key, obj);
	}
	
	/**
	 * 从线程变量获取一个已有对象.
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T get(String key){
		return (T) getThreadLocalParam().get(key);
	}
	
	/**
	 * 清除所有的缓存
	 */
	public static void clear(){
		getThreadLocalParam().clear();
	}
	
	private static final ThreadLocal<Map<String, Object>> actionContext;
	static{
		actionContext = new ThreadLocal<Map<String, Object>>();
	}
	
	private static Map<String, Object> getThreadLocalParam(){
		Map<String, Object> paramInThreadLocal = actionContext.get();
		if (paramInThreadLocal == null){
			paramInThreadLocal = new HashMap<String, Object>();
			actionContext.set(paramInThreadLocal);
		}
		return paramInThreadLocal;
	}
}
