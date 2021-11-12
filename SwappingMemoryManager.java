import java.util.*;


public class SwappingMemoryManager {
    int runtime;
    int timequantum;
    int contextswitchtime;
    int space;
    int usedspace;
    ArrayList<Process> runningprocesses;
    Queue<Process> readyqueue;

    SwappingMemoryManager(int s){
        space = s;
        runningprocesses = new ArrayList<Process>();
        readyqueue = new LinkedList<Process>();
        usedspace = 0;
        runtime = 0;
        contextswitchtime = 0;
        timequantum = 0;
    }

    public int getSpace(){//retuns the total amount of memory
        return space; 
    }
    public int getUsedSpace(){//returns the amount of used memory
        return usedspace;
    }
    
    public int getFreeSpace(){//returns the amount of free memory
        return (space - usedspace);
    }

    public void queueProcess(Process p){//adds a process to the backing store
        readyqueue.add(p);
    }

    public void runProcess(Process p){//adds a process to memory
        runningprocesses.add(p);
        usedspace += p.getsize();
    }

    public void removeProcess(Process p){//removes process from memory
        runningprocesses.remove(p);
        usedspace -= p.getsize();
    }


    public void swapProcess(Process in, Process out){
        if(!readyqueue.contains(in) || !runningprocesses.contains(out)){//makes sure processes are where theyre being called
            System.out.println("\nProcess " + in.getid() + " is not in the ready queue or Process " + out.getid() + " is not currently running\n");
            return;
        }
        if(getFreeSpace() + out.getsize() < in.getsize()){//makes sure there is enough memory space for a process
            System.out.println("\nNot enough free space for Process " + in.getid() + " after removing Process "+ out.getid() + "\n");
            return;
        }

        queueProcess(out);//puts the removed process into backing store
        removeProcess(out);//removes process from memory
        runProcess(in);//loads new process into memory

        System.out.println("\nSwapped process " + in.getid() + " into memory and placed process " + out.getid() + " into the backing store\n");
    }

    private boolean done() {//To check if there are no more processes to finish
        boolean b = true;
        for(Process p : runningprocesses) {
            if(p.getremainingtime() > 0) {
                b = false;
            }
        }

        for(Process p : readyqueue) {
            if(p.getremainingtime() > 0) {
                b = false;
            }
        }
        return b;
    }

    public void runprocesses(){//runs processes by going through each process and decrementing its remaining time by the time quantum.

        for(Process p : readyqueue){//loads processes into memory in FIFO order
            runProcess(p);
        }

        while(!done()){
            for (Process p : runningprocesses) {
                if(p.getarrivaltime() <= runtime && p.getremainingtime() > 0){
                    System.out.println("Now running process - " + p.getid());
                    runtime += contextswitchtime;
                    if(p.getremainingtime() <= timequantum){//if the time quantum is greater than the remaining time then we set the remaining time to zero and log the data
                        runtime += p.getremainingtime();
                        p.setremainingtime(0);
                        p.setexittime(runtime);
                        System.out.println("\tFinished process - " + p.getid() + " at t = " + runtime);
                        System.out.println(("\tTT: " + (p.getexittime() - p.getarrivaltime())));
                        System.out.println(("\tWT: " + ((p.getexittime() - p.getarrivaltime()) - p.getbursttime())));
                        removeProcess(p);//removes process if its done with execution
                        for(Process r : readyqueue){//loads any processes that will fit into the left memory space
                            runProcess(r);
                        }
                    } else{
                        p.run(timequantum); //if not we then run the process for the time quantum and add on the context switch time.
                        runtime += timequantum;
                        System.out.println("\tRan process - " + p.getid() + " for " + timequantum + "milliseconds");
                    }
                    System.out.println();
                }
            }
        }
    } 


    public static void main(String[] args) {
       


    }

}