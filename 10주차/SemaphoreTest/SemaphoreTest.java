package SemaphoreTest;

import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {
    public static int a, sum = 0;
    public static long startTime;


    public static void main(String[] args) {
        final SomeResource resource = new SomeResource(3);
        Thread t;

        Scanner sc = new Scanner(System.in);

        System.out.print("스레드 개수를 입력하세요 : ");
        a = sc.nextInt(); // 정수를 입력받음

        // 시작 시간 설정
        startTime = System.currentTimeMillis();
        for(int i = 1 ; i <= a ; i++) {
            t = new Thread(new Runnable() {
                public void run() {
                    resource.use();
                    //System.out.println(SemaphoreTest.sum);
                }
            });
            t.start();
        }

    }
}


class SomeResource {

    private final Semaphore semaphore;
    private final int maxThread;

    public SomeResource(int maxThread) {
        this.maxThread = maxThread;
        this.semaphore = new Semaphore(maxThread);
    }

    public void use() {
        try {
            semaphore.acquire(); // Thread 가 semaphore에게 시작을 알림 (P연산)

//            System.out.println("[" + Thread.currentThread().getName() + "]"
 //                   + (maxThread - semaphore.availablePermits()) + "쓰레드가 점유중" );
//            semaphore.availablePermits(); //사용가능한 Thread의 숫자

            SemaphoreTest.sum += 1; // 임계 영역 실행

            semaphore.release(); // Thread 가 semaphore에게 종료를 알림 (V연산)

            if (SemaphoreTest.sum == SemaphoreTest.a) {
                System.out.println("finish");
                // 종료 시간 설정
                System.out.println("실행 시간 : " + (System.currentTimeMillis()-SemaphoreTest.startTime));
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
