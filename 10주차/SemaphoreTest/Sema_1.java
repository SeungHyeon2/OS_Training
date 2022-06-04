package SemaphoreTest;

import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Sema_1 {
	public static int value, sum = 0;
	public static long startTime;
	
	public static void main(String[] args) {
		final Work work = new Work(3);
		Thread t;
		
		Scanner sc = new Scanner(System.in);
		System.out.println("스레드 개수를 입력하세요 : ");
		
		value = sc.nextInt();
		startTime = System.currentTimeMillis();
		for(int i=1; i<=value; i++) {
			t = new Thread(new Runnable() {
				public void run() {
					work.use();
				}
			});
			t.start();
		}
	}
}

class Work{
	private Semaphore semaphore;
	private int maxT;
	
	public Work(int maxT) {
		this.maxT = maxT;
		this.semaphore = new Semaphore(maxT);
	}
	
	public void use() {
		try{
			semaphore.acquire();
			Sema_1.sum += 1;
			
			semaphore.release();
			
			if(Sema_1.sum==Sema_1.value) {
				System.out.println("걸린 시간 : " + (System.currentTimeMillis() - Sema_1.startTime));
			}
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
}