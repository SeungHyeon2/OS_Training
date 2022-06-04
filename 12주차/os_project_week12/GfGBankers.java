package os_project_week12;

public class GfGBankers {
	 	int n = 5; // 프로세스의 수
	    int m = 3; // 자원의 수
	    int need[][] = new int[n][m];
	    int [][]max;
	    int [][]alloc;
	    int []avail;
	    int safeSequence[] = new int[n];
	  
	    void initializeValues(){
	    // P0, P1, P2, P3, P4 프로세스
	    // Allocation 행렬
	    alloc = new int[][] { 
	    			  { 0, 1, 0 }, //P0   
	                  { 2, 0, 0 }, //P1
	                  { 3, 0, 2 }, //P2
	                  { 2, 1, 1 }, //P3
	                  { 0, 0, 2 } }; //P4
	            
	    // MAX 행렬
	    max = new int[][] { 
	    	     { 7, 5, 3 }, //P0
	             { 3, 2, 2 }, //P1
	             { 9, 0, 2 }, //P2
	             { 2, 2, 2 }, //P3 
	             { 4, 3, 3 } }; //P4
	      
	    // 가용 자원들
	     avail = new int[] { 3, 3, 2 }; 
	    }
	    void isSafe(){
	    	int count=0;
	    	//이미 할당된 프로세스를 찾기 위해 방문한 배열
	    	
	    	boolean visited[] = new boolean[n]; 
	    	for (int i = 0;i < n; i++){
	    		visited[i] = false;
	    		}
	    	
	    	//사용 가능한 리소스의 복사본을 저장하는 작업 배열
	    	int work[] = new int[m]; 
	    	for (int i = 0;i < m; i++){
	    		work[i] = avail[i];
	    	}
	    	
	    	while (count<n){
	    		boolean flag = false;
	    		for (int i = 0;i < n; i++){
	    			if (visited[i] == false){
	    				int j;
	    				for (j = 0;j < m; j++){        
	    					if (need[i][j] > work[j])
	    						break;
	    				}
	    			if (j == m){
	    				safeSequence[count++]=i;
	    				visited[i]=true;
	    				flag=true;         
	    				for (j = 0;j < m; j++){
	    					work[j] = work[j]+alloc[i][j];
	    				}}}}
	    		if (flag == false){
	    			break;}}
	    if (count < n){
	        System.out.println("The System is UnSafe!");
	    }
	    else{
	        System.out.println("The given System is Safe");
	        System.out.println("Following is the SAFE Sequence");
	        for (int i = 0;i < n; i++){
	        	System.out.print("P" + safeSequence[i]);
	            if (i != n-1)
	            System.out.print(" -> ");
	        }
	    }
	    }
	      
	    void calculateNeed()
	    {
	    for (int i = 0;i < n; i++)
	    {
	        for (int j = 0;j < m; j++)
	         {
	        need[i][j] = max[i][j]-alloc[i][j];
	         }
	    }        
	    }
	  
	    public static void main(String[] args)
	    {  
	      int i, j, k; 
	      GfGBankers gfg = new GfGBankers();
	           
	      gfg.initializeValues();    
	      //필요 행렬 계산
	      gfg.calculateNeed();            
	              
	      // 시스템이 안전한 상태인지 검사
	      gfg.isSafe();        
	    }
}
