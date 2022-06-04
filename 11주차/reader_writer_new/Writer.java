package reader_writer_new;

public class Writer extends Thread{
	private Monitor M;
	private int value;
	public Writer(String name, Monitor d){
		super(name);
		M = d;
	}

	public void run(){
		for(int j = 0; j < 5; j++){
				M.Start_Write(j);
				System.out.println("Writer "+getName()+" is writing data...");
				System.out.println("Writer is writing " + j);
				M.End_Write(j);
		}
	}
}