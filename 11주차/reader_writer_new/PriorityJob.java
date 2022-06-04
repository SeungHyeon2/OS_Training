package reader_writer_new;

public class PriorityJob {
    public int PID;
    public int remain;
    public int priority;
 
    public PriorityJob(int PID, int remain, int priority) {
        this.PID = PID;
        this.remain = remain;
        this.priority = priority;
    }
 
    @Override
    public String toString() {
        return "P" + String.valueOf(PID) + "(" + priority + ")";
    }
}