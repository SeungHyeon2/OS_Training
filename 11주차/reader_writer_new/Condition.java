package reader_writer_new;

public class Condition{ //조건변수 클래스
	
	private int number; //대기중인 작성자/판독자 수를 지정

	public Condition(){ //생성자
		number = 0; 
	}

	public synchronized boolean is_non_empty()  { //공유 메소드 is_non_empty
		if(number == 0)  // number가 0이면 false, 아니면 true
			return false; 
		else
			return true;
	}

	public synchronized void release_all(){ 
		number = 0;
		notifyAll(); // WAIT_SET에 있는 모든 Thread를 RUNNABLE 상태로 변경
	}


	public synchronized void release_one(){ 
		number -=1;
		notify(); // WAIT_SET에 있는 임의의 한 개의 Thread를 다시 Runnable로 변경
	}   

	public synchronized void wait_() throws InterruptedException{  
		number++;
		wait(); // 락을 가지고 들어온 스레드를 wait()이 호출된 곳에서 락을 해제하고 잠들게한다.
	}
	
	public synchronized void sleep_() throws InterruptedException{  
		Thread.sleep(1000);
	}
}