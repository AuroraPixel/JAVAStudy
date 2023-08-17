package TimeComplexity.Demo;

import java.util.ArrayList;
import java.util.List;

public class CQueue {
    private List<Integer> A;

    private List<Integer> B;

    public CQueue() {
        A = new ArrayList<>();
        B = new ArrayList<>();
    }

    public void appendTail(int value) {
        A.add(value);
    }

    public int deleteHead() {
        if (B.isEmpty()) {
            if (A.isEmpty()) {
                return -1;
            }
            while (!A.isEmpty()) {
                B.add(A.remove(A.size() - 1));
            }
        }
        return B.remove(B.size() - 1);
    }
}
