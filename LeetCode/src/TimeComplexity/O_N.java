package TimeComplexity;

public class O_N {
    public static void main(String[] args) {
        System.out.println(algorithm(1000));
    }

    static int algorithm(int N) {
        if (N <= 0) return 1;
        int count = 0;
        for (int i = 0; i < N; i++) {
            count += algorithm(N - 1);
        }
        return count;
    }
}
