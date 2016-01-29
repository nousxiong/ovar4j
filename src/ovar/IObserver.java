/**
 * 
 */
package ovar;

/**
 * @author Xiong
 * 观察者
 */
public interface IObserver<T> {
	/**
	 * 通知观察者新值被设置
	 * @param oldValue
	 * @param newValue
	 */
	public void onNext(T oldValue, T newValue);
}
