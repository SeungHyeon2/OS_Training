package Monitor;

import java.util.Scanner;

public class Monitor3 {
	private static Object object = new Object();
    private static int val1 = 0;
    private static int a = 0;

    public static void addsafe1() {
    	synchronized(object) {
    		val1 = val1 + 1;
    	}
    }
//    synchronized public static void addsafe1() {
//        val1 = val1 + 1;
//        //System.out.println("val1 : " + val1);
//    }

    public static void main(String[] args) throws InterruptedException {
       Scanner scan = new Scanner(System.in);
       System.out.println("스레드 개수 입력 : ");
       a = scan.nextInt();
       
       long beforetime = System.currentTimeMillis();
       
        Runnable test1 = () -> {
            //for (int i = 0; i < a; i++)
                addsafe1();
        };
        
        Thread[] add1 = new Thread[a];

        for (int i = 0; i < a; i++)
        {
            add1[i] = new Thread(test1);
            add1[i].start();
        }
        for (int i = 0; i < a; i++)
        {
            add1[i].join();
        }

        long aftertime = System.currentTimeMillis();
        long time = aftertime - beforetime;
        System.err.println("실행 시간:"+ time);
    }

}
