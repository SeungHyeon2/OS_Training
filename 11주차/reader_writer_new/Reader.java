package reader_writer_new;

public class Reader extends Thread{
	private Monitor M;
	private String value;
	public Reader(String name,Monitor c){
		super(name);
		M=c;
	}

	public void run(){
		for(int i = 0; i < 5; i++){
				M.Start_Read(i);
				System.out.println("Reader "+getName()+" is retreiving data...");
				System.out.println("Reader is reading " + i);
				M.End_Read(i);
		}
	}
}