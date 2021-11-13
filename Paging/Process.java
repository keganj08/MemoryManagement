import java.util.Random;

public class Process {
    int size;
    int arrivaltime;
    int bursttime;
    Random rand = new Random();

    public Process() {
        size = rand.nextInt(100);
        arrivaltime = rand.nextInt(50);
        bursttime = rand.nextInt(50);
    }

    public int getSize() {return size;}

    public void setSize(int s){
        size = s;
    }

    public static void main(String args[]){
    }
}