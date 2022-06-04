package Dining_Philosopher;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {
	private final ReentrantLock entLock;
	private final Condition self[];
	private int state[];
	
	//		num : 철학자가 몇명인지...
	public Monitor(int num){
		entLock=new ReentrantLock();
		self=new Condition[num];
		state=new int[num];
		
		for(int i=0; i<num; i++){
			self[i]=entLock.newCondition();
			state[i]=STATE.THINKING;
		}
	}
	
	//		who : 철학자의 ID.	
	void go(int who){
		try {
			//		냠냠.
			pickup(who);
			putdown(who);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void pickup(int who) throws InterruptedException {
		entLock.lock();
		state[who]=STATE.HUNGRY;
		System.out.println("Philosopher " + who + " is hungry.\n");
		Test(who);
		
		//		내 양쪽의 포크를 한 번에 집을 수 없었기 때문에
		//		나는 스파게티를 먹을 수 없다. 그러므로 대기 상태로 전환.
		if(state[who]!=STATE.EATING)
			self[who].await();
		
		entLock.unlock();
	}
	
	void Test(int who){
		if(state[Global.Left(who)]!=STATE.EATING &&
			state[Global.Right(who)]!=STATE.EATING &&
			state[who]==STATE.HUNGRY){
			state[who]=STATE.EATING;
			System.out.println("Philosopher " + who + " is eating.\n");
			
			self[who].signal();
		}
	}
	
	void putdown(int who){
		entLock.lock();
		state[who]=STATE.THINKING;
		System.out.println("Philosopher " + who + " is thinking.\n");
		
		Test(Global.Left(who));
		Test(Global.Right(who));
		
		entLock.unlock();
	}
}
