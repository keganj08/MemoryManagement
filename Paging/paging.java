package paging;
import java.util.*;

public class paging {

    public static void main(String args[]){
        PageTable pt = new PageTable(10, 300);

        Random rand = new Random();
        for(int i=0; i<rand.nextInt(20); i++){
            Process p = new Process();
            pt.mapProcessPages(p);
        }

        pt.printTable();
    }
}