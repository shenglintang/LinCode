package threadTest;

public class NormalCode {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final Bank bank = new Bank();

		Thread tadd = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					bank.addMoney(100);
					bank.lookMoney();
					System.out.println("\n");

				}
			}
		});

		Thread tget = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					bank.getMoney(100);
					bank.lookMoney();
					System.out.println("\n");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		tget.start();
		tadd.start();
	}
}
