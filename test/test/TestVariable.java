/**
 * 
 */
package test;

import static org.junit.Assert.*;

import org.junit.Test;

import ovar.IObserver;
import ovar.Variable;

/**
 * @author Xiong
 *
 */
public class TestVariable {
	
	class IntegerObserver implements IObserver<Integer> {

		@Override
		public void onNext(Integer oldValue, Integer newValue) {
			System.out.println("oldValue: "+oldValue+", newValue: "+newValue);
		}
		
	}
	
	class StringObserver implements IObserver<String> {

		@Override
		public void onNext(String oldValue, String newValue) {
			System.out.println("oldValue: "+oldValue+", newValue: "+newValue);
		}
		
	}
	
	class MyData {
		public int i;
		public String str = "str";
		
		public MyData(int i, String str){
			this.i = i;
			this.str = str;
		}

		@Override
		public boolean equals(Object o){
			if (o == this){
				return true;
			}
			
			if (o == null || getClass() != o.getClass()){
				return false;
			}
			
			MyData rhs = (MyData)o;
			return this.i == rhs.i && this.str.equals(rhs.str);
		}
		
		@Override
		public String toString(){
			return i+"-"+str;
		}
	}
	
	class MyDataObserver implements IObserver<MyData> {

		@Override
		public void onNext(MyData oldValue, MyData newValue) {
			System.out.println("oldValue: "+oldValue+", newValue: "+newValue);
		}
		
	}

	@Test
	public void test() {
		Variable<Integer> intVar = new Variable<Integer>(1);
		intVar.subscribe(new IntegerObserver());
		intVar.set(1);
		assertTrue(intVar.get() == 1);
		intVar.set(2);
		assertTrue(intVar.get() == 2);
		intVar.set(3);
		assertTrue(intVar.get() == 3);
		intVar.set(4);
		assertTrue(intVar.get() == 4);
		
		Variable<String> strVar = new Variable<String>("one");
		strVar.subscribe(new StringObserver());
		strVar.set("one");
		assertTrue("one".equals(strVar.get()));
		strVar.set("two");
		assertTrue("two".equals(strVar.get()));
		strVar.set("three");
		assertTrue("three".equals(strVar.get()));
		strVar.set("four");
		assertTrue("four".equals(strVar.get()));
		
		Variable<MyData> myVar = new Variable<MyData>(new MyData(1, "one"));
		myVar.subscribe(new MyDataObserver());
		myVar.set(new MyData(2, "two"));
	}

}
