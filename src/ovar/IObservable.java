/**
 * 
 */
package ovar;

/**
 * @author Xiong
 * 可观察对象
 */
public interface IObservable<T> {
	/**
	 * 添加一个观察者
	 * @param observer
	 */
	public void subscribe(IObserver<T> observer);
}
