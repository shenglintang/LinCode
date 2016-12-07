import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 创建一个执行任务的服务
		ExecutorService service = Executors.newFixedThreadPool(3);
		try {
			// 1.Runnable通过Future返回结果
			// 创建一个Runnable，来调度，等待任务执行完毕，取得返回结果
			Future<?> runable = service.submit(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					System.out.println("runable is running");
				}
			});
			System.out.println("runable.get():" + runable.get());
			
			// 2.Callable通过Future能返回结果
			// 提交并执行任务，任务启动时返回了一个 Future对象，
			// 如果想得到任务执行的结果或者是异常可对这个Future对象进行操作
			Future<String> callable = service.submit(new Callable<String>() {

				@Override
				public String call() throws Exception {
					// TODO Auto-generated method stub
					System.out.println("callable is running");
					return "result is callable";
				}
			});
			System.out.println("callable.get():" + callable.get());
			// 3. 对Callable调用cancel可以对对该任务进行中断
			// 提交并执行任务，任务启动时返回了一个 Future对象，

			Future<String> callable2 = service.submit(new Callable<String>() {

				@Override
				public String call() throws Exception {
					// TODO Auto-generated method stub
					try {
						while (true) {
							System.out.println("callable2 is running.");
							Thread.sleep(50);
						}
					} catch (InterruptedException e) {
						System.out.println("Interrupted callable2.");
					}
					return "result is callable2";
				}
			});
			// 等待5秒后，再停止第二个任务。因为第二个任务进行的是无限循环
			Thread.sleep(10);
			System.out.println("callable2 cancle: " + callable2.cancel(true));

			// 4.用Callable时抛出异常则Future什么也取不到了
			// 获取第三个任务的输出，因为执行第三个任务会引起异常
			// 所以下面的语句将引起异常的抛出
			Future<String> callable3 = service.submit(new Callable<String>() {

				@Override
				public String call() throws Exception {
					throw new Exception("callable3 throw exception!");
				}

			});
			System.out.println("task3: " + callable3.get());

			// 停止任务执行服务
			service.shutdownNow();

		} catch (Exception e) {
			  System.out.println(e.toString());  
		}
	}

}
