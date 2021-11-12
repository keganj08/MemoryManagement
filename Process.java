
public class Process implements Comparable<Process> {
    int bursttime;
    int arrivaltime;
    int remainingtime;
    int exittime;
    int pid;
    int size;
    int priority;
    int physicaladdress;

    public Process(int bt, int at, int id, int s){
        bursttime = bt;
        remainingtime = bt;
        arrivaltime = at;
        pid = id;
        size = s;
    }

    public int getbursttime(){
        return bursttime;
    }

    public int getarrivaltime(){
        return arrivaltime;
    }

    public int getremainingtime(){
        return remainingtime;
    }

    public int getexittime(){
        return exittime;
    }

    public int getid(){
        return pid;
    }

    public int getsize(){
        return size;
    }

    public int getPriority(){
        return priority;
    }

    public int getPhysicalAddress(){
        return physicaladdress;
    }

    public void run(int time){
        remainingtime -= time;
    }

    public void setremainingtime(int time){
        remainingtime = time;
    }

    public void setexittime(int time){
        exittime = time;
    }
    
    public void setPhysicalAddress(int address){
        physicaladdress = address;
    }

    public int compareTo(Process p) { //comparison class to sort processes by shortest remaining time
        if(p.getremainingtime() > remainingtime){
            return -1;
        }
        else if(p.getremainingtime() < remainingtime){
            return 1;
        }
        else{
           return 0; 
        } 
    }
}