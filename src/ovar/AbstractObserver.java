/**
 * 
 */
package ovar;

/**
 * @author Xiong
 * 带有上下文对象的观察者
 */
public abstract class AbstractObserver<T, C> implements IObserver<T> {
	/** 上下文 */
	private C ctx;
	
	/**
	 * 构造观察者，并设置上下文
	 * @param ob
	 */
	public AbstractObserver(C ctx){
		this.ctx = ctx;
	}
	
	public abstract void onNext(C ctx, T oldValue, T newValue);
	
	/**
	 * 观察者回调
	 */
	@Override
	public final void onNext(T oldValue, T newValue){
		onNext(ctx, oldValue, newValue);
	}
}
