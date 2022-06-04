package test7;

public class Detection {

	private static int n = 5,m = 3;
	private static int alloc[][] = new int[n][m];
	private static int req[][] = new int[n][m];
	private static int avail[] = new int[m];
	private static Boolean finish[] = new Boolean[n];
	private static Boolean terminated[] = new Boolean[n];
	private static String seq="";
	
	public static void fill(){
		alloc[0][0] = 0;
		alloc[0][1] = 1;
		alloc[0][2] = 0;
		
		alloc[1][0] = 2;
		alloc[1][1] = 0;
		alloc[1][2] = 0;
		
		alloc[2][0] = 3;
		alloc[2][1] = 0;
		alloc[2][2] = 3;
		
		alloc[3][0] = 2;
		alloc[3][1] = 1;
		alloc[3][2] = 1;
		
		alloc[4][0] = 0;
		alloc[4][1] = 0;
		alloc[4][2] = 2;
		
		
		req[0][0] = 0;
		req[0][1] = 0;
		req[0][2] = 0;
		
		req[1][0] = 2;
		req[1][1] = 0;
		req[1][2] = 2;
		
		req[2][0] = 0;
		req[2][1] = 0;
		req[2][2] = 1;
		
		req[3][0] = 1;
		req[3][1] = 0;
		req[3][2] = 1;
		
		req[4][0] = 0;
		req[4][1] = 0;
		req[4][2] = 2;
		
		avail[0]=0;
		avail[1]=0;
		avail[2]=0;
		
		for(int i=0;i<n;i++)
			finish[i] = false;
		
		for(int i=0;i<n;i++)
			terminated[i] = false;
	}
	
	
	public static void algo(){
		Boolean thereIsaAChange;
		do{
			thereIsaAChange = false;
			for(int i=0;i<n;i++){
				if(!finish[i] && isSmaller(req[i],avail)) { 
					// 프로세스 i의 요청이 가용자원보다 작거나 클때의 조건문
					for(int k=0;k<m;k++) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						avail[k] += alloc[i][k];
					}
					finish[i] = true;
					seq += "P"+i+", ";
					thereIsaAChange = true;
				}
			}
		}while(thereIsaAChange);
	}
	
	
	public static void deadLockRecovery(){
		if(isFinished()){
			System.out.println("안전합니다!");
			System.out.println(seq);
		}
		else{
			System.out.println("데드락이 발생하였습니다!");
			System.out.println("데드락으로부터 회복하려고 노력중입니다!!");
			
			while(!isFinished()){
				if(!release()) // 선정할 희생 프로세스가 없을 경우
					break;
				algo();
			}
			if(!isFinished()) 
				System.out.println("데드락 상태에서 회복하지 못했습니다 - 데드락 상태...");
			
			else 
				System.out.println("데드락 상태를 회복했습니다!!! - 안전 상태!");
			System.out.println(seq);
		}
	}
	
	private static Boolean release(){
		int vic = getMin();
		if(vic == -1) // min을 구할수가 없을떄(모든 프로세스가 이미 제거되었을때)
			return false;
		
		System.out.println("제거된 프로세스 : P"+vic);
		for(int i = 0; i<m;i++){
			avail[i] += alloc[vic][i];
			req[vic][i] += alloc[vic][i];
			alloc[vic][i] = 0;
			terminated[vic] = true;
		}
		return true;
	}
	
	private static int getMin(){
		int min = (int) 1e9;
		int minIndex = -1;
		int temp;
		for(int i=0;i<n;i++){
			if(!finish[i] && !terminated[i] ){
				temp = sum(alloc[i]);
				if(temp < min){
					min = temp;
					minIndex = i;
				}
			}
		}
		return minIndex;
	}
	
	private static Boolean isFinished(){
		for(int i=0;i<n;i++)
			if(finish[i] == false)
				return false;
		return true;
	}
	
	private static Boolean isSmaller(int arr1[], int arr2[]){
		for(int i=0;i<arr1.length;i++)
			if(arr1[i] > arr2[i])
				return false;
		return true;
	}
	
	
	
	private static int sum(int arr[]){
		int sum=0;
		for(int i=0;i<arr.length;i++)
			sum += arr[i];
		return sum;
	}
	
	
	public static void main(String[] args){
		fill();
		long beforeTime = System.currentTimeMillis();
		algo();
		deadLockRecovery();
		long afterTime = System.currentTimeMillis();
		long secDiffTime = (afterTime - beforeTime)/1000;
		System.out.println("시간차이(m) : "+secDiffTime);
	}
}