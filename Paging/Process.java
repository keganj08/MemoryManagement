import java.util.Random;

public class Process {
    int size;
    Random rand = new Random();

    public Process() {
        size = rand.nextInt(100);
    }

    public int getSize() {return size;}

    public void setSize(int s){
        size = s;
    }

    public static void main(String args[]){
    }
}