package paging;
import java.util.*;

public class PageTable {
    Map<Page, Frame> table;
    int pageSize;
    int numProcs;
    int memory;

    public PageTable(int pageSize, int memory) {
        this.table = new HashMap<Page, Frame>();
        this.pageSize = pageSize;
        this.numProcs = 0;
        this.memory = memory;
    }

    public void mapPage(Page page, Frame frame){
        this.table.put(page, frame);
    }

    public void mapProcessPages(Process proc) {
        this.numProcs ++;
        int procSize = proc.size;
        List<Frame> frames = new ArrayList<Frame>();
        while(this.table.size() * this.pageSize < this.memory &&  frames.size() * this.pageSize < procSize){
            Frame f = new Frame(this.pageSize);
            Page p = new Page(this.pageSize, this.numProcs+1);
            frames.add(f);
            this.mapPage(p, f);
        }
    }

    public void printTable(){
        for(Page key : this.table.keySet()) {
            System.out.println(key.procId);
        }
    }
}
