package reader_writer_new;

public class Monitor{ // 모니터 구현 클래스
	
	// main 메모리에 저장할 것을 명시
	private volatile int readers;
	private volatile boolean writing;
	private volatile Condition OK_to_Read, OK_to_Write;
	//readers 수, writing여부, 조건변수 생성

	public Monitor(){
		readers = 0;
		writing = false;
		OK_to_Read = new Condition();
		OK_to_Write = new Condition();
	}

	public synchronized void Start_Read(int n){

		System.out.println("wants to read " + n);
		if(writing || OK_to_Write.is_non_empty()){
			try{
				System.out.println("reader is waiting " + n);
				OK_to_Read.sleep_();
				//writing이 false이거나 조건변수가 비어있지 않을때 대기
			}
			catch(InterruptedException e){}
		}
		readers += 1;
		OK_to_Read.release_all();
		//writing이 true이고, 조건변수가 비어있을때 readers값 증가하고
		//thread들을 runnable롭 변경
	}

	public synchronized void End_Read(int n){

		System.out.println("finished reading " + n);
		readers -= 1;
		//readers 값 감소
		if(OK_to_Write.is_non_empty()){
			OK_to_Write.release_one();
			//write 조건변수 non empty시 notify
		}
		else if(OK_to_Read.is_non_empty()){
			OK_to_Read.release_one();
			//read 조건변수 non empty시 notify
		}
		else{
			OK_to_Write.release_all();
			//둘다 비어있으면 notifyAll()
		}
	}

	public synchronized void Start_Write(int n){
		System.out.println("wants to write " + n);
		if(readers != 0 || writing){//readers가 0이 아니거나 writing이 true이면
			try{
				System.out.println("Writer is waiting " + n);
				OK_to_Write.sleep_();//write 조건변수 대기
                	}catch(InterruptedException e){}
		}
		writing = true;
	}

	public synchronized void End_Write(int n){
		System.out.println("finished writing " + n);
		//writing false로 설정
		writing = false;
		if(OK_to_Read.is_non_empty()){
			OK_to_Read.release_one();
		}else if(OK_to_Write.is_non_empty()){
			OK_to_Write.release_one();
		}else{
			OK_to_Read.release_all();
		}
	}
}