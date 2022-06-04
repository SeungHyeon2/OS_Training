package reader_writer_new;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PriorityScheduler {
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
                    while (job.remain > 0) {
                        if (!job.equals(readyQueue.get(0)))
                            break;
 
                        log(job);
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
        PriorityScheduler scheduler = new PriorityScheduler();
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