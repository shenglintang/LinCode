

public class MyObj {
	Test main; // 记录Test对象，在finalize中时用于恢复可达�?

	public MyObj(Test t) {
		main = t; // 保存Test 对象
	}

	protected void finalize() {
		main.ref = this;// 恢复本对象，让本对象可达
		System.out.println("This is finalize");// 用于测试finalize只运行一�?
	}
}

class Test {

	MyObj ref;

	public static void main(String[] args) {
		Test test = new Test();
		test.ref = new MyObj(test);
		test.ref = null; // MyObject对象为不可达对象，finalize将被调用
		System.gc();
		if (test.ref != null) {
			System.out.println("My Object还活着");
		}
	}
}