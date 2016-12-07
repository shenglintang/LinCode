package threadTest;


public class Bank {

	private static ThreadLocal<Integer> count = new ThreadLocal<Integer>() {

		@Override
		protected Integer initialValue() {
			// TODO Auto-generated method stub
			return 0;
		}

	};

	// ��Ǯ
	public void addMoney(int money) {
		count.set(count.get() + money);
		System.out.println(System.currentTimeMillis() + "�����" + money);

	}

	// ȡǮ
	public void getMoney(int money) {
		if (count.get() - money < 0) {
			System.out.println("����");
			return;
		}
		count.set(count.get() - money);
		System.out.println(+System.currentTimeMillis() + "ȡ����" + money);
	}

	// ��ѯ
	public void lookMoney() {
		System.out.println("�˻���" + count.get());
	}
}
