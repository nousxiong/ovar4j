/**
 * 
 */
package ovar;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xiong
 * 可观察变量
 */
public class Variable<T> implements IObservable<T> {
	/** 存储的值 */
	private T value;
	private T oldValue;
	private T newValue;
	/** 观察者列表 */
	private List<IObserver<T>> observers = new ArrayList<IObserver<T>>(1);

	/**
	 * 构造一个空的Variable
	 */
	public Variable(){
	}
	
	/**
	 * 构造并赋初值
	 * @param value
	 */
	public Variable(T value) {
		init(value);
	}
	
	@Override
	public void subscribe(IObserver<T> observer) {
		observers.add(observer);
	}

	/**
	 * 初始化值
	 * @param value
	 * @return
	 */
	public Variable<T> init(T value){
		this.value = value;
		return this;
	}
	
	/**
	 * 取得值
	 * @return
	 */
	public T get(){
		return value;
	}
	
	/**
	 * 设置值，如果和旧值不同，则会触发观察者回调
	 * @param value
	 * @return
	 */
	public Variable<T> set(T value){
		this.oldValue = this.value;
		this.value = value;
		this.newValue = value;
		if (!this.oldValue.equals(this.newValue)){
			for (IObserver<T> observer : observers){
				observer.onNext(this.oldValue, this.newValue);
			}
		}
		return this;
	}
}
