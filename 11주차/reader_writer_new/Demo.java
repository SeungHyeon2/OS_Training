package reader_writer_new;


public class Demo {
	public static void main(String [] args){
	    Monitor M = new Monitor();
	    Reader reader = new Reader("1",M);
	    Writer writer = new Writer("1",M);
	    writer.start();
	    reader.start();     
	}
}
