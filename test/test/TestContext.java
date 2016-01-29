/**
 * 
 */
package test;

import static org.junit.Assert.*;

import org.junit.Test;

import ovar.AbstractObserver;
import ovar.Variable;

/**
 * @author Xiong
 * 测试带有上下文的观察者
 */
public class TestContext {
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

	class Context {
		public String str;
	}
	
	class IntegerObserver extends AbstractObserver<Integer, Context> {
		public IntegerObserver(Context ctx) {
			super(ctx);
		}

		@Override
		public void onNext(Context ctx, Integer oldValue, Integer newValue) {
			assertTrue("int".equals(ctx.str));
			System.out.println("oldValue: "+oldValue+", newValue: "+newValue);
		}
	}
	
	class StringObserver extends AbstractObserver<String, Context> {
		public StringObserver(Context ctx) {
			super(ctx);
		}

		@Override
		public void onNext(Context ctx, String oldValue, String newValue) {
			assertTrue("str".equals(ctx.str));
			System.out.println("oldValue: "+oldValue+", newValue: "+newValue);
		}
	}
	
	class MyDataObserver extends AbstractObserver<MyData, Context> {
		public MyDataObserver(Context ctx) {
			super(ctx);
		}

		@Override
		public void onNext(Context ctx, MyData oldValue, MyData newValue) {
			assertTrue("my".equals(ctx.str));
		}
	}
	
	@Test
	public void test() {
		Context ctx = new Context();
		Variable<Integer> intVar = new Variable<Integer>();
		Variable<String> strVar = new Variable<String>();
		Variable<MyData> myVar = new Variable<MyData>();
		
		intVar.init(1).subscribe(new IntegerObserver(ctx));
		strVar.init("one").subscribe(new StringObserver(ctx));
		myVar.init(new MyData(1, "one")).subscribe(new MyDataObserver(ctx));
		
		ctx.str = "int";
		intVar.set(1);
		assertTrue(intVar.get() == 1);
		intVar.set(2);
		assertTrue(intVar.get() == 2);
		intVar.set(3);
		assertTrue(intVar.get() == 3);
		intVar.set(4);
		assertTrue(intVar.get() == 4);

		ctx.str = "str";
		strVar.set("one");
		assertTrue("one".equals(strVar.get()));
		strVar.set("two");
		assertTrue("two".equals(strVar.get()));
		strVar.set("three");
		assertTrue("three".equals(strVar.get()));
		strVar.set("four");
		assertTrue("four".equals(strVar.get()));
		
		ctx.str = "my";
		myVar.set(new MyData(2, "two"));
	}

}
