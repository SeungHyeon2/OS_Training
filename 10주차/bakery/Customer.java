package bakery;

public class Customer extends Thread {
	boolean choosing[];
	int[] ticket;
	int threadCount;


	
	public Customer(boolean[] choosing, int[] ticket, String name) {
		super(name);
		this.choosing = choosing;
		this.ticket = ticket;
		//threadCount = threadNumber(ticket);
		//this.threadFinish = threadFinish;
	}

	@Override
	public void run() {
		
		threadCount = threadNumber(ticket);
		choosing[threadCount] = true;
		ticket[threadCount] = maxValue(ticket) + 1;		
		System.out.println("[" + getName() + "]" + " 티켓번호 부여 : " + ticket[threadCount]);
		choosing[threadCount] = false;
		
		
		
		for( int i = 0; i < ticket.length; i++) {
			//System.out.println(getName() + "의 for문" + i + "번 째 수행");
			if( i == threadCount ) {
				//System.out.println("[" + getName() + "]" + "[스레드 본인과 같음 continue]");
				continue;
			}
			
			while( choosing[i] != false ) {
				//System.out.println("[" + getName() + "]" + "티켓이 다 뽑힐때까지 대기");
			};
			
			while( ticket[i] != 0 && ticket[i] < ticket[threadCount]) {
				//System.out.println("[" + getName() + "]" + "가장 낮은 티켓값이 될 때까지 대기");
				
			};
			
			if( ticket[i] == ticket[threadCount] && i < threadCount ) {
				while( ticket[i] != 0) {
					//System.out.println("[" + getName() + "]" + "티켓 번호가 같아 더 작은 스레드가 수행될때까지 대기");
				};
			}
		}
			
			// 임계 영역 코드
//			System.out.println("[" + getName() + "]" + " ---임계영역 진입--- ");
//			System.out.println("[" + getName() + "]" + " 빵을 가져갔습니다");
//			System.out.println("[" + getName() + "]" + " ---티켓번호 초기화--- ");
//			for (int i = 0; i < 40; i++) {
//				System.out.println(getName() + i);
//			}

			ticket[threadCount] = 0;
			
//			threadFinish[0]--;
			
			//마지막 스레드 수행시 시간 측정
//			if(this.threadFinish[0] == 0) {
//				long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
//				long secDiffTime = (afterTime - beforeTime); //두 시간에 차 계산
//				System.err.println(" 소요시간(m) : " + secDiffTime);
//			}
			
			// 임계 영역 외부코드
			//System.out.println(getName() + " : 임계 영역 외부코드 수행" );
			
			
	}
	
	//현재 스레드 수 반환
		public int threadNumber(int[] ticket) {
			int count = 0;
			for( int i = 0; i < ticket.length; i++) {
				if( ticket[i] != 0) { count ++; }
			}
			//System.out.println("[" + getName() + "]" + " 현재 스레드 개수 : " + count);
			return count;
		}
		
		//티켓 최대값 반환
		public int maxValue(int[] ticket) {
			int max = 0;
			for (int i = 0; i < ticket.length; i++) {
				 if (ticket[i] > max && ticket[i] != 0) {
					 max = ticket[i];
				 }
			}
			//System.out.println("[" + getName() + "]" + " 현재 티켓 최소값 : " + max );
			return max;
		}
	
	

}
