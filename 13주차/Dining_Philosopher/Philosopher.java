package Dining_Philosopher;

public class Philosopher extends Thread{
	Monitor M;
	int ID;
	public Philosopher(Monitor M, int ID){
		this.M=M;
		this.ID=ID;
	}
	
	public void run(){
		while(true){
			M.go(ID);
		}
	}
}
