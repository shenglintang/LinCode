import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// ����һ��ִ������ķ���
		ExecutorService service = Executors.newFixedThreadPool(3);
		try {
			// 1.Runnableͨ��Future���ؽ��
			// ����һ��Runnable�������ȣ��ȴ�����ִ����ϣ�ȡ�÷��ؽ��
			Future<?> runable = service.submit(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					System.out.println("runable is running");
				}
			});
			System.out.println("runable.get():" + runable.get());
			
			// 2.Callableͨ��Future�ܷ��ؽ��
			// �ύ��ִ��������������ʱ������һ�� Future����
			// �����õ�����ִ�еĽ���������쳣�ɶ����Future������в���
			Future<String> callable = service.submit(new Callable<String>() {

				@Override
				public String call() throws Exception {
					// TODO Auto-generated method stub
					System.out.println("callable is running");
					return "result is callable";
				}
			});
			System.out.println("callable.get():" + callable.get());
			// 3. ��Callable����cancel���ԶԶԸ���������ж�
			// �ύ��ִ��������������ʱ������һ�� Future����

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
			// �ȴ�5�����ֹͣ�ڶ���������Ϊ�ڶ���������е�������ѭ��
			Thread.sleep(10);
			System.out.println("callable2 cancle: " + callable2.cancel(true));

			// 4.��Callableʱ�׳��쳣��FutureʲôҲȡ������
			// ��ȡ������������������Ϊִ�е���������������쳣
			// �����������佫�����쳣���׳�
			Future<String> callable3 = service.submit(new Callable<String>() {

				@Override
				public String call() throws Exception {
					throw new Exception("callable3 throw exception!");
				}

			});
			System.out.println("task3: " + callable3.get());

			// ֹͣ����ִ�з���
			service.shutdownNow();

		} catch (Exception e) {
			  System.out.println(e.toString());  
		}
	}

}
