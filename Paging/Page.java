package paging;

public class Page {
    int size;
    int procId;

    public Page(int frameSize, int procId) {
        this.size = frameSize;
        this.procId = procId;
    }
}
