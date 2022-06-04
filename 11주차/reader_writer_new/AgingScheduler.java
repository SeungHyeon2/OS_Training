package reader_writer_new;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AgingScheduler {
    private List<PriorityJob> readyQueue = new CopyOnWriteArrayList<>();
    private ExecutorService runningThread = Executors.newSingleThreadExecutor();
 
    void insert(PriorityJob job) {
        readyQueue.add(job);
        readyQueue.sort(Comparator.comparingInt(j -> j.priority));
        // 우선순위를 기준으로 정렬        
    }
 
    void interval(int n, int m) {
        for (int i = n; i < m + n; i++) {
            sleep(990); 
            // 오버헤드 고려해서 10msec 빠르게
            
            insert(new PriorityJob(i, 1, 5));
            if (i + 1 == m + n)
                System.exit(0);
        }
    }
    
    void start() {
        runningThread.execute(() -> {
            while (readyQueue.size() != 0) {
                for (PriorityJob job : readyQueue) {
                    int average = 0;
                    for (PriorityJob j : readyQueue) average += j.PID;
                    average /= readyQueue.size();
 
                    if (job.PID < average) {
                        job.priority--;
                        // Aging 수행
                        // PID가 평균보다 낮으면 (오래 되었으면)
                        // 우선순위를 깎아서 개선해줌.
                    }
 
                    while (job.remain > 0) {
                        if (!job.equals(readyQueue.get(0)))
                            break;
 
                        sleep(1000);
                        job.remain--;
 
                        if (job.remain == 0)
                            readyQueue.removeIf(j -> j.remain == 0);
                    }
                }
            }
 
            runningThread.shutdownNow();
            System.exit(0);
        });
    }
 
    public static void main(String[] args) {
        AgingScheduler scheduler = new AgingScheduler();
        scheduler.insert(new PriorityJob(1, 1, 30));
        scheduler.insert(new PriorityJob(2, 1, 5));
        scheduler.insert(new PriorityJob(3, 1, 5));
        
        scheduler.start();
        scheduler.interval(4, 55);
    }
 
    void log(PriorityJob job) {
        System.out.println(job + " is running " + readyQueue);
    }
 
    static void sleep(int msec) {
        try {
            TimeUnit.MILLISECONDS.sleep(msec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}