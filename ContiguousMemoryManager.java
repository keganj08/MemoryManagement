import java.util.*;


public class ContiguousMemoryManager extends Memory{
    int runtime;
    int contextswitchtime;
    int usedspace;
    int[] addresses;

    ContiguousMemoryManager(int s){
        addresses = new int[s];
        runningprocesses = new ArrayList<Process>();
        readyqueue = new ArrayList<Process>();
        usedspace = 0;
        runtime = 0;
        contextswitchtime = 0;
        timequantum = 0;
    }

    public int getSpace(){//retuns the total amount of memory
        return addresses.length; 
    }
    public int getUsedSpace(){//returns the amount of used memory
        return usedspace;
    }
    public int getFreeSpace(){//returns the amount of free memory
        return (addresses.length - usedspace);
    }

    public int findFirstFit(Process p){//Loops through array and finds the first partition that will hold the process
        int size = p.getsize();
        
        if(size >= addresses.length){
            return -2;
        }

        int temp = 0;
        int count = 0;
        for(int i = 0; i < addresses.length; i++){
            if(addresses[i] == 0){
                count++;
                if(count == size){
                    System.out.println("\nFound the first fitting partition for process "+ p.getid());
                    System.out.println("Internal Fragmentation: " + p.size/count + "\n");
                    return temp;
                }
            }
            else{
                count = 0;
                temp = i;
            }
            
        }
        

        return -1;
    }

    public int findBestFit(Process p){//loops through array and returns the start of the best fitting partition
        int size = p.getsize();
        if(size >= addresses.length){
            return -2;
        }
    
        int temp = -1;
        int count = 0;
        int cmax = Integer.MAX_VALUE;
        for(int i = 0; i < addresses.length; i++){
            if(addresses[i] == 0){
                count++;
            }
            else{
                if(count >= size){
                    cmax = Math.min(count, cmax);
                    temp = i-count;
                }
                count = 0;
            }
            
        }
        if(temp >=0){
            System.out.println("\nFound the best fitting partition for process "+ p.getid());
            System.out.println("Internal Fragmentation: " + p.size/count + "\n");
        }
    
        return temp;
    }
    
    public int findWorstFit(Process p){//loops through array and returns the start of the largest partition
        int size = p.getsize();
        if(size >= addresses.length){
            return -2;
        }
    
        int temp = -1;
        int count = 0;
        int cmax = Integer.MIN_VALUE;
        for(int i = 0; i < addresses.length; i++){
            if(addresses[i] == 0){
                count++;
            }
            else{
                if(count >= size){
                    cmax = Math.max(count, cmax);
                    temp = i-count;
                }
                count = 0;
            }
            
        }
        if(temp >=0){
            System.out.println("\nFound the worst fitting partition for process "+ p.getid());
            System.out.println("Internal Fragmentation: " + p.size/count + "\n");
        }
    
        return temp;
    }

    public void occupy(int size, int address){//Sets addresses in memory to occupied
        for(int i = address; i < size; i++){
            addresses[i] = 1;
        }
    }

    public void clear(int size, int address){//Clears occupied slots in memory
        for(int i = address; i < size; i++){
            addresses[i] = 0;
        }
    }


    public void queueProcess(Process p){//adds process to queue
        readyqueue.add(p);
    }

    public boolean runProcess(Process p){//Loads processes into memory
        runningprocesses.add(p);
        readyqueue.remove(p);
        int address = findWorstFit(p);

        if(address == -2){//checks if there is even enough memory space
            System.out.println("\nNot enough memory to run process " + p.getid() + "\n");
            return false;
        }
        else if(address == -1 && p.size <= getFreeSpace()){//checks for external fragmentation
            System.out.println("\nDue to external fragmentation there is no contiguous memory space large enough to handle process " + p.getid() + "\n");
            return false;
        }
        p.setPhysicalAddress(address);
        occupy(p.getsize(), address);
        usedspace += p.size;
        System.out.println("\nLoaded process " + p.getid() + " into memory\n");
        return true;
    }

    public void removeProcess(Process p){//removes process from memory and clears its physical address
        runningprocesses.remove(p);
        int address = p.getPhysicalAddress();
        clear(p.getsize(), address);
        usedspace -= p.getsize();
    }


    // private boolean done() {//To check if there are no more processes to finish
    //     boolean b = true;
    //     for(Process p : runningprocesses) {
    //         if(p.getremainingtime() > 0) {
    //             b = false;
    //         }
    //     }
    //     return b;
    // }

    // public void runprocesses(){//runs processes in memory using round robin.
    //     Collections.sort(readyqueue);//sorts the queue into fifo order
    //     for(Process p : readyqueue){//loads processes into memory
    //         runProcess(p);
    //     }

    //     while(!done()){
    //         for (Process p : runningprocesses) {
    //             if(p.getarrivaltime() <= runtime){
    //                 System.out.println("Now running process - " + p.getid());
    //                 runtime += p.getremainingtime(); //Runs the process till it is finished executing
    //                 p.setremainingtime(0);
    //                 p.setexittime(runtime);
    //                 System.out.println("\tFinished process - " + p.getid() + " at t = " + runtime);
    //                 System.out.println(("\tTT: " + (p.getexittime() - p.getarrivaltime())));
    //                 System.out.println(("\tWT: " + ((p.getexittime() - p.getarrivaltime()) - p.getbursttime())));
    //                 removeProcess(p);//removes process from memory
    //                 System.out.println("\tFreed Space: " + p.getSize());
    //                 for(Process r : readyqueue){//looks for processes that will fit into the now empty partition
    //                     runProcess(r);
    //                 }
    //                     System.out.println();
    //             }
    //             else{
    //                 runtime++; //if no processes have yet arrived the "clock" runs for another time unit
    //             }
    //         }
    //     }
    // }
    


    public static void main(String[] args) {
       ContiguousMemoryManager mmu = new ContiguousMemoryManager(100);
       mmu.queueProcess(new Process());


    }

}