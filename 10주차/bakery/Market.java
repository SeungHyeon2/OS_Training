package bakery;

import java.util.Scanner;

public class Market {
	public static void main(String[] args) {

		System.out.println("베이커리 알고리즘 시작");
		Scanner sc = new Scanner(System.in);
		System.out.print("프로그램에 참여 할 스레드의 개수 입력 : ");
		int num = sc.nextInt();
		sc.close();
		
//		int num = 300;
		int[] threadFinish = {num};
		boolean choosing[] = new boolean[num];
		int ticket[] = new int[num];
		long beforeTime = System.currentTimeMillis(); //시작시간 측정
		for( int i = 0; i < ticket.length; i++) {
			Customer customer = new Customer(choosing, ticket, i+" thread");
			customer.start();
			try{
				customer.join(); // main 스레드가 다른 스레드들을 기다리게 함
				//Thread.sleep(1000);
			}catch(Exception e) {

			}
		} // for문 종료

		long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
		long secDiffTime = (afterTime - beforeTime); //두 시간에 차 계산
		System.out.println("[main 스레드 종료]");
		System.err.println("소요시간(m) : " + secDiffTime);


		
	}


}
