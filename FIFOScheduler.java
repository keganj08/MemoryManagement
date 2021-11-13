public class FIFOScheduler {
    ArrayList<Process> runningprocesses;
    ArrayList<Process> readyqueue;
    Memory memory;
    int runtime;

    public FIFOScheduler(ArrayList<Process> processes, Memory m) {
        runningprocesses = new ArrayList<Process>();
        readyqueue = processes;
        runtime = 0;
        memory = m;
        Collections.sort(readyqueue);//sorts the queue into fifo order
    }

    private boolean done() {//To check if there are no more processes to finish
        boolean b = true;
        for(Process p : runningprocesses) {
            if(p.getremainingtime() > 0) {
                b = false;
            }
        }
        return b;
    }

    public void runprocesses(){//runs processes in memory using round robin.
        for(Process p : readyqueue){//loads processes into memory
            runProcess(p);
        }

        while(!done()){
            for (Process p : runningprocesses) {
                if(p.getarrivaltime() <= runtime){
                    System.out.println("Now running process - " + p.getid());
                    runtime += p.getremainingtime(); //Runs the process till it is finished executing
                    p.setremainingtime(0);
                    p.setexittime(runtime);
                    System.out.println("\tFinished process - " + p.getid() + " at t = " + runtime);
                    System.out.println(("\tTT: " + (p.getexittime() - p.getarrivaltime())));
                    System.out.println(("\tWT: " + ((p.getexittime() - p.getarrivaltime()) - p.getbursttime())));
                    memory.removeProcess(p);//removes process from memory
                    System.out.println("\tFreed Space: " + p.getSize());
                    for(Process r : readyqueue){//looks for processes that will fit into the now empty partition
                        runProcess(r);
                    }
                        System.out.println();
                }
                else{
                    runtime++; //if no processes have yet arrived the "clock" runs for another time unit
                }
            }
        }
    }
}
