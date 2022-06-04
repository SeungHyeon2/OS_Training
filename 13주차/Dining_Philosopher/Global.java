package Dining_Philosopher;

public class Global {
	static int num;
	static{
		num=5;
	}
	
	static int Left(int i){
		return (i-1+num)%num;
	}
	static int Right(int i){
		return (i+1)%num;
	}
}
