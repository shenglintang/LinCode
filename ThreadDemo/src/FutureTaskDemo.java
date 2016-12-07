import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class FutureTaskDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Callable<String> callable = new Callable<String>() {

			@Override
			public String call() throws Exception {
				// TODO Auto-generated method stub
				System.out.println("Sleep start.");
				Thread.sleep(1000 * 2);
				System.out.println("Sleep end.");
				return "time=" + System.currentTimeMillis();
			}
		};

		try {
			// 直接使用Thread的方式执行
			FutureTask<String> ft = new FutureTask<>(callable);
			new Thread(ft).start();
			System.out.println("ft.get() = " + ft.get());
			
			
			FutureTask<String> ft2 = new FutureTask<>(callable);
			
			// 使用Executors来执行
			System.out.println("=========");
			Executors.newSingleThreadExecutor().submit(ft2);
			System.out.println("ft2.get() = " + ft2.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

	}

}
