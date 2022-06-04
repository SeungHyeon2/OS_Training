package Dining_Philosopher;

public class Main {
	public static void main(String args[]){
		// 철학자는 5명으로 가정.
		Monitor M=new Monitor(Global.num);
		Philosopher P[]=new Philosopher[Global.num];
		
		for(int i=0; i<Global.num; i++){
			P[i]=new Philosopher(M, i);
		}
		for(int i=0; i<Global.num; i++){
			P[i].start();
		}
		
		try {
			for(int i=0; i<Global.num; i++){
				P[i].join();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
